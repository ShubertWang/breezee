/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sodexo.repository;

import com.breezee.sodexo.entity.SeatOrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Silence on 2016/2/16.
 */
@Repository
public interface SeatOrderRepository extends PagingAndSortingRepository<SeatOrderEntity,Long>,JpaSpecificationExecutor<SeatOrderEntity> {
    Page<SeatOrderEntity> findByUserId(Long userId, Pageable pageable);
}
