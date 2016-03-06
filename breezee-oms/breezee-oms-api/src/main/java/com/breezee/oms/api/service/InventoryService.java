/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.oms.api.service;

import com.breezee.common.IServiceLayer;
import com.breezee.oms.api.domain.InventoryInfo;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

/**
 * 库存服务
 * Created by Silence on 2016/2/12.
 */
@Path("/inventory")
public interface InventoryService extends IServiceLayer<InventoryInfo> {

    @Path("/skuId/{skuId}")
    @GET
    List<InventoryInfo> findInventoryBySkuId(@PathParam("skuId") String skuId);

    @Path("/skuId/{locationId}")
    @GET
    List<InventoryInfo> findInventoryByLocationId(@PathParam("locationId") String locationId);

    @Path("/skuId/{skuId}/{locationId}")
    @GET
    InventoryInfo findInventoryBySkuIdAndLocationId(@PathParam("skuId")String skuId,@PathParam("locationId")String locationId);

    @Path("/updateInventory")
    @PUT
    void updateInventoryBySkuId(InventoryInfo inventoryInfo);

}
