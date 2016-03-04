/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sodexo.api.service;

import com.breezee.common.IServiceLayer;
import com.breezee.sodexo.api.domain.FoodLineInfo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

/**
 * Created by Silence on 2016/2/13.
 */
@Path("/foodLine")
public interface IFoodLineService extends IServiceLayer<FoodLineInfo> {

    @Path("/messhallId/{messhallId}")
    @GET
    List<FoodLineInfo> findByMesshallId(@PathParam("messhallId") Long messhallId);

    @Path("/site/{site}")
    @GET
    List<FoodLineInfo> findBySite(@PathParam("site")String site);

    @Path("/site/{site}/{shipping}")
    @GET
    List<FoodLineInfo> findBySiteAndShipping(@PathParam("site")String site, @PathParam("shipping")String shipping);

    @Path("/orgId/{orgId}")
    @GET
    FoodLineInfo findByOrgId(@PathParam("orgId") Long orgId);

    @Path("/code/{code}")
    @GET
    FoodLineInfo findByCode(@PathParam("code") String code);
}
