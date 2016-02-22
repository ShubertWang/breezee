/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sysmgr.repository;

import com.breezee.sysmgr.entity.OrganizationEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Silence on 2016/2/11.
 */
@Repository("organizationRepository")
public interface OrganizationRepository extends CrudRepository<OrganizationEntity,Long> {

    @Query("select c from OrganizationEntity c where c.parent is null")
    List<OrganizationEntity> findTop();

    OrganizationEntity findByCode(String code);

}
