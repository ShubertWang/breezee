/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.bpm.conf;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.util.ReflectUtil;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


/**
 * @author Silence Zhong
 */
@Component
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
            String[] ss = diagramFiles();
            //对流程图文件，按照路径进行分组
            String deploymentName;
            DeploymentBuilder db = null;
            List<Deployment> deploymentList;
            for (String path : ss) {
                if (path.lastIndexOf("/") < 0)
                    continue;
                deploymentName = path.substring(path.lastIndexOf("/")+1);
//                    repositoryService.createDeploymentQuery().deploymentName(deploymentName);
                db = repositoryService.createDeployment().name(deploymentName+System.currentTimeMillis());
                LOGGER.info("loading bpm xml file:" + path);
                db = db.addClasspathResource(path);
                db.deploy();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取指定目录下的所有bpmn文件
     *
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    public String[] diagramFiles() throws Exception {
        String path = "diagram";
        URL dirURL = ReflectUtil.getResource(path);
        Set<String> result = new HashSet<>();
        if (dirURL != null && dirURL.getProtocol().equals("file")) {
            File f = new File(dirURL.getPath());
            File f1 = null;
            for (String s : f.list()) {
                f1 = new File(f, s);
                if (f1.isDirectory()) {
                    for (String s1 : f1.list()) {
                        if (s1.contains(".bpmn")) {
                            result.add(path + "/" + s + "/" + s1);
                        }
                    }
                }
            }
        } else if (dirURL.getProtocol().equals("jar")) {
            String jarPath = dirURL.getPath().substring(5, dirURL.getPath().indexOf("!")); //strip out only the JAR file
            JarFile jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
            Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
            while (entries.hasMoreElements()) {
                String name = entries.nextElement().getName();
                if (name.contains(".bpmn")) { //filter according to the path
                    result.add(name);
                }
            }
            jar.close();

        }
        System.out.println(result);
        if (result.size() > 0)
            return result.toArray(new String[result.size()]);
        else
            throw new UnsupportedOperationException("Cannot list files for URL " + dirURL);
    }
}
