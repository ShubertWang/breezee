/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sysmgr.impl;

import com.breezee.common.NullInfo;
import com.breezee.common.PageInfo;
import com.breezee.common.PageResult;
import com.breezee.common.SuccessInfo;
import com.breezee.sysmgr.api.domain.OrganizationInfo;
import com.breezee.sysmgr.api.service.IOrganizationService;
import com.breezee.sysmgr.entity.OrganizationEntity;
import com.breezee.sysmgr.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public OrganizationInfo saveInfo(OrganizationInfo organizationInfo) {
        OrganizationEntity entity = new OrganizationEntity().parse(organizationInfo);
        if(organizationInfo.getParent()!=null && organizationInfo.getParent().getId()!=null){
            entity.setParent(organizationRepository.findOne(organizationInfo.getParent().getId()));
        }
        organizationRepository.save(entity);
        return SuccessInfo.build(OrganizationInfo.class);
    }

    @Override
    public OrganizationInfo findInfoById(Long id) {
        OrganizationEntity en = organizationRepository.findOne(id);
        if(en==null)
            return NullInfo.build(OrganizationInfo.class);
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
        if(id == -1){
            List<OrganizationEntity> l = organizationRepository.findTop();
            List<OrganizationInfo> _l = new ArrayList<>(l.size());
            l.forEach(a->{
                _l.add(a.toInfo(false));
            });
            return _l;
        }
        OrganizationEntity en = organizationRepository.findOne(id);
        if(en==null)
            return new ArrayList<>();
        return en.toInfo(true).getChildren();
    }
}
