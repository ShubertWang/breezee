/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.pcm.api.domain;

import com.breezee.common.TreeObject;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * 领域对象--品类
 * Created by Silence on 2016/2/6.
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CategoryInfo extends TreeObject<CategoryInfo> {

    protected String type="leaf";

    protected String icon;

    protected int orderNo;

    protected List<ProductInfo> products;

    public CategoryInfo() {
    }

    public CategoryInfo(Long id) {
        this.id=id;
    }

    public CategoryInfo(String code,String name) {
        this.code = code;
        this.name = name;
    }

    private List<CateAttrInfo> cateAttrInfos;

    public String getType() {
        return leaf?"leaf":"folder";
    }

    public List<CateAttrInfo> getCateAttrInfos() {
        return cateAttrInfos;
    }

    public void setCateAttrInfos(List<CateAttrInfo> cateAttrInfos) {
        this.cateAttrInfos = cateAttrInfos;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<ProductInfo> getProducts() {
        return products;
    }

    public void setProducts(List<ProductInfo> products) {
        this.products = products;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }
}
