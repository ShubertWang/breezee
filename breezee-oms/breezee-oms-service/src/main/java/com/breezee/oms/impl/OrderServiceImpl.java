/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.oms.impl;

import com.breezee.bpm.api.domain.ProcsInsInfo;
import com.breezee.bpm.api.service.IWorkflowService;
import com.breezee.common.*;
import com.breezee.common.util.Callback;
import com.breezee.common.util.ContextUtil;
import com.breezee.oms.api.domain.OrderInfo;
import com.breezee.oms.api.domain.OrderLineInfo;
import com.breezee.oms.api.service.IOrderService;
import com.breezee.oms.entity.InventoryEntity;
import com.breezee.oms.entity.OrderEntity;
import com.breezee.oms.repository.InventoryRepository;
import com.breezee.oms.repository.OrderRepository;
import com.breezee.sodexo.api.domain.FoodLineInfo;
import com.breezee.sodexo.api.service.IFoodLineService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Silence on 2016/2/12.
 */
@Service("orderService")
public class OrderServiceImpl implements IOrderService, InitializingBean {

    @Autowired
    private OrderRepository orderRepository;

    @Resource
    private IWorkflowService workflowServiceImpl;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private IFoodLineService foodLineService;

    @Override
    public OrderInfo saveInfo(OrderInfo orderInfo) {
        String msg = checkInventory(orderInfo);
        if (msg != null) {
            return ErrorInfo.build(orderInfo, msg);
        }
        if (orderInfo.getId() == null)
            orderInfo.setCode(String.format("%05d", orderInfo.getUserId()) + Long.valueOf(System.currentTimeMillis() - 1457000000000L).toString());
        OrderEntity entity = new OrderEntity().parse(orderInfo);
        entity.setIssueDate(new Date());
        entity.setName(orderInfo.getCode());
        orderRepository.save(entity);
        //启动流程
        if (entity.getId() > 0 && orderInfo.getTaskId() == null) {
            Map<String, Object> vars = new HashMap<>();
            //注意第一次保存启动流程orderInfo的ProcDefId和code一定要有值
            vars.put("foodLineRole", entity.getStoreName());
            vars.put("startUser", entity.getUserId());
            vars.put("orderId", entity.getId());
            ProcsInsInfo procsInsInfo = workflowServiceImpl.startProcessInstanceById(orderInfo.getProcDefId(), entity.getId().toString(), vars);
            orderInfo.setTaskId(Long.parseLong(procsInsInfo.getCode()));
            //发送通知消息的时候可以进入任务，执行任务操作.同时也支持在任务取消时候取消任务。
            entity.setTaskId(orderInfo.getTaskId());
            orderRepository.save(entity);
        }

        orderInfo.setId(entity.getId());
        return SuccessInfo.build(orderInfo);
    }

    /**
     * 检查Sku库存,并减库存
     *
     * @param info
     * @return
     */
    private String checkInventory(OrderInfo info) {
        List<OrderLineInfo> l = info.getOrderLines();
        if (l != null) {
            for (OrderLineInfo a : l) {
                List<InventoryEntity> entitys = inventoryRepository.findBySkuId(a.getSkuId());
                if (entitys.size() == 0)
                    return ContextUtil.getMessage("inventory.no.exist", new Object[]{a.getSkuId()});
                if (entitys.get(0).getQuantity() < 1)
                    return ContextUtil.getMessage("inventory.lack", new Object[]{a.getSkuId()});
                entitys.get(0).setQuantity(entitys.get(0).getQuantity() - 1);
                inventoryRepository.save(entitys.get(0));
            }
        }
        return null;
    }

    @Override
    public OrderInfo deleteById(Long id) {
        return null;
    }

    @Override
    public OrderInfo findInfoById(Long id) {
        OrderEntity entity = orderRepository.findOne(id);
        if (entity == null)
            return ErrorInfo.build(OrderInfo.class);
        OrderInfo info = entity.toInfo();
        info.setStatusName(ContextUtil.getMessage("order.status." + info.getStatus()));
        setRestaurant(info);
        return info;
    }

    @Override
    public List<OrderInfo> listAll(Map<String, Object> m) {
        List<OrderEntity> l = orderRepository.findAll(DynamicSpecifications.createSpecification(m));
        return new InfoList<>(l, (Callback<OrderEntity, OrderInfo>) (orderEntity, orderInfo) -> orderEntity.toInfo());
    }

    @Override
    public PageResult<OrderInfo> pageAll(Map<String, Object> m, PageInfo pageInfo) {
        pageInfo = new PageInfo(m);
        if (m.get("username") != null) {
            m.remove("username");
        }
        pageInfo.setSort(new Sort(Sort.Direction.DESC, "issueDate"));
        Page<OrderEntity> page = orderRepository.findAll(DynamicSpecifications.createSpecification(m), pageInfo);
        return new PageResult<>(page, OrderInfo.class, (orderEntity, orderInfo) -> orderEntity.toInfo());
    }

    @Override
    public OrderInfo findOrderInfoByCode(String code) {
        OrderEntity entity = orderRepository.findByCode(code);
        if (entity == null)
            return ErrorInfo.build(OrderInfo.class);
        OrderInfo info = entity.toInfo();
        info.setStatusName(ContextUtil.getMessage("order.status." + info.getStatus()));
        return info;
    }

    @Override
    public List<OrderInfo> findOrderInfoListByCodes(Collection<String> codes) {
        List<OrderInfo> orderInfoList = Lists.newArrayList();
        List<OrderEntity> entityList = orderRepository.findByCodeIn(codes);
        if (entityList == null) {
            orderInfoList.add(ErrorInfo.build(OrderInfo.class));
        } else {
            for (OrderEntity item : entityList) {
                orderInfoList.add(item.toInfo());
            }
        }
        return orderInfoList;
    }

    @Override
    public PageResult<OrderInfo> findMyOrder(Long userId, PageInfo pageInfo) {
        if (pageInfo == null) {
            pageInfo = new PageInfo();
        }
        pageInfo.setSort(new Sort(Sort.Direction.DESC, "issueDate"));
        Page<OrderEntity> page = orderRepository.findByUserId(userId, pageInfo);
        return new PageResult<>(page, OrderInfo.class, (orderEntity, orderInfo) -> {
            OrderInfo info = orderEntity.toInfo();
            setRestaurant(info);
            return info;
        });
    }

    @Override
    public OrderInfo orderPay(String orderId, String payId) {
        OrderEntity entity = orderRepository.findOne(Long.parseLong(orderId));
        entity.setPayId(payId);
        entity.setStatus(1);
        orderRepository.save(entity);
        return entity.toInfo();
    }

    @Override
    public void updateRejectReason(Long id, String reason) {
        OrderEntity entity = orderRepository.findOne(id);
        entity.setRejectReason(reason);
        orderRepository.save(entity);
    }

    private void setRestaurant(OrderInfo info) {
        FoodLineInfo foodLineInfo = foodLineService.findByCode(info.getStoreName());
        if (foodLineInfo.getId() > 0 && foodLineInfo.getMesshallInfo() != null) {
            info.setRestaurantName(foodLineInfo.getMesshallInfo().getName());
            info.setRestaurantImage(foodLineInfo.getMesshallInfo().getImageCode());
        }
    }

    /**
     * 更新状态
     *
     * @param id
     * @param status
     */
    public void updateStatus(Long id, int status) {
        OrderEntity entity = orderRepository.findOne(id);
        entity.setStatus(status);
        orderRepository.save(entity);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

}
