/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sodexo.impl;

import com.breezee.bpm.api.service.IWorkflowService;
import com.breezee.common.*;
import com.breezee.common.util.Callback;
import com.breezee.sodexo.api.domain.SeatOrderInfo;
import com.breezee.sodexo.entity.SeatOrderEntity;
import com.breezee.sodexo.repository.SeatOrderRepository;
import com.breezee.sodexo.api.service.ISeatOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Silence on 2016/2/16.
 */
@Service("seatOrderService")
public class SeatOrderServiceImpl implements ISeatOrderService {

    @Autowired
    private Environment environment;

    @Autowired
    private SeatOrderRepository seatOrderRepository;

    @Resource
    private IWorkflowService workflowServiceImpl;

    @Override
    public SeatOrderInfo saveInfo(SeatOrderInfo orderInfo) {
        if (orderInfo.getId() == null)
            orderInfo.setCode(String.format("%05d", orderInfo.getUserId()) + Long.valueOf(System.currentTimeMillis()-1457000000000L).toString());
        SeatOrderEntity entity = new SeatOrderEntity().parse(orderInfo);
        entity.setIssueDate(new Date());
        entity.setName(orderInfo.getCode());
        seatOrderRepository.save(entity);
        //启动流程
        if (orderInfo.getTaskId() == null || orderInfo.getTaskId() < 0) {
            Map<String, Object> vars = new HashMap<>();
            //注意第一次保存启动流程orderInfo的ProcDefId和code一定要有值
            vars.put("seatLineRole", entity.getStoreName());
            vars.put("startUser", entity.getUserId());
            vars.put("orderId", entity.getId());
            workflowServiceImpl.startProcessInstanceById(orderInfo.getProcDefId(), entity.getId().toString(), vars);
        }
        orderInfo.setId(entity.getId());
        return SuccessInfo.build(orderInfo);
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
