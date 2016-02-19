/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.prj.sodexo.repository;

import com.breezee.prj.sodexo.entity.SeatOrderEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Silence on 2016/2/16.
 */
@Repository
public interface SeatOrderRepository extends PagingAndSortingRepository<SeatOrderEntity,Long>,JpaSpecificationExecutor<SeatOrderEntity> {
}
