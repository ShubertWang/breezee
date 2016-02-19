/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.prj.sodexo.entity;

import com.breezee.common.types.Amount;
import com.breezee.prj.sodexo.domain.FoodLineInfo;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * 门店 * 餐次 * 堂吃/外卖==餐线
 * Created by Silence on 2016/2/13.
 */
@Entity
@Table(name = "PRJ_TD_FOODLINE")
public class FoodLineEntity extends FoodLineInfo {

    protected String currencyCode;

    protected Double shippingPriceE;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FDL_ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "FDL_NAME", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    @Column(name = "FDL_CODE", unique = true, updatable = false, nullable = false, length = 64)
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

    public Long getMesshallId() {
        return messhallId;
    }

    public String getTimer() {
        return timer;
    }

    public String getShipping() {
        return shipping;
    }

    public Long getOwner() {
        return owner;
    }

    public boolean isPayment() {
        return payment;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Double getShippingPriceE() {
        return shippingPriceE;
    }

    public void setShippingPriceE(Double shippingPriceE) {
        this.shippingPriceE = shippingPriceE;
    }

    public Double getTurnTime() {
        return turnTime;
    }

    public FoodLineInfo toInfo(){
        FoodLineInfo info = new FoodLineInfo();
        BeanUtils.copyProperties(this,info);
        info.setShippingPrice(new Amount(this.getCurrencyCode(),this.getShippingPriceE()));
        return info;
    }

    public FoodLineEntity parse(FoodLineInfo info){
        BeanUtils.copyProperties(info,this);
        this.setCurrencyCode(info.getShippingPrice().getCurrencyCode());
        this.setShippingPriceE(info.getShippingPrice().getValue());
        return this;
    }
}
