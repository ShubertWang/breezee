/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sysmgr.api.service;

import com.breezee.common.IServiceLayer;
import com.breezee.sysmgr.api.domain.OrganizationInfo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

/**
 * Created by Silence on 2016/2/13.
 */
public interface IOrganizationService extends IServiceLayer<OrganizationInfo> {

    @Path("/p/{id}")
    @GET
    List<OrganizationInfo> findOrganizationsByParentId(@PathParam("id")Long id);
}
