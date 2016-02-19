/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sysmgr.entity;

import com.breezee.common.BaseInfo;

import javax.persistence.*;
import java.util.Date;

/**
 * cy
 * Created by Zhong, An-Jing on 2015/7/14.
 */
@Entity
@Table(name = "SYM_TF_PERMISSION")
public class PermissionEntity extends BaseInfo {

    private String authType;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PERM_ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "PERM_NAME", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    @Column(name = "PERM_CODE", unique = true, updatable = false, nullable = false, length = 64)
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

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }
}
