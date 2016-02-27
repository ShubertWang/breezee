/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.oms.repository;

import com.breezee.oms.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Silence on 2016/2/12.
 */
public interface InventoryRepository extends PagingAndSortingRepository<InventoryEntity,Long>,JpaSpecificationExecutor<InventoryEntity> {

    List<InventoryEntity> findBySkuId(String skuId);

    List<InventoryEntity> findByLocationId(String LocationId);

    InventoryEntity findBySkuIdAndLocationId(String skuId, String locationId);


}
