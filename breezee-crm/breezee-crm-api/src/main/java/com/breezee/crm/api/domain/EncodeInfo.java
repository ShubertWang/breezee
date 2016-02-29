package com.breezee.crm.api.domain;

import com.breezee.common.BaseInfo;

/**
 * Created by Silence on 2016/2/28.
 */

/**
 * 用户验证
 */
public class EncodeInfo extends BaseInfo {

    protected String site;

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
