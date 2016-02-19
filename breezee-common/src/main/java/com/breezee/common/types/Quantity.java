/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.common.types;

import java.io.Serializable;

/**
 * 数量
 * Created by Silence on 2016/2/11.
 */
public class Quantity implements ValueType<Quantity>,Serializable {
    private String unitCode;
    private int value;

    public Quantity() {
    }

    public Quantity(String unitCode, int value) {
        this.unitCode = unitCode;
        this.value = value;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
