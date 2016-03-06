/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.bpm.api.service;

import com.breezee.bpm.api.domain.WfHistoryInfo;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

/**
 * 流程跟踪服务
 * 主要用于流程图的展现
 * Created by Silence on 2016/2/3.
 */
@Path("/workflowTrace")
public interface IWorkflowTraceService {

    /**
     * 流程跟踪节点
     * @param processInstanceId 流程实例ID
     * @return
     * @throws Exception
     */
    @Path("/traceProcess/{processInstanceId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    List<Map<String, Object>> traceProcess(@PathParam("processInstanceId") String processInstanceId) throws Exception;

    /**
     * 子流程列表
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> traceProcess4Sub(String processInstanceId) throws Exception;

    /**
     * 流程跟踪图
     *
     * @param processInstanceId
     * @return
     */
    @Path("/processGraph/{procsInsId}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/octet-stream")
    byte[] processInstanceChart(@PathParam("procsInsId") String processInstanceId);

    /**
     * 流程实例的履历
     * @param processInstanceId
     * @return
     */
    List<WfHistoryInfo> processHistory(String processInstanceId);
}
