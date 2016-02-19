/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.pcm.api.domain;

import com.breezee.common.BaseInfo;

/**
 * 领域对象 -- 属性信息
 * Created by Silence on 2016/2/6.
 */
public class AttributeInfo extends BaseInfo {

    /**
     * 属性的类型：目前支持String, Number, Integer, DateTime
     */
    protected String fieldType;

    /**
     * 参数
     * 例如枚举值得code，以json格式标识
     */
    protected String arguments;

    /**
     * 单位描述，如：GB
     */
    protected String unitCode;

    /**
     * 排序
     */
    protected int orderNo;

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getArguments() {
        return arguments;
    }

    public void setArguments(String arguments) {
        this.arguments = arguments;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }
}
