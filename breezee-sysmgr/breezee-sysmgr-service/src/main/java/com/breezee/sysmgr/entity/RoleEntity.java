/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sysmgr.entity;

import com.breezee.common.BaseInfo;
import com.breezee.sysmgr.api.domain.RoleInfo;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * cy-auth
 * Created by Zhong, An-Jing on 2015/6/15.
 */
@Entity
@Table(name = "SYM_TF_ROLE")
public class RoleEntity extends BaseInfo {

    protected String permits;
    protected int orderNo;
    protected Set<AccountEntity> accounts;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "ROLE_NAME", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    @Column(name = "ROLE_CODE", unique = true, updatable = false, nullable = false, length = 64)
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

    public String getPermits() {
        return permits;
    }

    public void setPermits(String permits) {
        this.permits = permits;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    @ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
    @OrderBy("code ASC")
    public Set<AccountEntity> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<AccountEntity> accounts) {
        this.accounts = accounts;
    }

    public RoleInfo toInfo(){
        RoleInfo roleInfo = new RoleInfo();
        cloneAttributeTo(roleInfo);
        roleInfo.setPermits(this.getPermits());
        return roleInfo;
    }

    public RoleEntity parse(RoleInfo info){
        info.cloneAttributeTo(this);
        this.setPermits(info.getPermits());
        return this;
    }
}
