/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sodexo.api.domain;

import com.breezee.common.BaseInfo;

import java.util.Date;

/**
 * Created by Silence on 2016/2/16.
 */
public class SeatOrderInfo extends BaseInfo {

    protected Long userId;

    protected String userMobile;
    protected String userName;
    protected Date issueDate;

    /**
     * 餐线
     */
    protected String storeName;

    /**
     * 预计到达时间
     */
    protected Date reservedTime;

    /**
     * 人数
     */
    protected Integer personNum;

    /**
     * 是否能接受拼桌
     */
    protected boolean acceptShare;

    /**
     * 联系人
     */
    protected String contactPerson;

    protected String sex;

    /**
     * 联系手机
     */
    protected String phone;

    private Long taskId;
    private String procDefId = "seatProcess";
    private Long procsInsId;

    private String statusName;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Date getReservedTime() {
        return reservedTime;
    }

    public void setReservedTime(Date reservedTime) {
        this.reservedTime = reservedTime;
    }

    public Integer getPersonNum() {
        return personNum;
    }

    public void setPersonNum(Integer personNum) {
        this.personNum = personNum;
    }

    public boolean isAcceptShare() {
        return acceptShare;
    }

    public void setAcceptShare(boolean acceptShare) {
        this.acceptShare = acceptShare;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

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

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
