/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.pcm.repository;

import com.breezee.pcm.entity.AttributeEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Silence on 2016/2/7.
 */
@Repository("attributeRepository")
public interface AttributeRepository extends PagingAndSortingRepository<AttributeEntity, Long>,
        JpaSpecificationExecutor<AttributeEntity> {

    AttributeEntity findByCode(String code);
}
