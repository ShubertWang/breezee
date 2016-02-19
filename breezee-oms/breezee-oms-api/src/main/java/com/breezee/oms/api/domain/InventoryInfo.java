/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.oms.api.domain;

import com.breezee.common.BaseInfo;
import com.breezee.common.types.Quantity;

/**
 * Created by Silence on 2016/2/12.
 */
public class InventoryInfo extends BaseInfo{
    private String locationId;
    private String skuId;
    private Quantity quantity;

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public void setQuantity(Quantity quantity) {
        this.quantity = quantity;
    }
}
