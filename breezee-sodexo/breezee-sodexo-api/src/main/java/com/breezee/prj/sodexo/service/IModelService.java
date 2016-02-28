package com.breezee.prj.sodexo.service;

import com.breezee.common.IServiceLayer;
import com.breezee.prj.sodexo.domain.ModelInfo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

/**
 * fafa
 * Created by Zhong, An-Jing on 2015/12/20.
 */
@Path("/model")
public interface IModelService extends IServiceLayer<ModelInfo> {

    @Path("/p/{id}")
    @GET
    List<ModelInfo> findByParentId(@PathParam("id")Long parentId);
}
