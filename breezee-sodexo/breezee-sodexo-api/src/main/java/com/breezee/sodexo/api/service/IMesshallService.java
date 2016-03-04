/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sodexo.api.service;

import com.breezee.common.IServiceLayer;
import com.breezee.sodexo.api.domain.MesshallInfo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

/**
 * Created by Silence on 2016/2/13.
 */
@Path("/messhall")
public interface IMesshallService extends IServiceLayer<MesshallInfo> {

    @Path("/list/all")
    @GET
    List<MesshallInfo> findAll();

    @Path("/code/{code}")
    @GET
    MesshallInfo findByCode(@PathParam("code") String code);
}
