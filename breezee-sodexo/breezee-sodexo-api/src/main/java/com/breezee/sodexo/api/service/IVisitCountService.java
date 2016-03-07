package com.breezee.sodexo.api.service;

import com.breezee.common.IServiceLayer;
import com.breezee.sodexo.api.domain.VisitCountInfo;

import javax.ws.rs.Path;

/**
 * Created by Silence on 2016/3/7.
 */
@Path("/visitCount")
public interface IVisitCountService extends IServiceLayer<VisitCountInfo> {
}
