/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.oms.api.service;

import com.breezee.common.IServiceLayer;
import com.breezee.common.PageInfo;
import com.breezee.common.PageResult;
import com.breezee.oms.api.domain.OrderInfo;

import javax.ws.rs.*;
import java.util.Collection;
import java.util.List;

/**
 * Created by Silence on 2016/2/11.
 */
@Path("/order")
public interface IOrderService extends IServiceLayer<OrderInfo> {

    OrderInfo findOrderInfoByCode(String code);

    List<OrderInfo> findOrderInfoListByCodes(Collection<String> codes);

    @Path("/myOrder/{userId}")
    @POST
    PageResult<OrderInfo> findMyOrder(@PathParam("userId") Long userId, PageInfo pageInfo);

    @Path("/orderPay/{orderId}")
    @GET
    void orderPay(@PathParam("orderId") String orderId, @QueryParam("payId") String payId);
}
