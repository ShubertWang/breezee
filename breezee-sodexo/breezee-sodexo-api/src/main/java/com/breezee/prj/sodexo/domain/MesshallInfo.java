/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.prj.sodexo.domain;

import com.breezee.common.BaseInfo;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

/**
 * 领域对象 -- 餐厅
 * Created by Silence on 2016/2/13.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MesshallInfo extends BaseInfo {

    /**
     * 所属组织
     */
    protected Long orgId;

    protected String orgName;

    /**
     * 餐厅电话
     */
    protected String telephone;

    /**
     * 餐厅里面的店面数
     */
    protected Integer shopNum;

    /**
     * 营业时间
     */
    protected String startTime;

    /**
     * 结束时间
     */
    protected String endTime;

    /**
     * 开业时间
     */
    protected Date openDate;

    /**
     * 负责人
     */
    protected Long dutyPerson;

    protected String dutyName;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Integer getShopNum() {
        return shopNum;
    }

    public void setShopNum(Integer shopNum) {
        this.shopNum = shopNum;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public Long getDutyPerson() {
        return dutyPerson;
    }

    public void setDutyPerson(Long dutyPerson) {
        this.dutyPerson = dutyPerson;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getDutyName() {
        return dutyName;
    }

    public void setDutyName(String dutyName) {
        this.dutyName = dutyName;
    }
}
