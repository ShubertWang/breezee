/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sysmgr.repository;

import com.breezee.sysmgr.entity.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Silence on 2016/2/11.
 */
@Repository("roleRepository")
public interface RoleRepository extends CrudRepository<RoleEntity,Long>{
}
