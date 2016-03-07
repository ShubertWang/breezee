/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sodexo.impl;

import com.breezee.common.*;
import com.breezee.common.util.Callback;
import com.breezee.common.util.ContextUtil;
import com.breezee.sodexo.api.domain.MesshallInfo;
import com.breezee.sodexo.entity.MesshallEntity;
import com.breezee.sodexo.repository.CommentRepository;
import com.breezee.sodexo.repository.MesshallRepository;
import com.breezee.sodexo.api.service.IMesshallService;
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

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public MesshallInfo saveInfo(MesshallInfo messhallInfo) {
        MesshallEntity entity = messhallRepository.findByCode(messhallInfo.getCode());
        //如果新增的账号已经存在，则返回错误信息
        if (messhallInfo.getId() == null && entity != null) {
            return ErrorInfo.build(messhallInfo, ContextUtil.getMessage("duplicate.key", new String[]{messhallInfo.getCode()}));
        }
        if (entity == null)
            entity = new MesshallEntity();
        messhallRepository.save(entity.parse(messhallInfo));
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
        MesshallInfo info = entity.toInfo();
        info.getProperties().put("yc",commentRepository.countObject(info.getId().toString(),"messhall","evaluate",1));
        info.getProperties().put("bc",commentRepository.countObject(info.getId().toString(),"messhall","evaluate",0));
        return info;
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
        pageInfo = new PageInfo(m);
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

    @Override
    public MesshallInfo findByCode(String code) {
        MesshallEntity entity = messhallRepository.findByCode(code);
        if (entity == null)
            return ErrorInfo.build(MesshallInfo.class);
        return entity.toInfo();
    }

    @Override
    public void updateStatus(Long id, int status){}
}
