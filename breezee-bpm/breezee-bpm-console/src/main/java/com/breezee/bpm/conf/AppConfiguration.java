/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.bpm.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.*;

@Configuration
@PropertySources({
        @PropertySource(value = "classpath:/application.properties", ignoreResourceNotFound = true),
        @PropertySource(value = "classpath:/config/application.properties", ignoreResourceNotFound = true),
        @PropertySource(value = "classpath:/properties/engine.properties", ignoreResourceNotFound = true)
})
@ImportResource({"classpath:/activiti-context.xml", "classpath:/activiti-app-context.xml"})
public class AppConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        // To avoid instantiating and configuring the mapper everywhere
        ObjectMapper mapper = new ObjectMapper();
        return mapper;
    }

    @Bean
    public DataConfiguration dataConfiguration(){
        return new DataConfiguration();
    }


}
