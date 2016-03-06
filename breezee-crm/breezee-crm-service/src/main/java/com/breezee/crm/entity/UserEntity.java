/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.crm.entity;

import com.breezee.common.BaseInfo;
import com.breezee.crm.api.domain.ShippingAddressInfo;
import com.breezee.crm.api.domain.UserInfo;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Silence on 2016/2/11.
 */
@Entity
@Table(name = "CRM_TF_ACCOUNT")
public class UserEntity extends BaseInfo {

    /**
     * 类型：
     */
    protected String type;

    protected String password;

    protected String sex;

    protected String address;

    protected String tag;

    /**
     * 个性签名
     */
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

    protected String userJob;

    protected Set<ShippingAddressEntity> shippingAddresses;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "USER_NAME", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    @Column(name = "USER_CODE", unique = true, updatable = false, nullable = false, length = 64)
    public String getCode() {
        return code;
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

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getUserJob() {
        return userJob;
    }

    public void setUserJob(String userJob) {
        this.userJob = userJob;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @OrderBy(value = "updateTime DESC")
    public Set<ShippingAddressEntity> getShippingAddresses() {
        return shippingAddresses;
    }

    public void setShippingAddresses(Set<ShippingAddressEntity> shippingAddresses) {
        this.shippingAddresses = shippingAddresses;
    }

    public void addShippingAddress(ShippingAddressEntity se){
        if(this.shippingAddresses==null)
            this.shippingAddresses = new HashSet<>();
        se.setUser(this);
        this.shippingAddresses.add(se);
    }

    public UserInfo toInfo() {
        UserInfo info = new UserInfo();
        cloneAttributeTo(info);
        info.setAddress(this.getAddress());
        info.setCompany(this.getCompany());
        info.setDn(this.getDn());
        info.setEmail(this.getEmail());
        info.setIdCard(this.getIdCard());
        info.setPassword(this.getPassword());
        info.setPhone(this.getPhone());
        info.setSex(this.getSex());
        info.setSign(this.getSign());
        info.setStar(this.getStar());
        info.setTag(this.getTag());
        info.setType(this.getType());
        info.setWechat(this.getWechat());
        info.setOrgId(this.getOrgId());
        info.setAccountId(this.getAccountId());
        info.setUserJob(this.getUserJob());
        if (this.getShippingAddresses()!=null && this.getShippingAddresses().size()>0){
            this.getShippingAddresses().forEach(a->{
                ShippingAddressInfo si = a.toInfo();
                info.addShippingAddressInfo(si);
                if(a.isDefaultAddress()){
                    info.setDefaultAddress(si);
                }
            });
            if(info.getDefaultAddress()==null && info.getShippingAddressInfos().size()>0){
                info.setDefaultAddress(info.getShippingAddressInfos().get(0));
            }
        }
        return info;
    }

    public UserEntity parse(UserInfo info) {
        info.cloneAttributeTo(this);
        this.setAddress(info.getAddress());
        this.setCompany(info.getCompany());
        this.setDn(info.getDn());
        this.setEmail(info.getEmail());
        this.setIdCard(info.getIdCard());
        this.setPassword(info.getPassword());
        this.setPhone(info.getPhone());
        this.setSex(info.getSex());
        this.setSign(info.getSign());
        this.setStar(info.getStar());
        this.setTag(info.getTag());
        this.setType(info.getType());
        this.setWechat(info.getWechat());
        this.setOrgId(info.getOrgId());
        this.setAccountId(info.getAccountId());
        this.setUserJob(info.getUserJob());
        if(info.getShippingAddressInfos()!=null && info.getShippingAddressInfos().size()>0) {
            info.getShippingAddressInfos().forEach(a->{
                this.addShippingAddress(new ShippingAddressEntity().parse(a));
            });
        }
        return this;
    }
}
