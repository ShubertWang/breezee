/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.pcm.api.service;

import com.breezee.common.IServiceLayer;
import com.breezee.pcm.api.domain.AttributeInfo;

import javax.ws.rs.Path;

/**
 * 属性定义服务
 * Created by Silence on 2016/2/7.
 */
@Path("/attribute")
public interface IAttributeService extends IServiceLayer<AttributeInfo> {

}
