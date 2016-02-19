/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.prj.sodexo.domain;

import com.breezee.common.BaseInfo;
import com.breezee.common.types.Amount;

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
     * 餐次：早，中，晚
     */
    protected String timer;

    /**
     * 堂吃、外送
     */
    protected String shipping;

    /**
     * 负责人
     */
    protected Long owner;

    /**
     * 此餐线的订餐，是否需要支付
     */
    protected boolean payment;

    /**
     * 此服务线的外送费用
     */
    protected Amount shippingPrice;

    /**
     * 服务线翻台时间
     */
    protected Double turnTime;

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

    public boolean isPayment() {
        return payment;
    }

    public void setPayment(boolean payment) {
        this.payment = payment;
    }

    public Amount getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(Amount shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public Double getTurnTime() {
        return turnTime;
    }

    public void setTurnTime(Double turnTime) {
        this.turnTime = turnTime;
    }
}
