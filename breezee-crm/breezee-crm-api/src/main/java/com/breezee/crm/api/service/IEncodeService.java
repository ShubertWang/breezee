package com.breezee.crm.api.service;

import com.breezee.common.IServiceLayer;
import com.breezee.crm.api.domain.EncodeInfo;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Silence on 2016/2/28.
 */
@Path("/encode")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface IEncodeService extends IServiceLayer<EncodeInfo> {

    @Path("/site/{site}")
    @GET
    List<EncodeInfo> findBySite(@PathParam("site") String site);

    @Path("/validate/{site}/{code}")
    @GET
    EncodeInfo findBySiteAndCode(@PathParam("site") String site, @PathParam("code") String code);
}
