package com.breezee.demo;

import com.alibaba.dubbo.remoting.http.servlet.DispatcherServlet;
import com.breezee.common.servlet.DubboServletContextInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import java.io.IOException;

/**
 * Created by Silence on 2016/1/29.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
@ImportResource(value = {"classpath:/demo.xml"})
public class Demo {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(Demo.class, args);
    }

    @Bean
    public DispatcherServlet services(){
        return new DispatcherServlet();
    }

    @Bean
    public ServletContextInitializer servletContextInitializer(){
        return new DubboServletContextInitializer();
    }


}
