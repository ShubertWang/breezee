/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.crm.api.service;

import com.breezee.common.IServiceLayer;
import com.breezee.crm.api.domain.ShippingAddressInfo;
import com.breezee.crm.api.domain.UserInfo;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

/**
 * Created by Silence on 2016/2/11.
 */
@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface IUserService extends IServiceLayer<UserInfo> {

    @Path("/shippingAddress")
    @PUT
    Map<String,Object> saveShippingAddress(ShippingAddressInfo addressInfo);

    @Path("/code/{code}")
    @GET
    UserInfo findByCode(@PathParam("code") String code);

    /**
     * 获取人员的收货地址
     * @param userId
     * @return
     */
    @Path("/shippingAddress/user/{userId}")
    @GET
    List<ShippingAddressInfo> findShippingAddress(@PathParam("userId") Long userId);

    @Path("/shippingAddress/user")
    @POST
    UserInfo employeeValidate(UserInfo info);

    @Path("/registerSite")
    @POST
    UserInfo registerSite(UserInfo info);

}
