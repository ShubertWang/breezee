/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sysmgr.impl;

import com.breezee.common.*;
import com.breezee.common.util.Callback;
import com.breezee.common.util.ContextUtil;
import com.breezee.sysmgr.api.domain.RoleInfo;
import com.breezee.sysmgr.api.service.IRoleService;
import com.breezee.sysmgr.entity.RoleEntity;
import com.breezee.sysmgr.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Silence on 2016/2/13.
 */
@Service("roleService")
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public RoleInfo saveInfo(RoleInfo roleInfo) {
        RoleEntity entity = roleRepository.findByCode(roleInfo.getCode());
        if (roleInfo.getId() == null && entity != null) {
            return ErrorInfo.build(roleInfo, ContextUtil.getMessage("duplicate.key", new String[]{roleInfo.getCode()}));
        }
        if (entity == null)
            entity = new RoleEntity();
        roleRepository.save(entity.parse(roleInfo));
        return SuccessInfo.build(RoleInfo.class);
    }

    @Override
    public RoleInfo deleteById(Long id) {
        return null;
    }

    @Override
    public RoleInfo findInfoById(Long id) {
        RoleEntity entity = roleRepository.findOne(id);
        if (entity == null)
            return ErrorInfo.build(new RoleInfo(), ContextUtil.getMessage("entity.error.exist"));
        return entity.toInfo();
    }

    @Override
    public List<RoleInfo> listAll(Map<String, Object> m) {
        List<RoleEntity> l = (List<RoleEntity>) roleRepository.findAll();
        return new InfoList<>(l, (Callback<RoleEntity, RoleInfo>) (roleEntity, roleInfo) -> roleEntity.toInfo());
    }

    @Override
    public PageResult<RoleInfo> pageAll(Map<String, Object> m, PageInfo pageInfo) {
        return null;
    }

    @Override
    public void updateRoleAccount(RoleInfo roleInfo) {
        String delSql = "delete from sym_tf_acnt_role where ROLE_ID=" + roleInfo.getId();
        String insertSql = "insert into sym_tf_acnt_role (ROLE_ID,ACNT_ID) values(?,?)";
        List<Object[]> l = new ArrayList<>();

        if (roleInfo.getAccounts() != null)
            roleInfo.getAccounts().forEach(a -> {
                l.add(new Object[]{roleInfo.getId(), a});
            });
        jdbcTemplate.update(delSql);
        if(l.size()>0)
            jdbcTemplate.batchUpdate(insertSql, l);
    }
}
