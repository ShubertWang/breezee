/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.pcm.repository;

import com.breezee.pcm.entity.AttributeEntity;
import com.breezee.pcm.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Silence on 2016/2/7.
 */
@Repository("attributeRepository")
public interface AttributeRepository extends PagingAndSortingRepository<AttributeEntity, Long>,
        JpaSpecificationExecutor<AttributeEntity> {

    AttributeEntity findByCode(String code);

    @Query("select a from AttributeEntity a,CateAttrEntity c where c.attribute=a and c.category!=:categoryEntity")
    Page<AttributeEntity> findAttrsNotForCateId(@Param("category")CategoryEntity categoryEntity, Pageable pageable);
}
