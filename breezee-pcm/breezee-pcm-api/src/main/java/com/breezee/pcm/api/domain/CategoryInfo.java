/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.pcm.api.domain;

import com.breezee.common.TreeObject;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * 领域对象--品类
 * Created by Silence on 2016/2/6.
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CategoryInfo extends TreeObject<CategoryInfo> {

    private List<CateAttrInfo> cateAttrInfos;

    public List<CateAttrInfo> getCateAttrInfos() {
        return cateAttrInfos;
    }

    public void setCateAttrInfos(List<CateAttrInfo> cateAttrInfos) {
        this.cateAttrInfos = cateAttrInfos;
    }
}
