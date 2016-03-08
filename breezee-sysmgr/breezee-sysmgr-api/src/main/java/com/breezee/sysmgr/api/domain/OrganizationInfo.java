/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sysmgr.api.domain;

import com.breezee.common.TreeObject;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Silence on 2016/2/12.
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class OrganizationInfo extends TreeObject<OrganizationInfo> {

    protected String type="leaf";

    protected List<Long> accounts;

    protected List<Map<String,String>> serviceType;

    public String getType() {
        return leaf?"leaf":"folder";
    }

    public List<Long> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Long> accounts) {
        this.accounts = accounts;
    }

    public List<Map<String, String>> getServiceType() {
        this.serviceType = new ArrayList<>();
        this.serviceType.add(Collections.singletonMap("birthdayParty","生日会"));
        this.serviceType.add(Collections.singletonMap("teamBuilding","部门会议订餐"));
        this.serviceType.add(Collections.singletonMap("selfBuilding","自助餐"));
        this.serviceType.add(Collections.singletonMap("coffeeBreak","茶歇"));
        return serviceType;
    }

    public void setServiceType(List<Map<String, String>> serviceType) {
        this.serviceType = serviceType;
    }
}
