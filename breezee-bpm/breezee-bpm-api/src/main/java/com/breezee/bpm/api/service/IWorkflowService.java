/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.bpm.api.service;

import com.breezee.bpm.api.domain.ProcsInsInfo;
import com.breezee.common.PageInfo;
import com.breezee.common.PageResult;

import javax.ws.rs.Path;
import java.util.List;
import java.util.Map;

/**
 * 流程服务
 * 提供流程实例的启动，查询，修改，删除
 * <p>
 * Created by Silence on 2016/2/2.
 */
@Path("/bpm-workflow")
public interface IWorkflowService {

    /**
     * 启动指定流程
     *
     * @param processDefinitionId
     * @param businessKey
     * @return
     */
    ProcsInsInfo startProcessInstanceById(String processDefinitionId, String businessKey);

    /**
     * 启动指定流程，附加指定的变量
     *
     * @param processDefinitionId
     * @param businessKey
     * @param variables
     * @return
     */
    ProcsInsInfo startProcessInstanceById(String processDefinitionId, String businessKey,
                                          Map<String, Object> variables);

    /**
     * 删除指定流程实例
     *
     * @param processInstanceId
     * @param deleteReason
     */
    void deleteProcessInstance(String processInstanceId, String deleteReason);

    /**
     * 更新流程实例的业务主键
     *
     * @param processInstanceId
     * @param businessKey
     */
    void updateBusinessKey(String processInstanceId, String businessKey);

    /**
     * 附加参与者
     *
     * @param processInstanceId
     * @param userId
     */
    void addParticipantUser(String processInstanceId, String userId);

    /**
     * 附加参与者组
     *
     * @param processInstanceId
     * @param groupId
     */
    void addParticipantGroup(String processInstanceId, String groupId);

    /**
     * 删除流程实例参与者
     *
     * @param processInstanceId
     * @param userId
     */
    void deleteParticipantUser(String processInstanceId, String userId);

    /**
     * 删除流程实例参与组
     *
     * @param processInstanceId
     * @param groupId
     */
    void deleteParticipantGroup(String processInstanceId, String groupId);

    /**
     * 挂起流程实例
     *
     * @param processInstanceId
     */
    void suspendProcessInstanceById(String processInstanceId);

    /**
     * 激活流程实例
     *
     * @param processInstanceId
     */
    void activateProcessInstanceById(String processInstanceId);

    /**
     * 设置流程实例名称
     *
     * @param processInstanceId
     * @param name
     */
    void setProcessInstanceName(String processInstanceId, String name);

    /**
     * 查找指定流程实例
     *
     * @param processInstanceId
     * @return
     */
    ProcsInsInfo findProcessInstanceById(String processInstanceId);

    /**
     * 查找指定流程实例的父实例
     *
     * @param processInstanceId
     * @return
     */
    ProcsInsInfo findSuperProcessInstanceById(String processInstanceId);

    /**
     * 获取指定流程实例的所有子流程
     *
     * @param processInstanceId
     * @return
     */
    List<ProcsInsInfo> findSubProcessInstancesById(String processInstanceId);

    /**
     * 按照业务主键查找流程实例
     *
     * @param processInstanceBusinessKey
     * @return
     */
    ProcsInsInfo findProcessInstanceByBusinessKey(String processInstanceBusinessKey);

    /**
     * 获取指定流程的指定业务主键的流程实例
     *
     * @param processInstanceBusinessKey
     * @param processDefinitionKey
     * @return
     */
    ProcsInsInfo findProcessInstanceByBusinessKey(String processInstanceBusinessKey, String processDefinitionKey);

    /**
     * 获取指定定义的流程实例
     *
     * @param processDefinitionKey
     * @return
     */
    PageResult<ProcsInsInfo> findProcessInstancesByDefinitionKey(String processDefinitionKey, PageInfo pageInfo);

    /**
     * 获取所有挂起的流程实例
     *
     * @return
     */
    List<ProcsInsInfo> findSuspendedProcessInstances();

    /**
     * 获取所有活动的流程实例
     *
     * @return
     */
    List<ProcsInsInfo> findActiveProcessInstances(PageInfo pageInfo);
}
