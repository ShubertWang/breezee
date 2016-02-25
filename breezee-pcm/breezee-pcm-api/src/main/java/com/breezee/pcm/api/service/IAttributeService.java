/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.pcm.api.service;

import com.breezee.common.IServiceLayer;
import com.breezee.common.PageInfo;
import com.breezee.common.PageResult;
import com.breezee.pcm.api.domain.AttributeInfo;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * 属性定义服务
 * Created by Silence on 2016/2/7.
 */
@Path("/attribute")
public interface IAttributeService extends IServiceLayer<AttributeInfo> {

    @Path("/excludeCate/{categoryId}")
    @POST
    PageResult<AttributeInfo> findAttrsNotForCateId(@PathParam("categoryId") Long categoryId, PageInfo pageInfo);
}
