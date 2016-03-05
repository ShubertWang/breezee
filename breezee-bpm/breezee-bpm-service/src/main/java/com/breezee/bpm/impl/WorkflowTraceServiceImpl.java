/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.bpm.impl;

import com.breezee.bpm.api.domain.WfHistoryInfo;
import com.breezee.bpm.api.service.IWorkflowTraceService;
import com.breezee.bpm.cmd.HistoryProcessInstanceDiagramCmd;
import com.breezee.bpm.step.TaskStepEntity;
import com.breezee.bpm.step.TaskStepRepository;
import org.activiti.engine.*;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Silence on 2016/2/3.
 */
@Service("workflowTraceService")
public class WorkflowTraceServiceImpl implements IWorkflowTraceService {

    @Resource
    private ProcessEngine processEngine;

    @Resource
    private TaskService taskService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private HistoryService historyService;

    @Resource
    private RepositoryService repositoryService;

    @Autowired
    private TaskStepRepository taskStepRepository;

    public static final SimpleDateFormat second_sdf = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    public final static String CALL_ACTIVITY = "callActivity";

    private static Map<String, Object> types;

    static {
        types = new HashMap<String, Object>();
        types.put("userTask", "用户任务");
        types.put("serviceTask", "系统任务");
        types.put("startEvent", "开始节点");
        types.put("endEvent", "结束节点");
        types.put("exclusiveGateway", "条件判断节点(系统自动根据条件处理)");
        types.put("inclusiveGateway", "并行处理任务");
        types.put("callActivity", "子流程");
    }


    @Override
    public List<Map<String, Object>> traceProcess(String processInstanceId) throws Exception {
        String activityId = "";
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        if (tasks.size() > 0) {
            Task task = tasks.get(0);
            activityId = task.getTaskDefinitionKey();
        } else {
            List<Execution> execs = runtimeService.createExecutionQuery().processInstanceId(processInstanceId).list();
            if (execs != null && execs.size() > 0) {
                activityId = execs.get(execs.size() - 1).getActivityId();
            }
        }
        String processDefinitionId = "";
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().
                processInstanceId(processInstanceId).singleResult();
        if (processInstance == null) {
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().
                    processInstanceId(processInstanceId).singleResult();
            if (historicProcessInstance != null)
                processDefinitionId = historicProcessInstance.getProcessDefinitionId();
        } else {
            processDefinitionId = processInstance.getProcessDefinitionId();
        }
        List<ActivityImpl> activitiList = new ArrayList<>();
        try {
            ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(processDefinitionId);
            activitiList = processDefinition.getActivities();
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Map<String, Object>> activityInfos = new ArrayList<>();
        for (ActivityImpl activity : activitiList) {
            boolean currentActiviti = false;
            String id = activity.getId();
            // 当前节点
            if (id.equals(activityId)) {
                currentActiviti = true;
            }
            Map<String, Object> activityImageInfo = packageSingleActivitiInfo(activity, id,
                    processInstanceId, currentActiviti);
            activityInfos.add(activityImageInfo);
        }
        return activityInfos;
    }

    /**
     * 打包相同的节点
     *
     * @param activity
     * @param activityId
     * @param processInstanceId
     * @param currentActiviti
     * @return
     * @throws Exception
     */
    private Map<String, Object> packageSingleActivitiInfo(ActivityImpl activity,
                                                          String activityId,
                                                          String processInstanceId,
                                                          boolean currentActiviti) throws Exception {
        Map<String, Object> vars = new LinkedHashMap<>();
        Map<String, Object> activityInfo = new LinkedHashMap<>();
        activityInfo.put("currentActiviti", currentActiviti);
        setPosition(activity, activityInfo);
        setWidthAndHeight(activity, activityInfo);
        Map<String, Object> properties = activity.getProperties();
        String type = properties.get("type").toString();
        vars.put("任务类型", types.get(type) == null ? type : types.get(type));

        ActivityBehavior activityBehavior = activity.getActivityBehavior();
        if (activityBehavior instanceof UserTaskActivityBehavior) {

            Task currentTask = null;
            if (currentActiviti) {
                currentTask = taskService.createTaskQuery().processInstanceId(processInstanceId).
                        taskDefinitionKey(activityId).singleResult();
            }
            /*
             * 当前任务的分配角色
			 */
            UserTaskActivityBehavior userTaskActivityBehavior = (UserTaskActivityBehavior) activityBehavior;
            TaskDefinition taskDefinition = userTaskActivityBehavior.getTaskDefinition();
            Set<Expression> candidateGroupIdExpressions = taskDefinition.getCandidateGroupIdExpressions();
            if (!candidateGroupIdExpressions.isEmpty()) {
                // 任务的处理角色
                String roles = "";
                for (Expression expression : candidateGroupIdExpressions) {
                    String expressionText = expression.getExpressionText();
                    roles += expressionText;
                }
                vars.put("任务所属角色", roles);
                // 当前处理人
                if (currentTask != null) {
                    vars.put("当前处理人", currentTask.getAssignee());
                }
            }
        }
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().
                processInstanceId(processInstanceId).taskDefinitionKey(activity.getId()).list();
        if (list.size() > 0) {
            HistoricTaskInstance historicTaskInstance = list.get(list.size() - 1);
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String startTime = historicTaskInstance.getStartTime() == null ? "" :
                    sf.format(historicTaskInstance.getStartTime());
            vars.put("任务创建日期", startTime);
            String endTime = historicTaskInstance.getEndTime() == null ? "" :
                    sf.format(historicTaskInstance.getEndTime());
            vars.put("任务结束日期", endTime);
        }
        vars.put("节点说明", properties.get("documentation"));
        String description = activity.getProcessDefinition().getDescription();
        vars.put("描述", description);
        // 流程ID
        activityInfo.put("processInstanceId", processInstanceId);
        // 子流程
        if (CALL_ACTIVITY.equals(properties.get("type").toString())) {
            int size = 0;
            List<HistoricProcessInstance> subPro = historyService.createHistoricProcessInstanceQuery()
                    .superProcessInstanceId(processInstanceId).list();
            size = subPro.size();
            activityInfo.put("subProSize", size);
        }
        activityInfo.put("vars", vars);
        return activityInfo;
    }

    /**
     * 设置坐标位置
     *
     * @param activity
     * @param activityInfo
     */
    private void setPosition(ActivityImpl activity, Map<String, Object> activityInfo) {
        activityInfo.put("x", activity.getX());
        activityInfo.put("y", activity.getY());
    }

    /**
     * 设置宽度、高度属性
     *
     * @param activity
     * @param activityInfo
     */
    private void setWidthAndHeight(ActivityImpl activity, Map<String, Object> activityInfo) {
        activityInfo.put("width", activity.getWidth());
        activityInfo.put("height", activity.getHeight());
    }

    @Override
    public List<Map<String, Object>> traceProcess4Sub(String processInstanceId) throws Exception {
        List<Map<String, Object>> activityInfos = new ArrayList<>();
        List<HistoricProcessInstance> subPro = historyService.createHistoricProcessInstanceQuery()
                .superProcessInstanceId(processInstanceId).list();
        Map<String, Object> activityInfo = null;
        for (HistoricProcessInstance info : subPro) {
            List<HistoricActivityInstance> activityList = historyService.createHistoricActivityInstanceQuery()
                    .processInstanceId(info.getId()).orderByHistoricActivityInstanceEndTime().desc().list();
            HistoricActivityInstance activity = activityList.get(0);
            activityInfo = new LinkedHashMap<>();
            ProcessDefinition d = getProcessDefinition(info.getProcessDefinitionId());
            activityInfo.put("procsName", d.getName());
            activityInfo.put("taskName", activity.getActivityName());
            activityInfo.put("taskHandler", activity.getAssignee() == null ? "无" : activity.getAssignee());
            activityInfo.put("description", d.getDescription());
            activityInfo.put("subProId", info.getId());
            activityInfos.add(activityInfo);
        }
        return activityInfos;
    }

    /**
     * @param processDefinitionId 流程定义ID
     * @return 设定文件        ProcessDefinition 流程定义对象
     * @throws
     * @Title:根据流程定义Id 获取流程定义信息
     * @Description: TODO (获取流程定义信息)
     */
    public ProcessDefinition getProcessDefinition(String processDefinitionId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        return processDefinition;
    }

    @Override
    public byte[] processInstanceChart(String processInstanceId) {
        InputStream is = processEngine.getManagementService().executeCommand(
                new HistoryProcessInstanceDiagramCmd(processInstanceId));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        try {
            while (-1 != (n = is.read(buffer))) {
                output.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toByteArray();
    }

    @Override
    public List<WfHistoryInfo> processHistory(String processInstanceId) {
        List<WfHistoryInfo> data = new ArrayList<>();
        List<WfHistoryInfo> ret = new ArrayList<>();
        orchestrate(initFindRoot(processInstanceId), StringUtils.EMPTY, StringUtils.EMPTY, data);
        Stack<WfHistoryInfo> stack = new Stack<>();
        stack.addAll(data);
        while (!stack.isEmpty()) {
            if (stack.peek().isCallActivity()) {
                stack.pop();
            } else {
                break;
            }
        }
        ret.addAll(stack);
        return ret;
    }

    private void orchestrate(String prcsId, String parentPrcsId, String parentActivityId, List<WfHistoryInfo> data) {
        List<HistoricActivityInstance> activities = historyService
                .createHistoricActivityInstanceQuery().processInstanceId(prcsId).list();
        WfHistoryInfo vo = null;
        TaskStepEntity step = null;
        String prcsName;
        for (HistoricActivityInstance activity : activities) {
            if (StringUtils.isNotEmpty(activity.getCalledProcessInstanceId())) {
                prcsName = historyService.createHistoricProcessInstanceQuery()
                        .processInstanceId(prcsId).singleResult().getName();
                vo = new WfHistoryInfo();
                vo.setPrcsId(prcsId);
                vo.setPrcsName(prcsName);
                vo.setTaskId(activity.getId());
                vo.setTaskName(activity.getActivityName());
                vo.setParentPrcsId(parentPrcsId);
                vo.setParentActivityId(parentActivityId);
                vo.setCallActivity(true);
                data.add(vo);
                orchestrate(activity.getCalledProcessInstanceId(), prcsId, activity.getId(), data);
            } else {
                if (StringUtils.isNotEmpty(activity.getTaskId())) {
                    step = getStepForm(prcsId, activity.getTaskId());
                    if (null == step)
                        continue;
                    vo = new WfHistoryInfo();
                    populateVO(step, vo);
                    vo.setTaskId(activity.getTaskId());
                    vo.setTaskName(activity.getActivityName());
                    vo.setParentPrcsId(parentPrcsId);
                    vo.setParentActivityId(parentActivityId);
                    data.add(vo);
                }
            }
        }
    }

    private String initFindRoot(String prcsInstId) {
        String sPrcsInstId = prcsInstId;
        HistoricProcessInstance inst = historyService.createHistoricProcessInstanceQuery().processInstanceId(sPrcsInstId).singleResult();
        String superProcessInstanceId = inst.getSuperProcessInstanceId();
        if (StringUtils.isNotEmpty(superProcessInstanceId)) {
            sPrcsInstId = initFindRoot(superProcessInstanceId);
        }
        return sPrcsInstId;
    }

    private void populateVO(TaskStepEntity step, WfHistoryInfo vo) {
        vo.setElapsed(getDatePoor(step.getCreateDate(),
                step.getOptDate()));
        vo.setEndDate(step.getOptDate());
        vo.setStartDate(second_sdf.format(step.getCreateDate()));
        vo.setOptDesc(step.getOptDesc());
        vo.setOptReason(step.getOptReason());
        vo.setOptComment(step.getOptComment());
        vo.setOptUser(step.getOptUser());
        vo.setPrcsId(step.getProcInstId().toString());
    }

    private TaskStepEntity getStepForm(String prcsId, String taskId) {
        List<TaskStepEntity> tasks = taskStepRepository.findByProcInstIdAndWorkItemId(prcsId,taskId);
        if (tasks != null && tasks.size() > 0)
            return tasks.get(0);
        return null;
    }

    private String getDatePoor(Date startDate, Date endDate) {
        String result = "";
        if (startDate == null || endDate == null) {
            result = "无法算出旧数据时长";
        } else {
            long nd = 1000 * 24 * 60 * 60;
            long nh = 1000 * 60 * 60;
            long nm = 1000 * 60;
            long ns = 1000;
            // long ns = 1000;
            // 获得两个时间的毫秒时间差异
            long diff = endDate.getTime() - startDate.getTime();
            // 计算差多少天
            long day = diff / nd;
            // 计算差多少小时
            long hour = diff % nd / nh;
            // 计算差多少分钟
            long min = diff % nd % nh / nm;
            // 计算差多少秒
            long sec = diff % nd % nh % nm / ns;
            if (sec > 0) {
                result = sec + "秒";
            } else {
                result = "0秒";
            }
            if (min > 0) {
                result = min + "分钟" + result;
            }
            if (hour > 0) {
                result = hour + "小时" + result;
            }
            if (day > 0) {
                result = day + "天" + result;
            }
        }
        return result;
    }
}
