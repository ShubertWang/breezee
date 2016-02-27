/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.oms.entity;

import com.breezee.common.BaseInfo;
import com.breezee.common.types.Quantity;
import com.breezee.oms.api.domain.InventoryInfo;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Silence on 2016/2/12.
 */
@Entity
@Table(name = "OMS_TF_INVENTORY")
public class InventoryEntity extends BaseInfo {
    private String locationId;
    private String skuId;
    private String unitCode;
    private int quantity;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INVENT_ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public String getRemark() {
        return remark;
    }

    public String getTenantId() {
        return tenantId;
    }

    public String getCreator() {
        return creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getUpdator() {
        return updator;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public int getStatus() {
        return status;
    }

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

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public InventoryInfo toInfo(){
        InventoryInfo info = new InventoryInfo();
        cloneAttribute(info);
        info.setSkuId(this.getSkuId());
        info.setLocationId(this.getLocationId());
        info.setQuantity(new Quantity(this.getUnitCode(),this.getQuantity()));
        return info;
    }

    public InventoryEntity parse(InventoryInfo info){
        info.cloneAttribute(this);
        this.setLocationId(info.getLocationId());
        this.setSkuId(info.getSkuId());
        this.setUnitCode(info.getQuantity().getUnitCode());
        this.setQuantity(info.getQuantity().getValue());
        return this;
    }
}
