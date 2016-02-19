/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.crm.api.domain;

import com.breezee.common.BaseInfo;

/**
 * Created by Silence on 2016/2/11.
 */
public class ShippingAddressInfo extends BaseInfo {

    protected Long userId;

    /**
     * 收货人
     */
    protected String consigneeName;

    protected String countryIso;

    /**
     * 省
     */
    protected String province;

    /**
     * 市
     */
    protected String city;

    /**
     * 区
     */
    protected String county;

    /**
     * 镇
     */
    protected String town;

    /**
     * 详细地址
     */
    protected String consigneeAddress;

    /**
     * 手机号码
     */
    protected String consigneeMobile;

    /**
     * 电话号码
     */
    protected String consigneePhone;

    /**
     * 邮箱
     */
    protected String consigneeEmail;

    /**
     * 地址别名
     */
    protected String consigneeAlias;

    protected boolean defaultAddress;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getCountryIso() {
        return countryIso;
    }

    public void setCountryIso(String countryIso) {
        this.countryIso = countryIso;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress;
    }

    public String getConsigneeMobile() {
        return consigneeMobile;
    }

    public void setConsigneeMobile(String consigneeMobile) {
        this.consigneeMobile = consigneeMobile;
    }

    public String getConsigneePhone() {
        return consigneePhone;
    }

    public void setConsigneePhone(String consigneePhone) {
        this.consigneePhone = consigneePhone;
    }

    public String getConsigneeEmail() {
        return consigneeEmail;
    }

    public void setConsigneeEmail(String consigneeEmail) {
        this.consigneeEmail = consigneeEmail;
    }

    public String getConsigneeAlias() {
        return consigneeAlias;
    }

    public void setConsigneeAlias(String consigneeAlias) {
        this.consigneeAlias = consigneeAlias;
    }

    public boolean isDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(boolean defaultAddress) {
        this.defaultAddress = defaultAddress;
    }
}
