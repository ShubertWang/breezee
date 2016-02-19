/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.pcm.repository;

import com.breezee.pcm.entity.CategoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Silence on 2016/2/7.
 */
@Repository("categoryRepository")
public interface CategoryRepository extends CrudRepository<CategoryEntity, Long> {

    @Query("select c from CategoryEntity c where c.parent is null")
    List<CategoryEntity> findTop();
}
