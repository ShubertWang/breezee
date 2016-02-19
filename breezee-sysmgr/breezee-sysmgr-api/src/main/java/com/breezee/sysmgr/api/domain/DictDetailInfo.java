/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sysmgr.api.domain;

import com.breezee.common.BaseInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Silence on 2016/2/10.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DictDetailInfo extends BaseInfo {

    protected Integer orderNo;

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }
}
