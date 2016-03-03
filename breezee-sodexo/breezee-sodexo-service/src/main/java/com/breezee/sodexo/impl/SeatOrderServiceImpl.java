/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sodexo.impl;

import com.breezee.common.InfoList;
import com.breezee.common.ErrorInfo;
import com.breezee.common.PageInfo;
import com.breezee.common.PageResult;
import com.breezee.common.util.Callback;
import com.breezee.common.DynamicSpecifications;
import com.breezee.sodexo.api.domain.SeatOrderInfo;
import com.breezee.sodexo.entity.SeatOrderEntity;
import com.breezee.sodexo.repository.SeatOrderRepository;
import com.breezee.sodexo.api.service.ISeatOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Silence on 2016/2/16.
 */
@Service("seatOrderService")
public class SeatOrderServiceImpl implements ISeatOrderService {

    @Autowired
    private SeatOrderRepository seatOrderRepository;

    @Override
    public SeatOrderInfo saveInfo(SeatOrderInfo seatOrderInfo) {
        return seatOrderRepository.save(new SeatOrderEntity().parse(seatOrderInfo));
    }

    @Override
    public SeatOrderInfo deleteById(Long id) {
        return null;
    }

    @Override
    public SeatOrderInfo findInfoById(Long id) {
        SeatOrderEntity entity = seatOrderRepository.findOne(id);
        if(entity==null)
            return ErrorInfo.build(SeatOrderInfo.class);
        return entity.toInfo();
    }

    @Override
    public List<SeatOrderInfo> listAll(Map<String, Object> m) {
        List<SeatOrderEntity> l = seatOrderRepository.findAll(DynamicSpecifications.createSpecification(m));
        return new InfoList<>(l, (Callback<SeatOrderEntity, SeatOrderInfo>) (seatOrderEntity, info) -> seatOrderEntity.toInfo());
    }

    @Override
    public PageResult<SeatOrderInfo> pageAll(Map<String, Object> m, PageInfo pageInfo) {
        pageInfo = new PageInfo(m);
        Page<SeatOrderEntity> page = seatOrderRepository.findAll(DynamicSpecifications.createSpecification(m),pageInfo);
        return new PageResult<>(page, SeatOrderInfo.class, (seatOrderEntity, info) -> seatOrderEntity.toInfo());
    }
}
