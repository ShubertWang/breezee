package com.breezee.sodexo.api.domain;

import com.breezee.common.BaseInfo;

import java.util.Date;

/**
 * Created by Silence on 2016/3/5.
 */
public class OtherRequestInfo extends BaseInfo {

    protected Long userId;

    protected String userMobile;
    protected String userName;
    protected Date issueDate;

    protected Long taskId;
    protected String procDefId = "orderProcess";
    protected Long procsInsId;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getProcDefId() {
        return procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }

    public Long getProcsInsId() {
        return procsInsId;
    }

    public void setProcsInsId(Long procsInsId) {
        this.procsInsId = procsInsId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public String getUserName() {
        return userName;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }
}
