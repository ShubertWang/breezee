/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.oms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import java.io.IOException;

/**
 * Created by Silence on 2016/1/29.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan("com.breezee")
@ImportResource(value = {"classpath:/oms-provider.xml"})
public final class OmsService {

    /**
     * 启动本服务
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        SpringApplication.run(OmsService.class, args);
    }
}
