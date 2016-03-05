package com.breezee.bpm.api.domain;

import java.util.Date;

/**
 * Created by Silence on 2016/2/25.
 */
public class TaskStepInfo {

    private String id;

    private Date optDate;

    private String optType;

    private String optReason;

    private String optComment;

    private String optDesc;

    private String optUser;

    private String procInstId;

    private String workItemId;

    private Integer deleteFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getOptDate() {
        return optDate;
    }

    public void setOptDate(Date optDate) {
        this.optDate = optDate;
    }

    public String getOptType() {
        return optType;
    }

    public void setOptType(String optType) {
        this.optType = optType;
    }

    public String getOptReason() {
        return optReason;
    }

    public void setOptReason(String optReason) {
        this.optReason = optReason;
    }

    public String getOptComment() {
        return optComment;
    }

    public void setOptComment(String optComment) {
        this.optComment = optComment;
    }

    public String getOptDesc() {
        return optDesc;
    }

    public void setOptDesc(String optDesc) {
        this.optDesc = optDesc;
    }

    public String getOptUser() {
        return optUser;
    }

    public void setOptUser(String optUser) {
        this.optUser = optUser;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getWorkItemId() {
        return workItemId;
    }

    public void setWorkItemId(String workItemId) {
        this.workItemId = workItemId;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
