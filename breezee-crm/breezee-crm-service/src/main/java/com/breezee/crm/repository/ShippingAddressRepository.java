/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.crm.repository;

import com.breezee.crm.entity.ShippingAddressEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Silence on 2016/2/11.
 */
@Repository("shippingAddressRepository")
public interface ShippingAddressRepository extends CrudRepository<ShippingAddressEntity,Long> {
}
