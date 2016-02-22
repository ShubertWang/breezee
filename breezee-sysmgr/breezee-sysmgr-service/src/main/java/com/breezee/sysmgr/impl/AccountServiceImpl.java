/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sysmgr.impl;

import com.breezee.common.*;
import com.breezee.common.util.Callback;
import com.breezee.common.util.ContextUtil;
import com.breezee.common.util.SpecificationUtil;
import com.breezee.sysmgr.api.domain.AccountInfo;
import com.breezee.sysmgr.api.service.IAccountService;
import com.breezee.sysmgr.entity.AccountEntity;
import com.breezee.sysmgr.repository.AccountRepository;
import com.breezee.sysmgr.repository.OrganizationRepository;
import com.breezee.sysmgr.repository.RoleRepository;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
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
        AccountEntity entity = accountRepository.findByCode(accountInfo.getCode());
        //如果新增的账号已经存在，则返回错误信息
        if(accountInfo.getId()==null && entity !=null){
            return ErrorInfo.build(accountInfo, ContextUtil.getMessage("duplicate.key", new String[]{accountInfo.getCode()}));
        }
        if(entity==null)
            entity = new AccountEntity();
        entity.parse(accountInfo);
        if(accountInfo.getOrgId()!=null)
            entity.setOrganization(organizationRepository.findOne(accountInfo.getOrgId()));
        if(accountInfo.getRoles()!=null && accountInfo.getRoles().size()>0){
            for (Long roleId : accountInfo.getRoles()) {
                entity.addRole(roleRepository.findOne(roleId));
            }
        }
        if(accountInfo.getPassword()==null){
            entity.setPassword(md5Crypt(accountInfo.getCode()+"123"));
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
    public PageResult<AccountInfo> findAccountsNotOrgId(Long orgId, PageInfo pageInfo) {
        Page<AccountEntity> page = accountRepository.findAccountsNotInOrg(organizationRepository.findOne(orgId),pageInfo);
        return new PageResult<>(page, AccountInfo.class, (accountEntity, accountInfo) -> accountEntity.toInfo());
    }

    @Override
    public PageResult<AccountInfo> findAccountsByRoleId(Long roleId, PageInfo pageInfo) {
        return null;
    }

    @Override
    public PageResult<AccountInfo> findAccountsNotRoleId(Long roleId, PageInfo pageInfo) {
        return null;
    }

    @Override
    public void updateAccountStatus(Long accountId, Integer status) {
        AccountEntity entity = accountRepository.findOne(accountId);
        if(entity!=null) {
            entity.setStatus(status);
            accountRepository.save(entity);
        }
    }

    @Override
    public AccountInfo updatePassword(AccountInfo info) {
        AccountEntity entity = accountRepository.findOne(info.getId());
        if(entity!=null) {
            String password = entity.getPassword();
            String oldPassword = md5Crypt(info.getOldPassword());
            if(!password.equals(oldPassword)){
                return ErrorInfo.build(info,ContextUtil.getMessage("account.password.dismatch"));
            } else {
                entity.setPassword(md5Crypt(info.getPassword()));
                accountRepository.save(entity);
            }
        }
        return SuccessInfo.build(info);
    }

    @Override
    public AccountInfo checkPassword(AccountInfo info) {
        AccountEntity entity = accountRepository.findOne(info.getId());
        if(entity!=null){
            if(!entity.getPassword().equals(md5Crypt(info.getPassword()))){
                return ErrorInfo.build(info,ContextUtil.getMessage("account.password.dismatch"));
            }
        }
        return info;
    }

    /**
     * 使用md5进行加密
     * @param s
     * @return
     */
    private String md5Crypt(String s){
        if(s==null)
            return "none";
        try {
            return Md5Crypt.md5Crypt(s.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s;
    }
}
