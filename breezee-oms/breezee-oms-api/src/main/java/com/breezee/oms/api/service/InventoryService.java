/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.oms.api.service;

import com.breezee.common.IServiceLayer;
import com.breezee.oms.api.domain.InventoryInfo;

import java.util.List;

/**
 * 库存服务
 * Created by Silence on 2016/2/12.
 */
public interface InventoryService extends IServiceLayer<InventoryInfo> {

    List<InventoryInfo> findInventoryBySkuId(String skuId);

    List<InventoryInfo> findInventoryBySkuIdAndLocationId(String skuId,String locationId);

}
