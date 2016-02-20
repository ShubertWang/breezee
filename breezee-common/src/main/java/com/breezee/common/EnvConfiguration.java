/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.common;

import com.alibaba.dubbo.remoting.http.servlet.DispatcherServlet;
import com.breezee.common.servlet.DubboServletContextInitializer;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * Created by Silence on 2016/2/9.
 */
@Configuration
@ImportResource(value = {"classpath:/bean/bre-datasource.xml",
        "classpath:/bean/bre-dubbo.xml"})
public class EnvConfiguration {

    @Bean
    public DispatcherServlet services() {
        return new DispatcherServlet();
    }

    @Bean
    public ServletContextInitializer servletContextInitializer() {
        return new DubboServletContextInitializer();
    }

    @Bean
    public MessageSource messageSource(){
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("messages","i18n");
        return messageSource;
    }
}
