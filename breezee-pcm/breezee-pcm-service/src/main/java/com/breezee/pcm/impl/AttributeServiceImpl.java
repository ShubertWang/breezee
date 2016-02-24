/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.pcm.impl;

import com.breezee.common.InfoList;
import com.breezee.common.PageInfo;
import com.breezee.common.PageResult;
import com.breezee.common.SuccessInfo;
import com.breezee.common.DynamicSpecifications;
import com.breezee.common.util.Callback;
import com.breezee.pcm.api.domain.AttributeInfo;
import com.breezee.pcm.api.service.IAttributeService;
import com.breezee.pcm.entity.AttributeEntity;
import com.breezee.pcm.repository.AttributeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

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
}
