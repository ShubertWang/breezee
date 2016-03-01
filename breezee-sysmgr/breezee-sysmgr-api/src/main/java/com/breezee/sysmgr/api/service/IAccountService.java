/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sysmgr.api.service;

import com.breezee.common.IServiceLayer;
import com.breezee.common.PageInfo;
import com.breezee.common.PageResult;
import com.breezee.sysmgr.api.domain.AccountInfo;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Silence on 2016/2/12.
 */
@Path("/account")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface IAccountService extends IServiceLayer<AccountInfo> {

    @Path("/org/{orgId}")
    @POST
    PageResult<AccountInfo> findAccountsByOrgId(@PathParam("orgId") Long orgId, PageInfo pageInfo);

    @Path("/excludeOrg/{orgId}")
    @POST
    PageResult<AccountInfo> findAccountsNotOrgId(@PathParam("orgId") Long orgId, PageInfo pageInfo);

    @Path("/role/{roleId}")
    @POST
    PageResult<AccountInfo> findAccountsByRoleId(@PathParam("roleId") Long roleId, PageInfo pageInfo);

    @Path("/excludeRole/{roleId}")
    @POST
    PageResult<AccountInfo> findAccountsNotRoleId(@PathParam("roleId") Long roleId, PageInfo pageInfo);

    @Path("/status/{accountId}/{status}")
    @GET
    void updateAccountStatus(@PathParam("accountId") Long accountId, @PathParam("status") Integer status);

    @Path("/updatePassword")
    @POST
    AccountInfo updatePassword(AccountInfo info);

    @Path("/checkPassword")
    @POST
    AccountInfo checkPassword(AccountInfo info);

    AccountInfo findByCode(String code);
}
