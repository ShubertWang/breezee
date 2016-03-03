/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.pcm.repository;

import com.breezee.pcm.entity.CategoryEntity;
import com.breezee.pcm.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.ws.rs.QueryParam;
import java.util.List;

/**
 * Created by Silence on 2016/2/7.
 */
@Repository("productRepository")
public interface ProductRepository extends PagingAndSortingRepository<ProductEntity, Long>,
        JpaSpecificationExecutor<ProductEntity> {

    ProductEntity findByCode(String code);

    @Query("select p from ProductEntity p where p.category=:category and p.recommend = true")
    List<ProductEntity> findRecom(@Param("category") CategoryEntity categoryEntity);
}
