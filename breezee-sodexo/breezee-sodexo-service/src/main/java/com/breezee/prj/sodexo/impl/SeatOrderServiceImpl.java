/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.prj.sodexo.impl;

import com.breezee.common.InfoList;
import com.breezee.common.NullInfo;
import com.breezee.common.PageInfo;
import com.breezee.common.PageResult;
import com.breezee.common.util.Callback;
import com.breezee.common.util.SpecificationUtil;
import com.breezee.prj.sodexo.domain.SeatOrderInfo;
import com.breezee.prj.sodexo.entity.SeatOrderEntity;
import com.breezee.prj.sodexo.repository.SeatOrderRepository;
import com.breezee.prj.sodexo.service.ISeatOrderService;
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
    public SeatOrderInfo findInfoById(Long id) {
        SeatOrderEntity entity = seatOrderRepository.findOne(id);
        if(entity==null)
            return NullInfo.build(SeatOrderInfo.class);
        return entity.toInfo();
    }

    @Override
    public List<SeatOrderInfo> listAll(Map<String, Object> m) {
        List<SeatOrderEntity> l = seatOrderRepository.findAll(SpecificationUtil.createSpecification(m));
        return new InfoList<>(l, (Callback<SeatOrderEntity, SeatOrderInfo>) (seatOrderEntity, info) -> seatOrderEntity.toInfo());
    }

    @Override
    public PageResult<SeatOrderInfo> pageAll(Map<String, Object> m, PageInfo pageInfo) {
        Page<SeatOrderEntity> page = seatOrderRepository.findAll(SpecificationUtil.createSpecification(m),pageInfo);
        return new PageResult<>(page, SeatOrderInfo.class, (seatOrderEntity, info) -> seatOrderEntity.toInfo());
    }
}
