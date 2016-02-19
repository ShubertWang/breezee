/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.pcm.api.service;

import com.breezee.common.IServiceLayer;
import com.breezee.pcm.api.domain.CategoryInfo;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * 品类服务
 * Created by Silence on 2016/2/6.
 */
@Path("/category")
@Produces(MediaType.APPLICATION_JSON)
public interface ICategoryService extends IServiceLayer<CategoryInfo> {

    /**
     * 获取指定品类下的所有子品类
     * @param id
     * @return
     */
    @Path("/p/{id}")
    @GET
    List<CategoryInfo> findCategoryByParentId(@PathParam("id")Long id);

}
