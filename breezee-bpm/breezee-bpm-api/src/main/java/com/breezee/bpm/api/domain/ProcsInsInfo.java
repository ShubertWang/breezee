/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.bpm.api.domain;

import com.breezee.common.BaseInfo;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

/**
 * Created by Silence on 2016/2/2.
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ProcsInsInfo extends BaseInfo{

    protected String processDefinitionId;
    protected String processDefinitionName;
    protected Integer processDefinitionVersion;
    protected String deploymentId;

    protected String processInstanceId;

    protected String businessKey;

    protected String parentId;

    protected String superExecutionId;

    protected boolean forcedUpdate;
    protected Date lockTime;
    protected boolean isActive = true;
    protected boolean isScope = true;
    protected boolean isConcurrent = false;
    protected boolean isEnded = false;
    protected boolean isEventScope = false;

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public String getProcessDefinitionName() {
        return processDefinitionName;
    }

    public void setProcessDefinitionName(String processDefinitionName) {
        this.processDefinitionName = processDefinitionName;
    }

    public Integer getProcessDefinitionVersion() {
        return processDefinitionVersion;
    }

    public void setProcessDefinitionVersion(Integer processDefinitionVersion) {
        this.processDefinitionVersion = processDefinitionVersion;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getSuperExecutionId() {
        return superExecutionId;
    }

    public void setSuperExecutionId(String superExecutionId) {
        this.superExecutionId = superExecutionId;
    }

    public boolean isForcedUpdate() {
        return forcedUpdate;
    }

    public void setForcedUpdate(boolean forcedUpdate) {
        this.forcedUpdate = forcedUpdate;
    }

    public Date getLockTime() {
        return lockTime;
    }

    public void setLockTime(Date lockTime) {
        this.lockTime = lockTime;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isScope() {
        return isScope;
    }

    public void setScope(boolean scope) {
        isScope = scope;
    }

    public boolean isConcurrent() {
        return isConcurrent;
    }

    public void setConcurrent(boolean concurrent) {
        isConcurrent = concurrent;
    }

    public boolean isEnded() {
        return isEnded;
    }

    public void setEnded(boolean ended) {
        isEnded = ended;
    }

    public boolean isEventScope() {
        return isEventScope;
    }

    public void setEventScope(boolean eventScope) {
        isEventScope = eventScope;
    }
}
