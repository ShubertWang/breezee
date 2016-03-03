/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.prj.sodexo.repository;

import com.breezee.prj.sodexo.entity.MesshallEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Silence on 2016/2/13.
 */
@Repository
public interface MesshallRepository extends PagingAndSortingRepository<MesshallEntity,Long>,
        JpaSpecificationExecutor<MesshallEntity> {

    MesshallEntity findByCode(String code);
}
