/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sysmgr.entity;

import com.breezee.common.BaseInfo;
import com.breezee.sysmgr.api.domain.OrganizationInfo;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * cy-auth Created by Zhong, An-Jing on 2015/6/15.
 */
@Entity
@Table(name = "SYM_TF_ORGANIZATION")
public class OrganizationEntity extends BaseInfo{

    private OrganizationEntity parent;
    private Set<AccountEntity> accounts;
    private Set<OrganizationEntity> children;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORG_ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "ORG_NAME", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    @Column(name = "ORG_CODE", unique = true, updatable = false, nullable = false, length = 64)
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

    @OneToOne
    @JoinColumn(name = "PARENT_ID", referencedColumnName = "ORG_ID")
    public OrganizationEntity getParent() {
        return parent;
    }

    public void setParent(OrganizationEntity parent) {
        this.parent = parent;
    }

    @OneToMany(mappedBy = "parent", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    public Set<OrganizationEntity> getChildren() {
        return children;
    }

    public void setChildren(Set<OrganizationEntity> children) {
        this.children = children;
    }

    @OneToMany(mappedBy = "organization", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    public Set<AccountEntity> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<AccountEntity> accounts) {
        this.accounts = accounts;
    }

    public OrganizationInfo toInfo(boolean loadChild){
        OrganizationInfo info = (OrganizationInfo) this.clone();
        if(loadChild && this.getChildren()!=null && this.getChildren().size()>0){
            this.getChildren().forEach(a->{
                info.getChildren().add(a.toInfo(false));
            });
        }
        return info;
    }

    public OrganizationEntity parse(OrganizationInfo categoryInfo){
        categoryInfo.cloneAttribute(this);
        return this;
    }
}
