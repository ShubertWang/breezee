/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.prj.sodexo.service;

import com.breezee.common.IServiceLayer;
import com.breezee.prj.sodexo.domain.FoodLineInfo;

import javax.ws.rs.Path;

/**
 * Created by Silence on 2016/2/13.
 */
@Path("/foodLine")
public interface IFoodLineService extends IServiceLayer<FoodLineInfo> {
}
