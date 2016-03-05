package com.breezee.bpm.identity;

import com.breezee.sysmgr.api.service.IAccountService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;

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
        //获取用户的组织，作为groupId.
        //TODO:如果需要对相应的角色做代办控制的话，应该在这里来做
        //就是用户应该同时具备组织和角色都复合条件的才返回
//        AccountInfo accountInfo;
//        if (StringUtils.isNumeric(userId))
//            accountInfo = accountService.findInfoById(Long.parseLong(userId));
//        else
//            accountInfo = accountService.findByCode(userId);
//        List<Group> ll = new ArrayList<>();
//        if (accountInfo!=null && accountInfo.getOrgCode() != null)
//            ll.add(convertGroup(accountInfo.getOrgCode()));
//        return ll;
        return super.findGroupsByUser(userId);
    }

    public Group convertGroup(String code) {
        GroupEntity ge = new GroupEntity();
        ge.setName(code);
        ge.setId(code);
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
