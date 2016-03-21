package com.breezee.bpm.identity;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.GroupIdentityManager;

/**
 * **我们直接使用了taskCandidateGroup来获取待办了
 * Created by Silence on 2016/2/27.
 */
public class MyGroupManagerFactory implements SessionFactory {

    @Override
    public Class<?> getSessionType() {
        return GroupIdentityManager.class;
    }

    @Override
    public Session openSession() {
        return new MyGroupManager();
    }
}
