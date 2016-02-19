/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.crm.repository;

import com.breezee.crm.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Silence on 2016/2/11.
 */
public interface UserRepository extends PagingAndSortingRepository<UserEntity,Long>,
        JpaSpecificationExecutor<UserEntity> {
}
