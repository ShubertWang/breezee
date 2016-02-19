/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.bpm.api.service;

import com.breezee.bpm.api.domain.TraceInfo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * 流程跟踪服务
 * 主要用于流程图的展现
 * Created by Silence on 2016/2/3.
 */
@Path("/workflowTrace")
public interface IWorkflowTraceService {

    /**
     * 流程跟踪节点
     *
     * @param processInstanceId 流程实例ID
     * @return
     * @throws Exception
     */
    List<TraceInfo> traceProcess(String processInstanceId) throws Exception;

    /**
     * 流程跟踪图
     *
     * @param processInstanceId
     * @return
     */
    @Path("/chart/{procsInsId}")
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    byte[] processInstanceChart(@PathParam("procsInsId") String processInstanceId);
}
