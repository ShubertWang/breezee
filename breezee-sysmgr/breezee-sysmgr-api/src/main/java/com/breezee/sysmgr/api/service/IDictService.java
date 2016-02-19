/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sysmgr.api.service;

import com.breezee.common.IServiceLayer;
import com.breezee.sysmgr.api.domain.DictDetailInfo;
import com.breezee.sysmgr.api.domain.DictInfo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * 数据字典服务
 * Created by Silence on 2016/2/10.
 */
@Path("/dict")
public interface IDictService extends IServiceLayer<DictInfo> {

    /**
     * 获取字典明细信息
     * @param dictId
     * @param code
     * @return
     */
    DictDetailInfo findDictDetailByCode(Long dictId, String code);

    @Path("/code/{code}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<DictDetailInfo> findDictDetailByCode(@PathParam("code") String dictCode);
}
