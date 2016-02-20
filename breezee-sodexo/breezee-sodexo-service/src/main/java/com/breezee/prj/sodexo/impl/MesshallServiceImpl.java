/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.prj.sodexo.impl;

import com.breezee.common.*;
import com.breezee.common.util.Callback;
import com.breezee.common.util.SpecificationUtil;
import com.breezee.prj.sodexo.domain.MesshallInfo;
import com.breezee.prj.sodexo.entity.MesshallEntity;
import com.breezee.prj.sodexo.repository.MesshallRepository;
import com.breezee.prj.sodexo.service.IMesshallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Silence on 2016/2/13.
 */
@Service("messhallService")
public class MesshallServiceImpl implements IMesshallService {

    @Autowired
    private MesshallRepository messhallRepository;

    @Override
    public MesshallInfo saveInfo(MesshallInfo messhallInfo) {
        messhallRepository.save(new MesshallEntity().parse(messhallInfo));
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
        List<MesshallEntity> l = messhallRepository.findAll(SpecificationUtil.createSpecification(m));
        return new InfoList<>(l, (Callback<MesshallEntity, MesshallInfo>) (messhallEntity, messhallInfo) -> messhallEntity.toInfo());
    }

    @Override
    public PageResult<MesshallInfo> pageAll(Map<String, Object> m, PageInfo pageInfo) {
        Page<MesshallEntity> page = messhallRepository.findAll(SpecificationUtil.createSpecification(m),pageInfo);
        return new PageResult<>(page, MesshallInfo.class, (messhallEntity, messhallInfo) -> messhallEntity.toInfo());
    }
}
