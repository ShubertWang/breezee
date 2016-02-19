/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.common.types;

import java.io.Serializable;

/**
 * Created by Silence on 2016/2/11.
 */
public class Amount implements ValueType<Amount>,Serializable {
    private String currencyCode;
    private Double value;

    public Amount() {
    }

    public Amount(String currencyCode, Double value) {
        this.currencyCode = currencyCode;
        this.value = value;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
