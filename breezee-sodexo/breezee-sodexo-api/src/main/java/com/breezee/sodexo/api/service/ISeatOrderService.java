/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sodexo.api.service;

import com.breezee.common.IServiceLayer;
import com.breezee.sodexo.api.domain.SeatOrderInfo;

import javax.ws.rs.Path;

/**
 * Created by Silence on 2016/2/16.
 */
@Path("/seatOrder")
public interface ISeatOrderService extends IServiceLayer<SeatOrderInfo> {
}
