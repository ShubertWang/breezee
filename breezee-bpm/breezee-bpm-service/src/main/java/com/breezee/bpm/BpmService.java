/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.bpm;


import com.breezee.bpm.conf.OrderComplete;
import com.breezee.common.types.Amount;
import com.breezee.common.util.ContextUtil;
import com.breezee.oms.api.domain.OrderInfo;
import com.breezee.oms.api.service.IOrderService;
import com.google.common.collect.Maps;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

import java.util.Date;
import java.util.Map;

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
//      ContextUtil.runAnnotation(BpmService.class, AppConfigurationWithDubbo.class);
        ContextUtil.current = SpringApplication.run(BpmService.class, args);

/*
        ProcessEngine processEngine = ContextUtil.getBean("processEngine", ProcessEngine.class);
        RuntimeService runtimeService = processEngine.getRuntimeService();
        IdentityService identityService = processEngine.getIdentityService();
        TaskService taskService = processEngine.getTaskService();



        //启动流程
        IOrderService orderService = ContextUtil.getBean("orderService", IOrderService.class);
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setProcDefId("orderProcess");
        orderInfo.setCode(String.valueOf(System.currentTimeMillis()));
        orderInfo.setIssueDate(new Date());
        orderInfo.setPaymentAmount(new Amount("CNY",new Double("1000")));
        orderInfo.setShippingMethod("现金");
        orderInfo.setSubTotal(new Amount("CNY",new Double("5000")));
        orderInfo.setUserId(1l);
        orderInfo.setCreateTime(new Date());
        orderService.saveInfo(orderInfo);
        */



        /*
        //提交流程
        Map<String,Object> vars1 = Maps.newConcurrentMap();
        Map<String,Object> vars2 = Maps.newConcurrentMap();
        vars1.put("orderCancel","Y");
        vars1.put("foodLineRole","bpm_admin");

        vars2.put("orderCancel","N");
        vars2.put("foodLineRole","bpm_admin");

        taskService.complete("115021",vars1);
        taskService.complete("117521",vars2);

        OrderComplete orderComplete = ContextUtil.getBean("orderComplete", OrderComplete.class);
        Map<String,Object> vars3 = Maps.newConcurrentMap();
        vars3.put("orderComplate", orderComplete);
        taskService.complete("125007",vars3);
        */

    }
}
