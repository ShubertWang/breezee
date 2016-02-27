/*
 * Copyright (c) 2016. 
 * Breezee.com All Rights Reserved.
 */

package com.breezee.bpm.impl;

import com.breezee.bpm.api.domain.TaskInfo;
import com.breezee.bpm.api.service.ITaskService;
import com.breezee.common.PageInfo;
import com.breezee.common.PageResult;
import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.BeanUtils;
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
    public TaskInfo autoComplete(Long procInsId,Map<String,Object> m) {
        Task task = taskService.createTaskQuery().processInstanceId(procInsId.toString()).singleResult();
        complete(task.getId().toString(),m);
        return findTaskById(Long.parseLong(task.getId()));
    }

    @Override
    public void complete(String taskId) {
        taskService.complete(taskId);
    }

    @Override
    public void complete(String taskId, Map<String, Object> variables) {
        if(variables.get("taskOwner")!=null)
            taskService.setOwner(taskId, variables.get("taskOwner").toString());
        taskService.complete(taskId, variables);
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
        BeanUtils.copyProperties(task,info);
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
    public PageResult<TaskInfo> findUndoTasks(Map<String,Object> m, PageInfo pageInfo) {
        if(pageInfo==null){
            pageInfo = new PageInfo(Integer.valueOf(m.get("pageNumber").toString()),Integer.valueOf(m.get("pageSize").toString()));
        }
        TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned(m.get("username").toString()).active();
        long count = taskQuery.count();
        List<Task> l = taskQuery.listPage(pageInfo.getPageNumber(), pageInfo.getPageSize());
        return convert(l, count);
    }

    @Override
    public PageResult<TaskInfo> findFinishedTasks(Map<String,Object> m, PageInfo pageInfo) {
        HistoricTaskInstanceQuery hquery = historyService.createHistoricTaskInstanceQuery().taskAssignee(m.get("username").toString()).finished();
        long count = hquery.count();
        List<HistoricTaskInstance> l = hquery.listPage(pageInfo.getPageNumber(), pageInfo.getPageSize());
        return convert(l, count);
    }

    private <T extends org.activiti.engine.task.TaskInfo> PageResult<TaskInfo> convert(List<T> l, long count) {
        List<TaskInfo> ll = new ArrayList<>(l.size());
        l.forEach(a -> {
            TaskInfo vo = new TaskInfo();
            BeanUtils.copyProperties(a, vo);
            ll.add(vo);
        });
        PageResult<TaskInfo> pageResult = new PageResult<>();
        pageResult.setTotal(count);
        pageResult.setContent(ll);
        return pageResult;
    }
}
