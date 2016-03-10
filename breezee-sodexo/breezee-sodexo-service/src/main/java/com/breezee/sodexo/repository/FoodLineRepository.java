/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sodexo.repository;

import com.breezee.sodexo.entity.FoodLineEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Silence on 2016/2/13.
 */
@Repository
public interface FoodLineRepository extends PagingAndSortingRepository<FoodLineEntity,Long>,
        JpaSpecificationExecutor<FoodLineEntity> {

    List<FoodLineEntity> findByMesshallId(Long messhallId);

    List<FoodLineEntity> findBySite(String site);

    @Query("select f from FoodLineEntity f where f.site=:site and f.shipping=:shipping and f.status=1")
    List<FoodLineEntity> findBySiteAndShipping(@Param("site") String site, @Param("shipping")String shipping);

    FoodLineEntity findByCode(String code);

    List<FoodLineEntity> findByOrgId(Long orgId);

}
