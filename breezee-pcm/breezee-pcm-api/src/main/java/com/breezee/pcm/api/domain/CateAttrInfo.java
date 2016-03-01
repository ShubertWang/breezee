/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.pcm.api.domain;

import com.breezee.common.BaseInfo;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by Silence on 2016/2/7.
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CateAttrInfo extends BaseInfo {

    private Long attrId;

    private String attrType;

    /**
     * 可以被继承
     */
    private boolean inheritable = true;

    /**
     * 默认值
     */
    private String defaultValue = null;

    /**
     * 允许为空
     */
    private boolean nullable = false;

    /**
     * 是否显示
     */
    private boolean display = true;

    /**
     * 排序，如果小于0，则使用define的排序
     */
    private int orderNo = -1;

    private Long sourceCateId;

    private String enumCode;

    private String unitCode;

    public Long getAttrId() {
        return attrId;
    }

    public void setAttrId(Long attrId) {
        this.attrId = attrId;
    }

    public boolean isInheritable() {
        return inheritable;
    }

    public void setInheritable(boolean inheritable) {
        this.inheritable = inheritable;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public String getAttrType() {
        return attrType;
    }

    public void setAttrType(String attrType) {
        this.attrType = attrType;
    }

    public Long getSourceCateId() {
        return sourceCateId;
    }

    public void setSourceCateId(Long sourceCateId) {
        this.sourceCateId = sourceCateId;
    }

    public String getEnumCode() {
        return enumCode;
    }

    public void setEnumCode(String enumCode) {
        this.enumCode = enumCode;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }
}
