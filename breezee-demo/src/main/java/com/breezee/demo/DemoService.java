package com.breezee.demo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

/**
 * Created by Silence on 2016/1/29.
 */
@Path("demo")
public interface DemoService {

    @Path("hello")
    @GET
    String sayHello(@QueryParam("name") String name);
}
