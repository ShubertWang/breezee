/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sysmgr.api.domain;

import com.breezee.common.BaseInfo;

import java.util.List;

/**
 * Created by Silence on 2016/2/12.
 */
public class RoleInfo extends BaseInfo {

    protected String permits;
    protected int orderNo;
    protected List<Long> accounts;

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

    public List<Long> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Long> accounts) {
        this.accounts = accounts;
    }
}
