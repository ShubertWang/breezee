/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sysmgr.impl;

import com.breezee.common.InfoList;
import com.breezee.common.PageInfo;
import com.breezee.common.PageResult;
import com.breezee.common.SuccessInfo;
import com.breezee.common.util.Callback;
import com.breezee.common.util.SpecificationUtil;
import com.breezee.sysmgr.api.domain.AccountInfo;
import com.breezee.sysmgr.api.service.IAccountService;
import com.breezee.sysmgr.entity.AccountEntity;
import com.breezee.sysmgr.repository.AccountRepository;
import com.breezee.sysmgr.repository.OrganizationRepository;
import com.breezee.sysmgr.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Silence on 2016/2/12.
 */
@Service("accountService")
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public AccountInfo saveInfo(AccountInfo accountInfo) {
        AccountEntity entity = new AccountEntity().parse(accountInfo);
        if(accountInfo.getOrgId()!=null)
            entity.setOrganization(organizationRepository.findOne(accountInfo.getOrgId()));
        if(accountInfo.getRoles()!=null && accountInfo.getRoles().size()>0){
            accountInfo.getRoles().forEach(a->{
                entity.addRole(roleRepository.findOne(a));
            });
        }
        accountRepository.save(entity);
        return SuccessInfo.build(AccountInfo.class);
    }

    @Override
    public AccountInfo findInfoById(Long id) {
        AccountEntity entity = accountRepository.findOne(id);
        return entity.toInfo();
    }

    @Override
    public List<AccountInfo> listAll(Map<String, Object> m) {
        List<AccountEntity> l = accountRepository.findAll(SpecificationUtil.createSpecification(m));
        return new InfoList<>(l, (Callback<AccountEntity, AccountInfo>) (accountEntity, accountInfo) -> accountEntity.toInfo());
    }

    @Override
    public PageResult<AccountInfo> pageAll(Map<String, Object> m, PageInfo pageInfo) {
        Page<AccountEntity> page = accountRepository.findAll(SpecificationUtil.createSpecification(m),pageInfo);
        return new PageResult<>(page, AccountInfo.class, (accountEntity, accountInfo) -> accountEntity.toInfo());
    }

    @Override
    public PageResult<AccountInfo> findAccountsByOrgId(Long orgId, PageInfo pageInfo) {
        Page<AccountEntity> page = accountRepository.findAccountsByOrg(organizationRepository.findOne(orgId),pageInfo);
        return new PageResult<>(page, AccountInfo.class, (accountEntity, accountInfo) -> accountEntity.toInfo());
    }

    @Override
    public PageResult<AccountInfo> findAccountsByRoleId(Long roleId, PageInfo pageInfo) {
        return null;
    }
}
