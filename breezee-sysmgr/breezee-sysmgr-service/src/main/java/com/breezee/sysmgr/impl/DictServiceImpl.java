/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sysmgr.impl;

import com.breezee.common.*;
import com.breezee.common.util.Callback;
import com.breezee.common.DynamicSpecifications;
import com.breezee.sysmgr.api.domain.DictDetailInfo;
import com.breezee.sysmgr.api.domain.DictInfo;
import com.breezee.sysmgr.api.service.IDictService;
import com.breezee.sysmgr.entity.DictEntity;
import com.breezee.sysmgr.repository.DictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Silence on 2016/2/10.
 */
@Service("dictService")
public class DictServiceImpl implements IDictService {

    @Autowired
    private DictRepository dictRepository;

    @Override
    public DictDetailInfo findDictDetailByCode(Long dictId, String code) {
        return null;
    }

    @Override
    public List<DictDetailInfo> findDictDetailByCode(String dictCode) {
        DictEntity dictEntity = dictRepository.findByCode(dictCode);
        if(dictEntity==null)
            return new ArrayList<>();
        DictInfo info = dictEntity.toInfo();
        return info.getDetailInfos();
    }

    @Override
    public DictInfo saveInfo(DictInfo dictInfo) {
        dictRepository.save(new DictEntity().parse(dictInfo));
        return SuccessInfo.build(DictInfo.class);
    }

    @Override
    public DictInfo findInfoById(Long id) {
        return dictRepository.findOne(id).toInfo();
    }

    @Override
    public List<DictInfo> listAll(Map<String, Object> m) {
        List<DictEntity> l = dictRepository.findAll(DynamicSpecifications.createSpecification(m));
        return new InfoList<>(l, (Callback<DictEntity, DictInfo>) (dictEntity, dictInfo) -> dictEntity.toInfo());
    }

    @Override
    public PageResult<DictInfo> pageAll(Map<String, Object> m, PageInfo pageInfo) {
        Page<DictEntity> page = dictRepository.findAll(pageInfo);
        return new PageResult<>(page, DictInfo.class, (dictEntity, dictInfo) -> dictEntity.toInfo());
    }
}
