/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.pcm.api.service;

import com.breezee.common.IServiceLayer;
import com.breezee.pcm.api.domain.ProductInfo;

import javax.ws.rs.Path;
import java.util.List;

/**
 * 产品服务
 * Created by Silence on 2016/2/7.
 */
@Path("/product")
public interface IProductService extends IServiceLayer<ProductInfo> {

    /**
     * 获取指定品类下的所有产品
     * @param cateId
     * @param rec 是否递归查询子类
     * @return
     */
    List<ProductInfo> findProductsByCateId(Long cateId, boolean rec);
}
