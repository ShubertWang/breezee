package com.breezee.sodexo.api.service;

import com.breezee.common.IServiceLayer;
import com.breezee.sodexo.api.domain.CommentInfo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * Created by Silence on 2016/3/3.
 */
@Path("/comment")
public interface ICommentService extends IServiceLayer<CommentInfo> {

    @Path("/countObject/{objectId}/{value}")
    @GET
    long countObject(@PathParam("objectId") String objectId, @PathParam("value") Integer value);
}
