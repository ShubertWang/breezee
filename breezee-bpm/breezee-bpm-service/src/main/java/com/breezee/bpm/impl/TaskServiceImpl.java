/*
 * Copyright (c) 2016. 
 * Breezee.com All Rights Reserved.
 */

package com.breezee.bpm.impl;

import com.breezee.bpm.api.domain.TaskInfo;
import com.breezee.bpm.api.domain.TaskStepInfo;
import com.breezee.bpm.api.service.ITaskService;
import com.breezee.bpm.step.TaskStepEntity;
import com.breezee.bpm.step.TaskStepRepository;
import com.breezee.common.PageInfo;
import com.breezee.common.PageResult;
import com.breezee.oms.api.service.IOrderService;
import com.breezee.sodexo.api.service.IOtherRequestService;
import com.breezee.sodexo.api.service.ISeatOrderService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Silence on 2016/2/2.
 */
@Service
public class TaskServiceImpl implements ITaskService {

    @Resource
    private TaskService taskService;

    @Resource
    private HistoryService historyService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private IOrderService orderService;

    @Resource
    private ISeatOrderService seatOrderService;

    @Resource
    private IOtherRequestService otherRequestService;

    @Autowired
    private TaskStepRepository taskStepRepository;

    @Override
    public TaskInfo newTask() {
        return newTask(null);
    }

    @Override
    public TaskInfo newTask(String taskId) {
        Task t = null;
        if (StringUtils.hasText(taskId))
            t = taskService.newTask(taskId);
        else
            t = taskService.newTask();
        TaskInfo TaskInfo = new TaskInfo();
        BeanUtils.copyProperties(t, TaskInfo);
        return TaskInfo;
    }

    @Override
    public TaskInfo autoComplete(Long procInsId, Map<String, Object> m) {
        Task task = taskService.createTaskQuery().processInstanceId(procInsId.toString()).singleResult();
        complete(task.getId().toString(), m);
        return findTaskById(Long.parseLong(task.getId()));
    }

    @Override
    public void complete(String taskId) {
        taskService.complete(taskId);
    }

    @Override
    public void complete(String taskId, Map<String, Object> variables) {
        Long orderId = Long.parseLong(variables.get("orderId").toString());
        Integer orderStatus = Integer.parseInt(variables.get("orderStatus").toString());
        String prcsDef = variables.get("prcsDef").toString();
        Object orderCancel = variables.get("orderCancel");
        variables.remove("orderId");
        variables.remove("orderStatus");
        if(taskId.equals("-1")){
            String owner = variables.get("userId").toString();
            String group = variables.get("storeName").toString();
            variables.remove("userId");
            variables.remove("storeName");
            List<Task> l = taskService.createTaskQuery().taskOwner(owner)
                    .taskCandidateGroup(group).active().list();
            ProcessInstance pi;
            for(Task t : l){
                pi = runtimeService.createProcessInstanceQuery().processInstanceId(t.getProcessInstanceId()).singleResult();
                if(pi.getBusinessKey().equals(orderId.toString())){
                    taskService.complete(taskId, variables);
                }
            }
        } else {
            if(taskId.equals("0")){
                List<Task> ll = taskService.createTaskQuery().processInstanceBusinessKey(orderId.toString()).active().orderByTaskCreateTime().desc().list();
                if(ll.size()>0)
                    taskId = ll.get(0).getId();
            }
            if (variables.get("taskOwner") != null)
                taskService.setOwner(taskId, variables.get("taskOwner").toString());
            if (variables.get("complete").toString().equals("true"))
                taskService.complete(taskId, variables);
        }
        switch (prcsDef){
            case "orderProcess":
                orderService.updateStatus(orderId, orderStatus);
                if(orderCancel!=null && orderCancel.toString().equals("Y")){
                    orderService.updateRejectReason(orderId,variables.get("rejectReason")+"");
                }
                break;
            case "seatProcess":
                seatOrderService.updateStatusAndNo(orderId,orderStatus,
                        variables.get("seatNo")==null?null:variables.get("seatNo").toString());
                break;
            case "requestProcess":
                break;
        }

    }

    @Override
    public void complete(String taskId, Map<String, Object> variables, boolean localScope) {
        taskService.complete(taskId, variables, localScope);
    }

    @Override
    public List<TaskInfo> findTasksByProcInsId(Long procInsId) {
        List<Task> l = taskService.createTaskQuery().processInstanceId(procInsId.toString()).list();
        List<TaskInfo> ll = new ArrayList<>(l.size());
        l.forEach(a -> {
            TaskInfo vo = new TaskInfo();
            BeanUtils.copyProperties(a, vo);
            ll.add(vo);
        });
        return ll;
    }

    @Override
    public TaskInfo findTaskById(Long taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId.toString()).singleResult();
        TaskInfo info = new TaskInfo();
        BeanUtils.copyProperties(task, info);
        return info;
    }

    @Override
    public void saveTask(TaskInfo task) {
        Task t = taskService.createTaskQuery().taskId(task.getId().toString()).singleResult();
        BeanUtils.copyProperties(t, task, "endTime", "startTime");
        taskService.saveTask(t);
    }

    @Override
    public void setAssignee(String taskId, String userId) {
        taskService.setAssignee(taskId, userId);
    }

    @Override
    public void setOwner(String taskId, String userId) {
        taskService.setOwner(taskId, userId);
    }

    @Override
    public void setDueDate(String taskId, Date dueDate) {
        taskService.setDueDate(taskId, dueDate);
    }

    @Override
    public void setPriority(String taskId, int priority) {
        taskService.setPriority(taskId, priority);
    }

    @Override
    public void addCandidateUser(String taskId, String userId) {
        taskService.addCandidateUser(taskId, userId);
    }

    @Override
    public void addCandidateGroup(String taskId, String groupId) {
        taskService.addCandidateGroup(taskId, groupId);
    }

    @Override
    public void deleteTask(String taskId) {
        taskService.deleteTask(taskId);
    }

    @Override
    public void deleteTask(String taskId, boolean cascade) {
        taskService.deleteTask(taskId, cascade);
    }

    @Override
    public void deleteTask(String taskId, String deleteReason) {
        taskService.deleteTask(taskId, deleteReason);
    }

    @Override
    public void deleteCandidateUser(String taskId, String userId) {
        taskService.deleteCandidateUser(taskId, userId);
    }

    @Override
    public void deleteCandidateGroup(String taskId, String groupId) {
        taskService.deleteCandidateGroup(taskId, groupId);
    }

    @Override
    public void delegateTask(String taskId, String userId) {
        taskService.delegateTask(taskId, userId);
    }

    @Override
    public void setVariables(String taskId, Map<String, ? extends Object> variables) {
        taskService.setVariables(taskId, variables);
    }

    @Override
    public void setVariablesLocal(String taskId, Map<String, ? extends Object> variables) {
        taskService.setVariablesLocal(taskId, variables);
    }

    @Override
    public Map<String, Object> getVariables(String taskId) {
        return taskService.getVariables(taskId);
    }

    @Override
    public Map<String, Object> getVariablesLocal(String taskId) {
        return taskService.getVariablesLocal(taskId);
    }

    @Override
    public void removeVariables(String taskId, Collection<String> variableNames) {
        taskService.removeVariables(taskId, variableNames);
    }

    @Override
    public void removeVariablesLocal(String taskId, Collection<String> variableNames) {
        taskService.removeVariablesLocal(taskId, variableNames);
    }

    @Override
    public List<TaskInfo> getSubTasks(String parentTaskId) {
        List<Task> l = taskService.getSubTasks(parentTaskId);
        List<TaskInfo> ll = new ArrayList<>(l.size());
        l.forEach(a -> {
            TaskInfo vo = new TaskInfo();
            BeanUtils.copyProperties(a, vo);
            ll.add(vo);
        });
        return ll;
    }

    @Override
    public PageResult<TaskInfo> findUndoTasks(Map<String, Object> m, PageInfo pageInfo) {
        pageInfo = new PageInfo(m);
//        TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateUser(m.get("userId").toString()).active().orderByTaskCreateTime().desc();
        TaskQuery taskQuery = taskService.createTaskQuery();
        if(m.get("userJob")!=null){
            String[] tmp = m.get("userJob").toString().split(",");
//            for (int i=0;i<tmp.length;i++){
//                taskQuery = taskQuery.or().taskCandidateGroup(tmp[i]);
//            }
            taskQuery.taskCandidateGroupIn(Arrays.asList(tmp));
        }
        taskQuery = taskQuery.active().orderByTaskCreateTime().desc();
        long count = taskQuery.count();
        List<Task> l = taskQuery.listPage(pageInfo.getPageNumber(), pageInfo.getPageSize());
        PageResult<TaskInfo> pageResult = convert(l, count, m.get("prcsId"));
        return pageResult;
    }

    @Override
    public PageResult<TaskInfo> findFinishedTasks(Map<String, Object> m, PageInfo pageInfo) {
        if (pageInfo == null) {
            pageInfo = new PageInfo(Integer.valueOf(m.get("pageNumber").toString()), Integer.valueOf(m.get("pageSize").toString()));
        }
        HistoricTaskInstanceQuery hquery = historyService.createHistoricTaskInstanceQuery().taskAssignee(m.get("username").toString()).finished();
        long count = hquery.count();
        List<HistoricTaskInstance> l = hquery.listPage(pageInfo.getPageNumber(), pageInfo.getPageSize());
        return convert(l, count, null);
    }

    @Override
    public void saveStep(TaskStepInfo stepInfo) {
        taskStepRepository.save(new TaskStepEntity().parse(stepInfo));
    }

    private <T extends org.activiti.engine.task.TaskInfo> PageResult<TaskInfo> convert(List<T> l, long count, Object prcsId) {
        List<TaskInfo> ll = new ArrayList<>(l.size());
        for(org.activiti.engine.task.TaskInfo a : l){
            TaskInfo vo = new TaskInfo();
            vo.setId(Long.parseLong(a.getId()));
            BeanUtils.copyProperties(a, vo);
            ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(a.getProcessInstanceId()).singleResult();
            if (pi != null && pi.getBusinessKey() != null) {
                vo.setProcessDefinitionKey(pi.getProcessDefinitionKey());
                if(prcsId!=null && !pi.getProcessDefinitionKey().equals(prcsId.toString())){
                    continue;
                }
                if(pi.getProcessDefinitionKey().equals("orderProcess")) {
                    vo.getProperties().put("orderInfo", orderService.findInfoById(Long.parseLong(pi.getBusinessKey())));
                } else if(pi.getProcessDefinitionKey().equals("seatProcess")){
                    vo.getProperties().put("seatInfo",seatOrderService.findInfoById(Long.parseLong(pi.getBusinessKey())));
                } else if(pi.getProcessDefinitionKey().equals("requestProcess")){
                    vo.getProperties().put("requestInfo",otherRequestService.findInfoById(Long.parseLong(pi.getBusinessKey())));
                }
                vo.setProcessDefinitionName(pi.getProcessDefinitionName());
                vo.setBusinessKey(pi.getBusinessKey());
            }
            ll.add(vo);
        };
        PageResult<TaskInfo> pageResult = new PageResult<>();
        pageResult.setTotal(count);
        pageResult.setContent(ll);
        return pageResult;
    }
}
