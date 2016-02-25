/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.crm.repository;

import com.breezee.crm.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Silence on 2016/2/11.
 */
@Repository("userRepository")
public interface UserRepository extends PagingAndSortingRepository<UserEntity,Long>,
        JpaSpecificationExecutor<UserEntity> {

    UserEntity findByCode(String code);

    UserEntity findByWechat(String wechat);

    UserEntity findByDn(String dn);
}
