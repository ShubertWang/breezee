/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sysmgr.api.domain;

import com.breezee.common.BaseInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Silence on 2016/2/12.
 */
public class AccountInfo extends BaseInfo {

    private String password;

    private String oldPassword;

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

    private List<OrganizationInfo> organizations = new ArrayList<>();

    private List<String> roles = new ArrayList<>();

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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public void addRole(String roleId){
        this.roles.add(roleId);
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public List<OrganizationInfo> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<OrganizationInfo> organizations) {
        this.organizations = organizations;
    }

    public void addOrg(Long orgId,String orgCode,String orgName){
        OrganizationInfo info = new OrganizationInfo();
        info.setId(orgId);
        info.setCode(orgCode);
        info.setName(orgName);
        this.organizations.add(info);
    }
}
