/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.prj.sodexo.domain;

import com.breezee.common.BaseInfo;

import java.util.Date;

/**
 * Created by Silence on 2016/2/16.
 */
public class SeatOrderInfo extends BaseInfo {

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

    /**
     * 联系手机
     */
    protected String phone;

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
}
