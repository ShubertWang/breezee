/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.oms.entity;

import com.breezee.common.BaseInfo;
import com.breezee.common.types.Amount;
import com.breezee.common.types.Quantity;
import com.breezee.oms.api.domain.OrderLineInfo;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Silence on 2016/2/11.
 */
@Entity
@Table(name = "OMS_TF_ORDER_LINE")
public class OrderLineEntity extends BaseInfo {

    private String skuId;

    private String location;

    private String note;

    private Integer quantity;

    private String unitCode;

    private Double unitPrice;

    private String currencyCode;

    private OrderEntity order;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LINE_ID", unique = true, nullable = false)
    public Long getId() {
        return id;
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

    public String getRemark() {
        return remark;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "ORDER_ID", referencedColumnName = "ORDER_ID")
    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public OrderLineInfo toInfo(){
        OrderLineInfo info = new OrderLineInfo();
        cloneAttribute(info);
        info.setLocation(this.getLocation());
        info.setNote(this.getNote());
        info.setOrderId(this.getOrder().getId());
        info.setQuantity(new Quantity(this.getUnitCode(),this.getQuantity()));
        info.setSkuId(this.getSkuId());
        info.setUnitPrice(new Amount(this.getCurrencyCode(),this.getUnitPrice()));
        return info;
    }

    public OrderLineEntity parse(OrderLineInfo info){
        info.cloneAttribute(this);
        this.setSkuId(info.getSkuId());
        this.setNote(info.getNote());
        this.setCurrencyCode(info.getUnitPrice().getCurrencyCode());
        this.setLocation(info.getLocation());
        this.setQuantity(info.getQuantity().getValue());
        this.setUnitCode(info.getQuantity().getUnitCode());
        this.setUnitPrice(info.getUnitPrice().getValue());
        return this;
    }
}
