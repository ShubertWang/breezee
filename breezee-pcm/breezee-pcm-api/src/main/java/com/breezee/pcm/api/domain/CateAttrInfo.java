/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.pcm.api.domain;

import com.breezee.common.BaseInfo;

/**
 * Created by Silence on 2016/2/7.
 */
public class CateAttrInfo extends BaseInfo {

    private Long attrId;

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
}
