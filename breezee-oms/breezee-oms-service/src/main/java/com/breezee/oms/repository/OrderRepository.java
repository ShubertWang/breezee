/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.oms.repository;

import com.breezee.oms.entity.InventoryEntity;
import com.breezee.oms.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Created by Silence on 2016/2/12.
 */
@Repository
public interface OrderRepository extends PagingAndSortingRepository<OrderEntity,Long>,
        JpaSpecificationExecutor<OrderEntity> {

    List<OrderEntity> findByCode(String code);

    List<OrderEntity> findByCodeIn(Collection<String> codes);
}
