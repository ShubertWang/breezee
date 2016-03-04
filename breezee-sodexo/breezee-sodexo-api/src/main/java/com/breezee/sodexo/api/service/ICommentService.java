package com.breezee.sodexo.api.service;

import com.breezee.common.IServiceLayer;
import com.breezee.sodexo.api.domain.CommentInfo;

import javax.ws.rs.Path;

/**
 * Created by Silence on 2016/3/3.
 */
@Path("/comment")
public interface ICommentService extends IServiceLayer<CommentInfo> {



}
