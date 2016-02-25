/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.pcm.impl;

import com.breezee.common.*;
import com.breezee.common.util.Callback;
import com.breezee.common.util.ContextUtil;
import com.breezee.pcm.api.domain.AttributeInfo;
import com.breezee.pcm.api.service.IAttributeService;
import com.breezee.pcm.entity.AttributeEntity;
import com.breezee.pcm.repository.AttributeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Silence on 2016/2/7.
 */
@Service("attributeService")
public class AttributeServiceImpl implements IAttributeService {

    @Autowired
    private AttributeRepository attributeRepository;

    @Override
    public AttributeInfo saveInfo(AttributeInfo attributeInfo) {
        AttributeEntity entity = attributeRepository.findByCode(attributeInfo.getCode());
        //如果新增的账号已经存在，则返回错误信息
        if (attributeInfo.getId() == null && entity != null) {
            return ErrorInfo.build(attributeInfo, ContextUtil.getMessage("duplicate.key", new String[]{attributeInfo.getCode()}));
        }
        if (entity == null)
            entity = new AttributeEntity();
        entity.parse(attributeInfo);
        attributeRepository.save(new AttributeEntity().parse(attributeInfo));
        return SuccessInfo.build(AttributeInfo.class);
    }

    @Override
    public AttributeInfo findInfoById(Long id) {
        AttributeEntity entity = attributeRepository.findOne(id);
        return entity.toInfo();
    }

    @Override
    public List<AttributeInfo> listAll(Map<String, Object> m) {
        List<AttributeEntity> l = attributeRepository.findAll(DynamicSpecifications.createSpecification(m));
        return new InfoList<>(l, (Callback<AttributeEntity, AttributeInfo>) (attributeEntity, attributeInfo) -> attributeEntity.toInfo());
    }

    @Override
    public PageResult<AttributeInfo> pageAll(Map<String, Object> m, PageInfo pageInfo) {
        Page<AttributeEntity> page = attributeRepository.findAll(DynamicSpecifications.createSpecification(m), pageInfo);
        return new PageResult<>(page, AttributeInfo.class, (attributeEntity, attributeInfo) -> attributeEntity.toInfo());
    }

    @Override
    public PageResult<AttributeInfo> findAttrsNotForCateId(Long categoryId, PageInfo pageInfo) {
        return pageAll(new HashMap<>(),pageInfo);
    }
}
