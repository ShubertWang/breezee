/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sysmgr.api.domain;

import com.breezee.common.TreeObject;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Created by Silence on 2016/2/12.
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class OrganizationInfo extends TreeObject<OrganizationInfo> {

    protected String type="leaf";

    protected List<Long> accounts;

    public String getType() {
        return leaf?"leaf":"folder";
    }

    public List<Long> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Long> accounts) {
        this.accounts = accounts;
    }
}
