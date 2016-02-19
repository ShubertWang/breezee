/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.prj.sodexo.entity;

import com.breezee.prj.sodexo.domain.MesshallInfo;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * 餐厅门店
 * Created by Silence on 2016/2/13.
 */
@Entity
@Table(name = "PRJ_TD_MESSHALL")
public class MesshallEntity extends MesshallInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEH_ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "MEH_NAME", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    @Column(name = "MEH_CODE", unique = true, updatable = false, nullable = false, length = 64)
    public String getCode() {
        return code;
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

    public Long getOrgId() {
        return orgId;
    }

    public Integer getShopNum() {
        return shopNum;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public Long getDutyPerson() {
        return dutyPerson;
    }

    public MesshallInfo toInfo(){
        MesshallInfo info = new MesshallInfo();
        BeanUtils.copyProperties(this,info);
        return info;
    }

    public MesshallEntity parse(MesshallInfo info){
        BeanUtils.copyProperties(info,this);
        return this;
    }
}
