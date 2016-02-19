/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sysmgr.api.domain;

import com.breezee.common.BaseInfo;

/**
 * Created by Silence on 2016/2/12.
 */
public class RoleInfo extends BaseInfo {

    protected String permits;
    protected int orderNo;

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
}
