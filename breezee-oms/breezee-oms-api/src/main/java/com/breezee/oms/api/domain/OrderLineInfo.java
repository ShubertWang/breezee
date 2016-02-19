/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.oms.api.domain;

import com.breezee.common.BaseInfo;
import com.breezee.common.types.Amount;
import com.breezee.common.types.Quantity;

/**
 * 订单行信息对象
 * Created by Silence on 2016/2/11.
 */
public class OrderLineInfo extends BaseInfo {

    private Long orderId;

    private String skuId;

    private String location;

    private String note;

    private Quantity quantity;

    private Amount unitPrice;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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

    public Quantity getQuantity() {
        return quantity;
    }

    public void setQuantity(Quantity quantity) {
        this.quantity = quantity;
    }

    public Amount getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Amount unitPrice) {
        this.unitPrice = unitPrice;
    }
}
