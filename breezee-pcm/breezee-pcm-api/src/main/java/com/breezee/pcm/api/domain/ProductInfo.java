/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.pcm.api.domain;

import com.breezee.common.BaseInfo;
import com.breezee.common.types.Amount;
import com.breezee.common.types.Quantity;
import com.fasterxml.jackson.annotation.JsonAnyGetter;

import java.util.HashMap;
import java.util.Map;

/**
 * 领域对象 -- 产品信息
 * Created by Silence on 2016/2/6.
 */
public class ProductInfo extends BaseInfo {

    protected Long cateId;

    protected Amount basePrice;

    protected Quantity quantity;

    protected Map<String, Object> values=new HashMap<>();

    public Long getCateId() {
        return cateId;
    }

    public void setCateId(Long cateId) {
        this.cateId = cateId;
    }

    @JsonAnyGetter
    public Map<String, Object> getValues() {
        return values;
    }

    public void setValues(Map<String, Object> values) {
        this.values = values;
    }

    public Amount getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Amount basePrice) {
        this.basePrice = basePrice;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public void setQuantity(Quantity quantity) {
        this.quantity = quantity;
    }
}
