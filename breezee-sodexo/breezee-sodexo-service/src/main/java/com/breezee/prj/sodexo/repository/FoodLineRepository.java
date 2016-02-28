/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.prj.sodexo.repository;

import com.breezee.prj.sodexo.entity.FoodLineEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
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

    List<FoodLineEntity> findBySiteAndShipping(String site,String shipping);

    FoodLineEntity findByCode(String code);

    FoodLineEntity findByOrgId(Long orgId);

}
