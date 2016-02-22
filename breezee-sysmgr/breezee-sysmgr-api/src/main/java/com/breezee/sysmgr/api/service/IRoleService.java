/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sysmgr.api.service;

import com.breezee.common.IServiceLayer;
import com.breezee.sysmgr.api.domain.RoleInfo;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by Silence on 2016/2/13.
 */
@Path("/role")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface IRoleService extends IServiceLayer<RoleInfo> {

    @Path("/acntRel")
    @PUT
    void updateRoleAccount(RoleInfo roleInfo);
}
