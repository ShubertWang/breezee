/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.oms.impl;

import com.breezee.common.*;
import com.breezee.common.util.Callback;
import com.breezee.common.util.SpecificationUtil;
import com.breezee.oms.api.domain.InventoryInfo;
import com.breezee.oms.entity.InventoryEntity;
import com.breezee.oms.repository.InventoryRepository;
import com.breezee.oms.api.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Silence on 2016/2/12.
 */
@Service("inventoryService")
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public List<InventoryInfo> findInventoryBySkuId(String skuId) {
        List<InventoryEntity> l = inventoryRepository.findBySkuId(skuId);
        List<InventoryInfo> ll = new ArrayList<>(l.size());
        l.forEach(a->{
            ll.add(a.toInfo());
        });
        return ll;
    }

    @Override
    public List<InventoryInfo> findInventoryBySkuIdAndLocationId(String skuId, String locationId) {
        List<InventoryEntity> l = inventoryRepository.findBySkuIdAndLocationId(skuId, locationId);
        List<InventoryInfo> ll = new ArrayList<>(l.size());
        l.forEach(a->{
            ll.add(a.toInfo());
        });
        return ll;
    }

    @Override
    public InventoryInfo saveInfo(InventoryInfo inventoryInfo) {
        inventoryRepository.save(new InventoryEntity().parse(inventoryInfo));
        return SuccessInfo.build(InventoryInfo.class);
    }

    @Override
    public InventoryInfo findInfoById(Long id) {
        InventoryEntity entity = inventoryRepository.findOne(id);
        if(entity==null)
            return NullInfo.build(InventoryInfo.class);
        return entity.toInfo();
    }

    @Override
    public List<InventoryInfo> listAll(Map<String, Object> m) {
        List<InventoryEntity> l = inventoryRepository.findAll(SpecificationUtil.createSpecification(m));
        return new InfoList<>(l, (Callback<InventoryEntity, InventoryInfo>) (inventoryEntity, inventoryInfo) -> inventoryEntity.toInfo());
    }

    @Override
    public PageResult<InventoryInfo> pageAll(Map<String, Object> m, PageInfo pageInfo) {
        Page<InventoryEntity> page = inventoryRepository.findAll(SpecificationUtil.createSpecification(m), pageInfo);
        return new PageResult<>(page, InventoryInfo.class, (inventoryEntity, inventoryInfo) -> inventoryEntity.toInfo());
    }
}
