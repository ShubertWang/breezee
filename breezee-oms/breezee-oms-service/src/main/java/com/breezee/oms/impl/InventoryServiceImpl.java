/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.oms.impl;

import com.breezee.common.*;
import com.breezee.common.util.Callback;
import com.breezee.common.DynamicSpecifications;
import com.breezee.common.util.ContextUtil;
import com.breezee.oms.api.domain.InventoryInfo;
import com.breezee.oms.entity.InventoryEntity;
import com.breezee.oms.repository.InventoryRepository;
import com.breezee.oms.api.service.InventoryService;
import com.breezee.pcm.api.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Silence on 2016/2/12.
 */
@Service("inventoryService")
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Resource
    private IProductService productService;

    @Override
    public List<InventoryInfo> findInventoryBySkuId(String skuId) {
        List<InventoryEntity> l = inventoryRepository.findBySkuId(skuId);
        List<InventoryInfo> ll = new ArrayList<>(l.size());
        for (InventoryEntity entity : l) {
            InventoryInfo inventoryInfo = entity.toInfo();
            inventoryInfo.setName(productService.findByCode(entity.getSkuId()).getName());
            ll.add(inventoryInfo);
        }
        return ll;
    }

    @Override
    public List<InventoryInfo> findInventoryByLocationId(String locationId) {
        List<InventoryEntity> l = inventoryRepository.findByLocationId(locationId);
        List<InventoryInfo> ll = new ArrayList<>(l.size());
        for (InventoryEntity entity : l) {
            InventoryInfo inventoryInfo = entity.toInfo();
            inventoryInfo.setName(productService.findByCode(entity.getSkuId()).getName());
            ll.add(inventoryInfo);
        }
        return ll;
    }

    @Override
    public InventoryInfo findInventoryBySkuIdAndLocationId(String skuId, String locationId) {
        InventoryEntity entity = inventoryRepository.findBySkuIdAndLocationId(skuId, locationId);
        if (entity == null)
            return ErrorInfo.build(InventoryInfo.class);
        return entity.toInfo();
    }

    @Override
    public void updateInventoryBySkuId(InventoryInfo inventoryInfo) {
        List<InventoryEntity> l = inventoryRepository.findBySkuId(inventoryInfo.getSkuId());
        if(l.size()==0){
            inventoryRepository.save(new InventoryEntity().parse(inventoryInfo));
        } else {
            for (InventoryEntity entity : l) {
                entity.setUpdateTime(new Date());
                if (inventoryInfo.getUpdator() != null)
                    entity.setUpdator(inventoryInfo.getUpdator());
                if (inventoryInfo.getQuantity() != null)
                    entity.setQuantity(inventoryInfo.getQuantity().getValue());
                inventoryRepository.save(entity);
            }
        }
    }

    @Override
    public InventoryInfo saveInfo(InventoryInfo inventoryInfo) {
        InventoryEntity entity = inventoryRepository.findBySkuIdAndLocationId(inventoryInfo.getSkuId(), inventoryInfo.getLocationId());
        //如果新增的账号已经存在，则返回错误信息
        if (inventoryInfo.getId() == null && entity != null) {
            return ErrorInfo.build(inventoryInfo, ContextUtil.getMessage("duplicate.key", new String[]{inventoryInfo.getSkuId() + "-" + inventoryInfo.getLocationId()}));
        }
        if (entity == null)
            entity = new InventoryEntity();
        entity.parse(inventoryInfo);
        inventoryRepository.save(entity);
        return SuccessInfo.build(InventoryInfo.class);
    }

    @Override
    public InventoryInfo deleteById(Long id) {
        return null;
    }

    @Override
    public InventoryInfo findInfoById(Long id) {
        InventoryEntity entity = inventoryRepository.findOne(id);
        if (entity == null)
            return ErrorInfo.build(InventoryInfo.class);
        return entity.toInfo();
    }

    @Override
    public List<InventoryInfo> listAll(Map<String, Object> m) {
        List<InventoryEntity> l = inventoryRepository.findAll(DynamicSpecifications.createSpecification(m));
        return new InfoList<>(l, (Callback<InventoryEntity, InventoryInfo>) (inventoryEntity, inventoryInfo) -> inventoryEntity.toInfo());
    }

    @Override
    public PageResult<InventoryInfo> pageAll(Map<String, Object> m, PageInfo pageInfo) {
        pageInfo = new PageInfo(m);
        Page<InventoryEntity> page = inventoryRepository.findAll(DynamicSpecifications.createSpecification(m), pageInfo);
        return new PageResult<>(page, InventoryInfo.class, (inventoryEntity, inventoryInfo) -> inventoryEntity.toInfo());
    }
}
