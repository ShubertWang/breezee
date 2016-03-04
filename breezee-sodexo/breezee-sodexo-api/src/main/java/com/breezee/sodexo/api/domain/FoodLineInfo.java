/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sodexo.api.domain;

import com.breezee.common.BaseInfo;
import com.breezee.common.types.Amount;

import java.util.HashMap;

/**
 * 领域对象 -- 餐线
 * Created by Silence on 2016/2/13.
 */
public class FoodLineInfo extends BaseInfo {

    /**
     * 所属餐厅
     */
    protected Long messhallId;

    /**
     * 所属组织
     */
    protected Long orgId;

    protected String site;

    protected String orgName;

    /**
     * 餐次：早，中，晚
     */
    protected String timer;

    /**
     * 营业开始时间,9:00
     */
    protected String startTime;

    /**
     * 营业结束时间,11:00
     */
    protected String endTime;

    /**
     * 预计截止时间,10:00
     */
    protected String closeTime;

    /**
     * 预定提前量,min
     */
    protected Integer shiftNum;

    /**
     * 堂吃、外送
     */
    protected String shipping;

    /**
     * 负责人
     */
    protected Long owner;

    /**
     * 支付方式（多选：到付，微信支付，餐卡支付）逗号分开
     */
    protected String payType;

    /**
     * 此服务线的外送费用
     */
    protected Amount shippingPrice;

    /**
     * 服务线翻台时间
     */
    protected Integer turnTime;

    protected String telphone;

    protected String imageCode = "icon_ct_mr";

    protected MesshallInfo messhallInfo;

    public FoodLineInfo(){
        this.properties = new HashMap<>();
    }

    public Long getMesshallId() {
        return messhallId;
    }

    public void setMesshallId(Long messhallId) {
        this.messhallId = messhallId;
    }

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public Amount getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(Amount shippingPrice) {
        this.shippingPrice = shippingPrice;
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

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public Integer getShiftNum() {
        return shiftNum;
    }

    public void setShiftNum(Integer shiftNum) {
        this.shiftNum = shiftNum;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Integer getTurnTime() {
        return turnTime;
    }

    public void setTurnTime(Integer turnTime) {
        this.turnTime = turnTime;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getImageCode() {
        return imageCode;
    }

    public void setImageCode(String imageCode) {
        this.imageCode = imageCode;
    }

    public MesshallInfo getMesshallInfo() {
        return messhallInfo;
    }

    public void setMesshallInfo(MesshallInfo messhallInfo) {
        this.messhallInfo = messhallInfo;
    }
}
