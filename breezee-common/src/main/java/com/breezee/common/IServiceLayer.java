/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.common;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

/**
 * Service Layer higher interface
 * Created by Silence on 2016/2/7.
 */
public interface IServiceLayer<T extends BaseInfo> {

    /**
     * 保存页面对象
     * @param t
     */
    @Path("/")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    T saveInfo(T t);

    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    T deleteById(@PathParam("id") Long id);

    /**
     * 获取指定Id的对象
     * @param id
     * @return
     */
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    T findInfoById(@PathParam("id") Long id);

    /**
     * 列出所有的指定类型对象
     * @param m
     * @return
     */
    @Path("/list")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    List<T> listAll(Map<String,Object> m);

    /**
     * 分页获取所有指定类型的对象
     * @param m
     * @param pageInfo
     * @return
     */
    @Path("/page")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
    PageResult<T> pageAll(Map<String,Object> m, PageInfo pageInfo);
}
