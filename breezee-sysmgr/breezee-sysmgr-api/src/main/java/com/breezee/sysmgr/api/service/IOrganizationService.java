/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sysmgr.api.service;

import com.breezee.common.IServiceLayer;
import com.breezee.sysmgr.api.domain.OrganizationInfo;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Silence on 2016/2/13.
 */
@Path("/organization")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface IOrganizationService extends IServiceLayer<OrganizationInfo> {

    @Path("/p/{id}")
    @GET
    List<OrganizationInfo> findOrganizationsByParentId(@PathParam("id")Long id);

    @Path("/acntRel")
    @PUT
    void updateOrganizationAccount(OrganizationInfo organizationInfo);
}
