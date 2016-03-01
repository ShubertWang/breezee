package com.breezee.bpm.identity;

import com.breezee.sysmgr.api.domain.AccountInfo;
import com.breezee.sysmgr.api.service.IAccountService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Silence on 2016/2/27.
 */
public class MyGroupManager extends GroupEntityManager {

    private IAccountService accountService;

    public MyGroupManager(IAccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public List<Group> findGroupsByUser(String userId) {
        AccountInfo accountInfo = accountService.findByCode(userId);
        List<String> l = accountInfo.getRoles();
        List<Group> ll = new ArrayList<>();
        if (l != null) {
            l.forEach(a->{
                ll.add(convertGroup(a));
            });
        }
        return ll;
    }

    public Group convertGroup(String roleId) {
        GroupEntity ge = new GroupEntity();
        ge.setName(roleId);
        ge.setId(roleId);
        ge.setType("1");
        ge.setRevision(1);
        return ge;
    }

    @Override
    public List<Group> findGroupByQueryCriteria(GroupQueryImpl query, Page page) {
        return super.findGroupByQueryCriteria(query, page);
    }

    @Override
    public long findGroupCountByQueryCriteria(GroupQueryImpl query) {
        return super.findGroupCountByQueryCriteria(query);
    }

    @Override
    public Group createNewGroup(String groupId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteGroup(String groupId) {
        throw new UnsupportedOperationException();
    }
}
