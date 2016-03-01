package com.breezee.bpm.identity;

import com.breezee.sysmgr.api.service.IAccountService;
import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.GroupIdentityManager;

import javax.annotation.Resource;

/**
 * Created by Silence on 2016/2/27.
 */
public class MyGroupManagerFactory implements SessionFactory {

    @Resource
    private IAccountService accountService;

    @Override
    public Class<?> getSessionType() {
        return GroupIdentityManager.class;
    }

    @Override
    public Session openSession() {
        return new MyGroupManager(accountService);
    }

    public IAccountService getAccountService() {
        return accountService;
    }

    public void setAccountService(IAccountService accountService) {
        this.accountService = accountService;
    }
}
