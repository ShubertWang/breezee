package com.breezee.bpm.identity;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.UserIdentityManager;

/**
 * Created by Silence on 2016/2/27.
 */
public class MyUserManagerFactory implements SessionFactory {

    @Override
    public Class<?> getSessionType() {
        return UserIdentityManager.class;
    }

    @Override
    public Session openSession() {
        return new MyUserManager();
    }

}
