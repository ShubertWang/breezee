package com.breezee.sodexo.api.service;

import com.breezee.common.IServiceLayer;
import com.breezee.sodexo.api.domain.OtherRequestInfo;

import javax.ws.rs.Path;

/**
 * Created by Silence on 2016/3/5.
 */
@Path("/otherRequest")
public interface IOtherRequestService extends IServiceLayer<OtherRequestInfo> {
}
