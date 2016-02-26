/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.prj.sodexo.impl;

import com.breezee.common.*;
import com.breezee.common.util.Callback;
import com.breezee.prj.sodexo.domain.MesshallInfo;
import com.breezee.prj.sodexo.entity.MesshallEntity;
import com.breezee.prj.sodexo.repository.MesshallRepository;
import com.breezee.prj.sodexo.service.IMesshallService;
import com.breezee.sysmgr.api.service.IAccountService;
import com.breezee.sysmgr.api.service.IOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Silence on 2016/2/13.
 */
@Service("messhallService")
public class MesshallServiceImpl implements IMesshallService {

    @Autowired
    private MesshallRepository messhallRepository;

    @Autowired
    private IOrganizationService organizationService;

    @Autowired
    private IAccountService accountService;

    @Override
    public MesshallInfo saveInfo(MesshallInfo messhallInfo) {
        messhallRepository.save(new MesshallEntity().parse(messhallInfo));
        return SuccessInfo.build(MesshallInfo.class);
    }

    @Override
    public MesshallInfo deleteById(Long id) {
        messhallRepository.delete(id);
        return SuccessInfo.build(MesshallInfo.class);
    }

    @Override
    public MesshallInfo findInfoById(Long id) {
        MesshallEntity entity = messhallRepository.findOne(id);
        if (entity == null)
            return ErrorInfo.build(MesshallInfo.class);
        return entity.toInfo();
    }

    @Override
    public List<MesshallInfo> listAll(Map<String, Object> m) {
        List<MesshallEntity> l = messhallRepository.findAll(DynamicSpecifications.createSpecification(m));
        return new InfoList<>(l, (Callback<MesshallEntity, MesshallInfo>) (messhallEntity, messhallInfo) -> {
            MesshallInfo info = messhallEntity.toInfo();
            if(info.getOrgId()!=null)
                info.setOrgName(organizationService.findInfoById(info.getOrgId()).getName());
            return info;
        });
    }

    @Override
    public PageResult<MesshallInfo> pageAll(Map<String, Object> m, PageInfo pageInfo) {
        Page<MesshallEntity> page = messhallRepository.findAll(DynamicSpecifications.createSpecification(m),pageInfo);
        return new PageResult<>(page, MesshallInfo.class, (messhallEntity, messhallInfo) -> {
            MesshallInfo info = messhallEntity.toInfo();
            if(info.getOrgId()!=null)
                info.setOrgName(organizationService.findInfoById(info.getOrgId()).getName());
            if(info.getDutyPerson()!=null)
                info.setDutyName(accountService.findInfoById(info.getDutyPerson()).getName());
            return info;
        });
    }

    @Override
    public List<MesshallInfo> findAll() {
        return listAll(new HashMap<>());
    }
}
