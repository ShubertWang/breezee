/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sysmgr.impl;

import com.breezee.common.ErrorInfo;
import com.breezee.common.PageInfo;
import com.breezee.common.PageResult;
import com.breezee.common.SuccessInfo;
import com.breezee.common.util.ContextUtil;
import com.breezee.sysmgr.api.domain.OrganizationInfo;
import com.breezee.sysmgr.api.service.IOrganizationService;
import com.breezee.sysmgr.entity.OrganizationEntity;
import com.breezee.sysmgr.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Silence on 2016/2/13.
 */
@Service("organizationService")
public class OrganizationServiceImpl implements IOrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public OrganizationInfo saveInfo(OrganizationInfo organizationInfo) {
        OrganizationEntity entity = organizationRepository.findByCode(organizationInfo.getCode());
        if (organizationInfo.getId() == null && entity != null) {
            return ErrorInfo.build(organizationInfo, ContextUtil.getMessage("duplicate.key", new String[]{organizationInfo.getCode()}));
        }
        if (entity == null)
            entity = new OrganizationEntity();
        entity.parse(organizationInfo);
        if (organizationInfo.getParent() != null && organizationInfo.getParent().getId() != null) {
            entity.setParent(organizationRepository.findOne(organizationInfo.getParent().getId()));
        }
        organizationRepository.save(entity);
        return SuccessInfo.build(OrganizationInfo.class);
    }

    @Override
    public OrganizationInfo deleteById(Long id) {
        return null;
    }

    @Override
    public OrganizationInfo findInfoById(Long id) {
        OrganizationEntity en = organizationRepository.findOne(id);
        if (en == null)
            return ErrorInfo.build(OrganizationInfo.class);
        return en.toInfo(false);
    }

    @Override
    public List<OrganizationInfo> listAll(Map<String, Object> m) {
        return null;
    }

    @Override
    public PageResult<OrganizationInfo> pageAll(Map<String, Object> m, PageInfo pageInfo) {
        return null;
    }

    @Override
    public List<OrganizationInfo> findOrganizationsByParentId(Long id) {
        List<OrganizationEntity> l = new ArrayList<>();
        if (id == -1) {
            l = organizationRepository.findTop();
        } else {
            OrganizationEntity en = organizationRepository.findOne(id);
            if (en != null)
                l.addAll(en.getChildren());
        }
        List<OrganizationInfo> _l = new ArrayList<>(l.size());
        l.forEach(a -> {
            _l.add(a.toInfo(false));
        });
        return _l;
    }

    @Override
    public List<OrganizationInfo> findOrganizationsByParentCode(String id) {
        List<OrganizationEntity> l = new ArrayList<>();
        OrganizationEntity en = organizationRepository.findByCode(id);
        if (en != null)
            l.addAll(en.getChildren());
        List<OrganizationInfo> _l = new ArrayList<>(l.size());
        l.forEach(a -> {
            _l.add(a.toInfo(false));
        });
        return _l;
    }

    @Override
    public void updateOrganizationAccount(OrganizationInfo organizationInfo) {
        String delSql = "delete from sym_tf_acnt_org where ORG_ID=" + organizationInfo.getId();
        //一个账号只能属于一个组织，所以删除其关联的其他组织
        String delSqlAct = "delete from sym_tf_acnt_org where ACNT_ID=?";
        String insertSql = "insert into sym_tf_acnt_org (ORG_ID,ACNT_ID) values(?,?)";
        List<Object[]> l = new ArrayList<>();
        List<Object[]> ll = new ArrayList<>();

        if (organizationInfo.getAccounts() != null)
            organizationInfo.getAccounts().forEach(a -> {
                l.add(new Object[]{organizationInfo.getId(), a});
                ll.add(new Object[]{a});
            });
        jdbcTemplate.update(delSql);
        if(ll.size()>0)
            jdbcTemplate.batchUpdate(delSqlAct, ll);
        if(l.size()>0)
            jdbcTemplate.batchUpdate(insertSql, l);
    }

    @Override
    public OrganizationInfo findByCode(String code) {
        OrganizationEntity en = organizationRepository.findByCode(code);
        if (en == null)
            return ErrorInfo.build(OrganizationInfo.class);
        return en.toInfo(false);
    }
}
