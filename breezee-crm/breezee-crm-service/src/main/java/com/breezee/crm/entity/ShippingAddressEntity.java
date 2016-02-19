/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.crm.entity;

import com.breezee.common.BaseInfo;
import com.breezee.crm.api.domain.ShippingAddressInfo;

import javax.persistence.*;
import java.util.Date;

/**
 * 收获地址
 * Created by Silence on 2016/2/11.
 */
@Entity
@Table(name = "CRM_TF_USER_ADDRESS")
public class ShippingAddressEntity extends BaseInfo {

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

    protected UserEntity user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADDR_ID", unique = true, nullable = false)
    public Long getId() {
        return id;
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

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public ShippingAddressInfo toInfo() {
        ShippingAddressInfo info = (ShippingAddressInfo) this.clone();
        info.setCity(this.getCity());
        info.setConsigneeAddress(this.getConsigneeAddress());
        info.setConsigneeAlias(this.getConsigneeAlias());
        info.setConsigneeEmail(this.getConsigneeEmail());
        info.setConsigneeMobile(this.getConsigneeMobile());
        info.setConsigneeName(this.getConsigneeName());
        info.setConsigneePhone(this.getConsigneePhone());
        info.setCountryIso(this.getCountryIso());
        info.setCounty(this.getCounty());
        info.setDefaultAddress(this.isDefaultAddress());
        info.setProvince(this.getProvince());
        info.setTown(this.getTown());
        return info;
    }

    public ShippingAddressEntity parse(ShippingAddressInfo info) {
        info.cloneAttribute(this);
        this.setCity(info.getCity());
        this.setConsigneeAddress(info.getConsigneeAddress());
        this.setConsigneeAlias(info.getConsigneeAlias());
        this.setConsigneeEmail(info.getConsigneeEmail());
        this.setConsigneeMobile(info.getConsigneeMobile());
        this.setConsigneeName(info.getConsigneeName());
        this.setConsigneePhone(info.getConsigneePhone());
        this.setCountryIso(info.getCountryIso());
        this.setCounty(info.getCounty());
        this.setDefaultAddress(info.isDefaultAddress());
        this.setProvince(info.getProvince());
        this.setTown(info.getTown());
        return this;
    }
}
