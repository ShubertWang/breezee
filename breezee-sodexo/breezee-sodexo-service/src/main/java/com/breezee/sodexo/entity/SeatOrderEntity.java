/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sodexo.entity;

import com.breezee.sodexo.api.domain.SeatOrderInfo;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Silence on 2016/2/16.
 */
@Entity
@Table(name = "SDX_TF_SEAT_ORDER")
public class SeatOrderEntity extends SeatOrderInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEAT_ORDER_ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "SEAT_ORDER_NAME", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    public String getTenantId() {
        return tenantId;
    }

    public String getCreator() {
        return creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getUpdator() {
        return updator;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public int getStatus() {
        return status;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getReservedTime() {
        return reservedTime;
    }

    public Integer getPersonNum() {
        return personNum;
    }

    public boolean isAcceptShare() {
        return acceptShare;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public String getPhone() {
        return phone;
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

    public SeatOrderInfo toInfo() {
        SeatOrderInfo info = new SeatOrderInfo();
        BeanUtils.copyProperties(this, info);
        return info;
    }

    public SeatOrderEntity parse(SeatOrderInfo info) {
        BeanUtils.copyProperties(info, this);
        return this;
    }
}
