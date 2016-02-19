/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.breezee.bpm.conf;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Joram Barrez
 */
public class DataConfiguration {

    protected static final Logger LOGGER = LoggerFactory.getLogger(DataConfiguration.class);

    @Autowired
    protected RepositoryService repositoryService;

    @PostConstruct
    public void init() {
        LOGGER.info("Initializing process definitions");
        initProcessDefinitions();
    }

    /**
     * 初始化指定目录下的所有bpmn文件
     */
    protected void initProcessDefinitions() {
        //diagram/product/simple-product.bpmn
        try {
            String[] ss = new String[]{"diagram/simple-product.bpmn"};
            Map<String, DeploymentBuilder> m = new HashMap<>();
            //对流程图文件，按照路径进行分组
            String deploymentName;
            DeploymentBuilder db = null;
            int i = 0;
            List<Deployment> deploymentList;
            for (String path : ss) {
                if(path.lastIndexOf("/")<0)
                    continue;
                deploymentName = path.substring(0, path.lastIndexOf("/"));
                deploymentList = repositoryService.createDeploymentQuery().deploymentName(deploymentName).list();
                if (deploymentList != null && deploymentList.size() > 0)
                    continue;
                db = m.get(deploymentName);
                if (db == null) {
//                    repositoryService.createDeploymentQuery().deploymentName(deploymentName);
                    db = repositoryService.createDeployment().name(deploymentName);
                    m.put(deploymentName, db);
                }
                LOGGER.info("loading bpm xml file:" + path);
                db = db.addClasspathResource(path);
                i++;
            }
            if (i > 0)
                db.deploy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
