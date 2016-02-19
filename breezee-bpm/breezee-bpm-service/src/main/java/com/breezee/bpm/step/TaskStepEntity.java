/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */
package com.breezee.bpm.step;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 流程环节实体信息
 * Created by Silence on 2016/2/1.
 */
@Entity
@Table(name = "BPM_TF_TASK_STEP")
public class TaskStepEntity implements Serializable {

    private Long id;

    private Date optDate;

    private String optType;

    private String optReason;

    private String optComment;

    private String optDesc;

    private String optUser;

    private Long procInstId;

    private Long workItemId;

    private String updateBy;

    private Date updateDate;

    private String createBy;

    private Date createDate;

    private String deleteBy;

    private Date deleteDate;

    private Integer deleteFlag;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STEP_ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Long getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(Long procInstId) {
        this.procInstId = procInstId;
    }

    public Long getWorkItemId() {
        return workItemId;
    }

    public void setWorkItemId(Long workItemId) {
        this.workItemId = workItemId;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDeleteBy() {
        return deleteBy;
    }

    public void setDeleteBy(String deleteBy) {
        this.deleteBy = deleteBy;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
