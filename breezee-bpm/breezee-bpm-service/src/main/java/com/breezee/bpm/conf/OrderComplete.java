package com.breezee.bpm.conf;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * Created by fanluo on 28/02/2016.
 */

@Service("orderComplete")
public class OrderComplete implements JavaDelegate, Serializable, InitializingBean {

    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("~~~~order complete invoke~~~~~");
        // TODO
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }
}
