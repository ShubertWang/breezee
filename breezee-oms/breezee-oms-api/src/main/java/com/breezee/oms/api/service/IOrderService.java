/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.oms.api.service;

import com.breezee.common.IServiceLayer;
import com.breezee.oms.api.domain.OrderInfo;

import javax.ws.rs.Path;

/**
 * Created by Silence on 2016/2/11.
 */
@Path("/order")
public interface IOrderService extends IServiceLayer<OrderInfo>{

}
