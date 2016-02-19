package com.breezee.demo;

import javax.ws.rs.QueryParam;

/**
 * Created by Silence on 2016/1/29.
 */
//@com.alibaba.dubbo.config.annotation.Service(protocol = {"dubbo","rest"},interfaceClass = DemoService.class)
//@Service("bidService")
public class DemoServiceImpl implements DemoService {

    public DemoServiceImpl(){
        System.out.println("---------------");
    }

    @Override
    public String sayHello(@QueryParam("name") String name) {
        return name+"*************8";
    }
}
