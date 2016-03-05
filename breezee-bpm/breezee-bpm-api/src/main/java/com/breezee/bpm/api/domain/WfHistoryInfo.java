package com.breezee.bpm.api.domain;

import java.util.Date;

/**
 * 流程实例的历史
 */
public class WfHistoryInfo {
    private String prcsId;
    private String prcsName;
    private String taskId;
    private String taskName;
    private boolean isCallActivity;
    private String optUser;
    private String optDesc;
    private String optComment;
    private String optReason;
    private String startDate;
    private Date endDate;
    private String elapsed;
    private String parentPrcsId;
    private String parentActivityId;

    public String getOptComment() {
        return optComment;
    }

    public void setOptComment(String optComment) {
        this.optComment = optComment;
    }

    public boolean isCallActivity() {
        return isCallActivity;
    }

    public void setCallActivity(boolean isCallActivity) {
        this.isCallActivity = isCallActivity;
    }

    public String getPrcsId() {
        return prcsId;
    }

    public void setPrcsId(String prcsId) {
        this.prcsId = prcsId;
    }

    public String getPrcsName() {
        return prcsName;
    }

    public void setPrcsName(String prcsName) {
        this.prcsName = prcsName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getOptUser() {
        return optUser;
    }

    public void setOptUser(String optUser) {
        this.optUser = optUser;
    }

    public String getOptDesc() {
        return optDesc;
    }

    public void setOptDesc(String optDesc) {
        this.optDesc = optDesc;
    }

    public String getOptReason() {
        return optReason;
    }

    public void setOptReason(String optReason) {
        this.optReason = optReason;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getElapsed() {
        return elapsed;
    }

    public void setElapsed(String elapsed) {
        this.elapsed = elapsed;
    }

    public String getParentPrcsId() {
        return parentPrcsId;
    }

    public void setParentPrcsId(String parentPrcsId) {
        this.parentPrcsId = parentPrcsId;
    }

    public String getParentActivityId() {
        return parentActivityId;
    }

    public void setParentActivityId(String parentTaskId) {
        this.parentActivityId = parentTaskId;
    }
}
