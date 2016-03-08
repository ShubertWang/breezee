/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sodexo.impl;

import com.breezee.bpm.api.service.IWorkflowService;
import com.breezee.common.*;
import com.breezee.common.util.Callback;
import com.breezee.sodexo.api.domain.FoodLineInfo;
import com.breezee.sodexo.api.domain.SeatOrderInfo;
import com.breezee.sodexo.api.service.IFoodLineService;
import com.breezee.sodexo.api.service.ISeatOrderService;
import com.breezee.sodexo.entity.SeatOrderEntity;
import com.breezee.sodexo.repository.SeatOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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

    @Autowired
    private IFoodLineService foodLineService;

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
        long t = seatOrderRepository.count(DynamicSpecifications.createSpecification(Collections.singletonMap("status",2)));
        SeatOrderInfo info = entity.toInfo();
        info.setQueueNo(t);
        setRestaurant(info);
        return info;
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

    @Override
    public void updateStatus(Long id, int status){
        SeatOrderEntity entity = seatOrderRepository.findOne(id);
        entity.setStatus(status);
        seatOrderRepository.save(entity);
    }

    @Override
    public void updateStatusAndNo(Long id, int status, String seatNo) {
        SeatOrderEntity entity = seatOrderRepository.findOne(id);
        entity.setStatus(status);
        if(seatNo!=null)
            entity.setSeatNo(seatNo);
        seatOrderRepository.save(entity);
    }

    @Override
    public PageResult<SeatOrderInfo> findMySeat(Long userId, PageInfo pageInfo) {
        if (pageInfo == null) {
            pageInfo = new PageInfo();
        }
        pageInfo.setSort(new Sort(Sort.Direction.DESC, "issueDate"));
        Page<SeatOrderEntity> page = seatOrderRepository.findByUserId(userId, pageInfo);
        return new PageResult<>(page, SeatOrderInfo.class, (orderEntity, orderInfo) -> {
            SeatOrderInfo info = orderEntity.toInfo();
            setRestaurant(info);
            return info;
        });
    }

    private void setRestaurant(SeatOrderInfo info) {
        FoodLineInfo foodLineInfo = foodLineService.findByCode(info.getStoreName());
        if (foodLineInfo.getId() > 0 && foodLineInfo.getMesshallInfo() != null) {
            info.setRestaurantName(foodLineInfo.getMesshallInfo().getName());
        }
    }
}
