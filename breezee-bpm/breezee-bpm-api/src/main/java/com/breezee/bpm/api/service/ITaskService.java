/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.bpm.api.service;

import com.breezee.bpm.api.domain.TaskInfo;
import com.breezee.common.PageInfo;
import com.breezee.common.PageResult;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 流程任务服务接口
 * 提供任务的查询，完成，删除和修改
 *
 * Created by Silence on 2016/2/2.
 */
@Path("/bpmTask")
public interface ITaskService {

    /**
     * 创建一个无指定流程的新任务，一般用来做任务分配使用
     * @return
     */
    @PUT
    TaskInfo newTask();

    /**
     * 创建一个指定Id的无指定流程的新任务，一般用来做任务分配使用
     * @return
     */
    @Path("/{taskId}")
    @PUT
    TaskInfo newTask(@PathParam("taskId") String taskId);

    /**
     * 自动驱动刚启动的流程往下一个节点流转
     * @param procInsId
     * @return
     */
    TaskInfo autoComplete(Long procInsId,Map<String,Object> m);

    /**
     * 完成指定任务
     * @param taskId
     */
    void complete(String taskId);

    /**
     * 完成指定任务，设置动态变量
     * @param taskId
     * @param variables
     */
    void complete(String taskId, Map<String, Object> variables);

    void complete(String taskId, Map<String, Object> variables, boolean localScope);

    List<TaskInfo> findTasksByProcInsId(Long procInsId);

    TaskInfo findTaskById(Long taskId);

    /**
     * 保存任务
     * @param task
     */
    void saveTask(TaskInfo task);

    /**
     * 设置当前处理人
     * @param taskId
     * @param userId
     */
    void setAssignee(String taskId, String userId);

    /**
     * 设置任务发起人
     * @param taskId
     * @param userId
     */
    void setOwner(String taskId, String userId);

    /**
     * 设置任务的到期时间
     * @param taskId
     * @param dueDate
     */
    void setDueDate(String taskId, Date dueDate);

    /**
     * 设置任务的优先级
     * @param taskId
     * @param priority
     */
    void setPriority(String taskId, int priority);

    /**
     * 设置候选人
     * @param taskId
     * @param userId
     */
    void addCandidateUser(String taskId, String userId);

    /**
     * 设置候选组
     * @param taskId
     * @param groupId
     */
    void addCandidateGroup(String taskId, String groupId);

    /**
     * 删除指定任务
     * @param taskId
     */
    void deleteTask(String taskId);

    /**
     * 是否级联删除
     * @param taskId
     * @param cascade
     */
    void deleteTask(String taskId, boolean cascade);

    /**
     * 以指定的原因删除指定任务
     * @param taskId
     * @param deleteReason
     */
    void deleteTask(String taskId, String deleteReason);

    void deleteCandidateUser(String taskId, String userId);

    void deleteCandidateGroup(String taskId, String groupId);

    /**
     * 指派任务之指定的人员
     * @param taskId
     * @param userId
     */
    void delegateTask(String taskId, String userId);

    /**
     * 设置任务的全局变量
     * @param taskId
     * @param variables
     */
    void setVariables(String taskId, Map<String, ? extends Object> variables);

    /**
     * 设置任务在当前节点的变量
     * @param taskId
     * @param variables
     */
    void setVariablesLocal(String taskId, Map<String, ? extends Object> variables);

    /**
     * 获取任务全局变量
     * @param taskId
     * @return
     */
    Map<String, Object> getVariables(String taskId);

    /**
     * 获取任务在当前节点的变量
     * @param taskId
     * @return
     */
    Map<String, Object> getVariablesLocal(String taskId);

    /**
     * 删除任务节点的变量
     * @param taskId
     * @param variableNames
     */
    void removeVariables(String taskId, Collection<String> variableNames);

    /**
     * 删除当前节点的变量
     * @param taskId
     * @param variableNames
     */
    void removeVariablesLocal(String taskId, Collection<String> variableNames);

    /**
     * 获取子任务
     * @param parentTaskId
     * @return
     */
    List<TaskInfo> getSubTasks(String parentTaskId);

    /**
     * 获取待办任务
     * @param TaskInfo
     * @return
     */
    @Path("/findUndoTasks")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    PageResult<TaskInfo> findUndoTasks(TaskInfo TaskInfo, PageInfo pageInfo);

    /**
     * 获取已办任务
     * @param TaskInfo
     * @return
     */
    @Path("/findFinishedTasks")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    PageResult<TaskInfo> findFinishedTasks(TaskInfo TaskInfo, PageInfo pageInfo);
}
