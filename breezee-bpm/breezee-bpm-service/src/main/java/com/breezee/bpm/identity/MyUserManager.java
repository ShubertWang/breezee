package com.breezee.bpm.identity;

import com.breezee.sysmgr.api.domain.AccountInfo;
import com.breezee.sysmgr.api.service.IAccountService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Silence on 2016/2/27.
 */
public class MyUserManager extends UserEntityManager {

    private IAccountService accountService;

    public MyUserManager(IAccountService accountService){
        this.accountService = accountService;
    }

    public static User converUser(AccountInfo user) {
        UserEntity ue = new UserEntity();
        ue.setId(user.getCode());
        ue.setFirstName(user.getName());
        return ue;
    }

    public User findUserById(String userId){
        return converUser(accountService.findByCode(userId));
    }

    public List<Group> findGroupsByUser(String userId){
        AccountInfo accountInfo = accountService.findByCode(userId);
        List<String> l = accountInfo.getRoles();
        List<Group> ll = new ArrayList<>();
        if (l != null) {
            l.forEach(a->{
                ll.add(new GroupEntity(a));
            });
        }
        return ll;
    }

    public List<User> findUserByQueryCriteria(UserQueryImpl query, Page page){
        if(query.getId()!=null){
            return Collections.singletonList(this.findUserById(query.getId()));
        }
        return super.findUserByQueryCriteria(query, page);
    }
}
