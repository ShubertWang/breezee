/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.pcm.api.domain;

import com.breezee.common.BaseInfo;
import com.breezee.common.types.Amount;
import com.breezee.common.types.Quantity;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.Map;

/**
 * 领域对象 -- 产品信息
 * Created by Silence on 2016/2/6.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductInfo extends BaseInfo {

    protected Long cateId;

    protected String cateName;

    protected Amount basePrice;

    protected Quantity quantity=new Quantity("",0);

    protected Map<String, Object> productData = new HashMap<>();

    public Long getCateId() {
        return cateId;
    }

    public void setCateId(Long cateId) {
        this.cateId = cateId;
    }

    public Map<String, Object> getProductData() {
        return productData;
    }

    public void setProductData(Map<String, Object> productData) {
        this.productData = productData;
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

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }
}
