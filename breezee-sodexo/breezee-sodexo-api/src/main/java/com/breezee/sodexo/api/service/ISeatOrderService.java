/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sodexo.api.service;

import com.breezee.common.IServiceLayer;
import com.breezee.common.PageInfo;
import com.breezee.common.PageResult;
import com.breezee.sodexo.api.domain.SeatOrderInfo;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * Created by Silence on 2016/2/16.
 */
@Path("/seatOrder")
public interface ISeatOrderService extends IServiceLayer<SeatOrderInfo> {

    void updateStatusAndNo(Long id,int status,String seatNo);

    @Path("/mySeat/{userId}")
    @POST
    PageResult<SeatOrderInfo> findMySeat(@PathParam("userId") Long userId, PageInfo pageInfo);
}
