/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sysmgr.entity;

import com.breezee.common.BaseInfo;
import com.breezee.sysmgr.api.domain.AccountInfo;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * cy-auth
 * Created by Zhong, An-Jing on 2015/6/15.
 */
@Entity
@Table(name = "SYM_TF_ACCOUNT")
public class AccountEntity extends BaseInfo {

    private String password;

    /**
     * 员工内型，例如：正式的，临时的，借调的等
     */
    private String type;
    /**
     * 工作岗位
     */
    private String job;
    private String sex;
    private String phone;
    private String email;
    private String mobile;
    private String wechat;
    private String qq;
    private String address;
    /**
     * 一个人只能属于一个部门
     */
    private OrganizationEntity organization;

    /**
     * 一个人可以有多个角色
     */
    private Set<RoleEntity> roles;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACNT_ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "ACNT_NAME", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    @Column(name = "ACNT_CODE", unique = true, updatable = false, nullable = false, length = 64)
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "SYM_TF_ACNT_ORG",
            joinColumns = @JoinColumn(name = "ACNT_ID", referencedColumnName = "ACNT_ID"),
            inverseJoinColumns = @JoinColumn(name = "ORG_ID", referencedColumnName = "ORG_ID"))
    @NotFound(action= NotFoundAction.IGNORE)
    public OrganizationEntity getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationEntity organization) {
        this.organization = organization;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "SYM_TF_ACNT_ROLE",
            joinColumns = @JoinColumn(name = "ACNT_ID", referencedColumnName = "ACNT_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID"))
    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    public void addRole(RoleEntity role){
        if(role == null)
            return;
        if(this.roles==null)
            this.roles = new HashSet<>();
        this.roles.add(role);
    }

    public AccountInfo toInfo(){
        AccountInfo info = new AccountInfo();
        cloneAttributeTo(info);
        info.setAddress(this.getAddress());
        info.setEmail(this.getEmail());
        info.setJob(this.getJob());
        info.setMobile(this.getMobile());
        info.setPassword(this.getPassword());
        info.setPhone(this.getPhone());
        info.setQq(this.getQq());
        info.setSex(this.getSex());
        info.setType(this.getType());
        info.setWechat(this.getWechat());
        if(this.getOrganization()!=null) {
            info.setOrgId(this.getOrganization().getId());
            info.setOrgName(this.getOrganization().getName());
            info.setOrgCode(this.getOrganization().getCode());
        }
        if(this.getRoles()!=null && this.getRoles().size()>0){
            this.getRoles().forEach(a->{
                info.addRole(a.getCode());
            });
        }
        return info;
    }

    public AccountEntity parse(AccountInfo info){
        info.cloneAttributeTo(this);
        this.setAddress(info.getAddress());
        this.setEmail(info.getEmail());
        this.setJob(info.getJob());
        this.setMobile(info.getMobile());
        this.setPassword(info.getPassword());
        this.setPhone(info.getPhone());
        this.setQq(info.getQq());
        this.setSex(info.getSex());
        this.setType(info.getType());
        this.setWechat(info.getWechat());
        return this;
    }
}
