/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.bpm.impl;

import com.breezee.bpm.api.domain.ProcsInsInfo;
import com.breezee.bpm.api.service.IWorkflowService;
import com.breezee.common.PageInfo;
import com.breezee.common.PageResult;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Silence on 2016/2/2.
 */
@Service
public class WorkflowServiceImpl implements IWorkflowService {

    @Resource
    private RuntimeService runtimeService;

    @Override
    public ProcsInsInfo startProcessInstanceById(String processDefinitionId, String businessKey) {
        return startProcessInstanceById(processDefinitionId, businessKey, Collections.emptyMap());
    }

    @Override
    public ProcsInsInfo startProcessInstanceById(String processDefinitionId, String businessKey, Map<String, Object> variables) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionId, businessKey, variables);
        return this.populator(processInstance);
    }

    @Override
    public void deleteProcessInstance(String processInstanceId, String deleteReason) {
        runtimeService.deleteProcessInstance(processInstanceId, deleteReason);
    }

    @Override
    public void updateBusinessKey(String processInstanceId, String businessKey) {
        runtimeService.updateBusinessKey(processInstanceId, businessKey);
    }

    @Override
    public void addParticipantUser(String processInstanceId, String userId) {
        runtimeService.addParticipantUser(processInstanceId, userId);
    }

    @Override
    public void addParticipantGroup(String processInstanceId, String groupId) {
        runtimeService.addParticipantGroup(processInstanceId, groupId);
    }

    @Override
    public void deleteParticipantUser(String processInstanceId, String userId) {
        runtimeService.deleteParticipantUser(processInstanceId, userId);
    }

    @Override
    public void deleteParticipantGroup(String processInstanceId, String groupId) {
        runtimeService.deleteParticipantGroup(processInstanceId, groupId);
    }

    @Override
    public void suspendProcessInstanceById(String processInstanceId) {
        runtimeService.suspendProcessInstanceById(processInstanceId);
    }

    @Override
    public void activateProcessInstanceById(String processInstanceId) {
        runtimeService.activateProcessInstanceById(processInstanceId);
    }

    @Override
    public void setProcessInstanceName(String processInstanceId, String name) {
        runtimeService.setProcessInstanceName(processInstanceId, name);
    }

    @Override
    public ProcsInsInfo findProcessInstanceById(String processInstanceId) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().
                processInstanceId(processInstanceId).singleResult();
        return populator(processInstance);
    }

    @Override
    public ProcsInsInfo findSuperProcessInstanceById(String processInstanceId) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().
                subProcessInstanceId(processInstanceId).singleResult();
        return populator(processInstance);
    }

    @Override
    public List<ProcsInsInfo> findSubProcessInstancesById(String processInstanceId) {
        List<ProcessInstance> l = runtimeService.createProcessInstanceQuery().
                superProcessInstanceId(processInstanceId).list();
        List<ProcsInsInfo> ll = new ArrayList<>(l.size());
        l.forEach(a -> {
            ll.add(populator(a));
        });
        return ll;
    }

    @Override
    public ProcsInsInfo findProcessInstanceByBusinessKey(String processInstanceBusinessKey) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().
                processInstanceBusinessKey(processInstanceBusinessKey).singleResult();
        return populator(processInstance);
    }

    @Override
    public ProcsInsInfo findProcessInstanceByBusinessKey(String processInstanceBusinessKey, String processDefinitionKey) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().
                processInstanceBusinessKey(processInstanceBusinessKey, processDefinitionKey).singleResult();
        return populator(processInstance);
    }

    @Override
    public PageResult<ProcsInsInfo> findProcessInstancesByDefinitionKey(String processDefinitionKey, PageInfo pageInfo) {
        long total = runtimeService.createProcessInstanceQuery().processDefinitionId(processDefinitionKey).count();
        List<ProcessInstance> l = runtimeService.createProcessInstanceQuery().
                processDefinitionId(processDefinitionKey).listPage(pageInfo.getPageNumber(),pageInfo.getPageSize());
        PageResult<ProcsInsInfo> page = new PageResult<>();
        page.setTotal(total);
        List<ProcsInsInfo> ll = new ArrayList<>();
        l.forEach(a->{
            ll.add(populator(a));
        });
        page.setContent(ll);
        return page;
    }

    @Override
    public List<ProcsInsInfo> findSuspendedProcessInstances() {
        List<ProcessInstance> l = runtimeService.createProcessInstanceQuery().suspended().list();
        List<ProcsInsInfo> ll = new ArrayList<>(l.size());
        l.forEach(a->{
            ll.add(populator(a));
        });
        return ll;
    }

    @Override
    public List<ProcsInsInfo> findActiveProcessInstances(PageInfo pageInfo) {
        List<ProcessInstance> l = runtimeService.createProcessInstanceQuery().active().list();
        List<ProcsInsInfo> ll = new ArrayList<>(l.size());
        l.forEach(a->{
            ll.add(populator(a));
        });
        return ll;
    }

    /**
     * 转换
     *
     * @param processInstance
     * @return
     */
    private ProcsInsInfo populator(ProcessInstance processInstance) {
        ProcsInsInfo prcsInsInfo = new ProcsInsInfo();
        BeanUtils.copyProperties(processInstance, prcsInsInfo);
        return prcsInsInfo;
    }
}
