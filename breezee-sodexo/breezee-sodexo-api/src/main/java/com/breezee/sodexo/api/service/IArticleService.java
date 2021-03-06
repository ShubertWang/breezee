package com.breezee.sodexo.api.service;

import com.breezee.common.IServiceLayer;
import com.breezee.common.PageInfo;
import com.breezee.common.PageResult;
import com.breezee.sodexo.api.domain.ArticleInfo;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * fafa
 * Created by Zhong, An-Jing on 2015/12/20.
 */
@Path("/article")
public interface IArticleService extends IServiceLayer<ArticleInfo> {

    @Path("/modelId/{modelId}")
    @POST
    PageResult<ArticleInfo> findByModelId(@PathParam("modelId") Long modelId, PageInfo page);

    @Path("/modelCode/{modelCode}")
    @POST
    PageResult<ArticleInfo> findByModelCode(@PathParam("modelCode") String modelCode, PageInfo page);
}
