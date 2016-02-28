/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.crm.api.domain;

import com.breezee.common.BaseInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Silence on 2016/2/11.
 */
public class UserInfo extends BaseInfo {

    protected String type;

    protected String password;

    protected String sex;

    protected String address;

    protected String tag;

    protected String sign;

    protected int star = 3;

    /**
     * 第三方系统的标识，例如餐卡号等
     */
    protected String dn;

    protected String company;

    protected String idCard;

    protected String wechat;

    protected String phone;

    protected String email;

    protected String orgId;

    protected String accountId;

    protected double balance = 0.00;

    protected Integer addressCount;

    protected boolean accountAble;

    protected List<ShippingAddressInfo> shippingAddressInfos = new ArrayList<>();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public List<ShippingAddressInfo> getShippingAddressInfos() {
        return shippingAddressInfos;
    }

    public void setShippingAddressInfos(List<ShippingAddressInfo> shippingAddressInfos) {
        this.shippingAddressInfos = shippingAddressInfos;
    }

    public void addShippingAddressInfo(ShippingAddressInfo sinfo){
        sinfo.setUserId(this.getId());
        if(this.shippingAddressInfos==null)
            this.shippingAddressInfos = new ArrayList<>();
        this.shippingAddressInfos.add(sinfo);
    }

    public Integer getAddressCount() {
        return shippingAddressInfos!=null?shippingAddressInfos.size():0;
    }


}
