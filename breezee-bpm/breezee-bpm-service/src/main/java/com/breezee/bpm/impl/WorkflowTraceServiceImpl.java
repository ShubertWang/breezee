/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.bpm.impl;

import com.breezee.bpm.api.domain.TraceInfo;
import com.breezee.bpm.api.service.IWorkflowTraceService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Silence on 2016/2/3.
 */
@Service("workflowTraceService")
public class WorkflowTraceServiceImpl implements IWorkflowTraceService {

    @Resource
    private RepositoryService repositoryService;

    @Override
    public List<TraceInfo> traceProcess(String processInstanceId) throws Exception {
        return null;
    }

    @Override
    public byte[] processInstanceChart(String processInstanceId) {
        //TODO:
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processInstanceId)
                .singleResult();
        String diagramResourceName = processDefinition.getDiagramResourceName();
        InputStream imageStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), diagramResourceName);
//输出
        int len = 0;
        byte[] b = new byte[1024];
        try {
            imageStream.read(b, 0, 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }
}
