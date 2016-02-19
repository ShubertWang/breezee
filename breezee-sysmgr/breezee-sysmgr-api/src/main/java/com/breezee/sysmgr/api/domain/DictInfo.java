/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sysmgr.api.domain;

import com.breezee.common.BaseInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Silence on 2016/2/10.
 */
public class DictInfo extends BaseInfo {
    protected List<DictDetailInfo> detailInfos = new ArrayList<>();

    public List<DictDetailInfo> getDetailInfos() {
        return detailInfos;
    }

    public void setDetailInfos(List<DictDetailInfo> detailInfos) {
        this.detailInfos = detailInfos;
    }
}
