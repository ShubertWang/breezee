/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.bpm;


import com.breezee.common.util.ContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * Bpm的standalone服务启动
 * Created by Silence on 2016/1/30.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan("com.breezee")
@PropertySource(name = "enginePro",
        value = {"classpath:/config/engine.properties"},
        ignoreResourceNotFound = true)
@ImportResource(value = {"classpath:/activiti-context.xml",
        "classpath:/bpm-provider.xml"})
public class BpmService {

    /**
     * 启动BPM服务
     * @param args
     */
    public static void main(String[] args) {
//        ContextUtil.runAnnotation(BpmService.class, AppConfigurationWithDubbo.class);
        ContextUtil.current = SpringApplication.run(BpmService.class, args);
    }
}
