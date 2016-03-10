/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sysmgr.repository;

import com.breezee.sysmgr.entity.DictEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Silence on 2016/2/10.
 */
@Repository("dictRepository")
public interface DictRepository extends PagingAndSortingRepository<DictEntity,Long>,JpaSpecificationExecutor<DictEntity> {

    DictEntity findByCode(String code);

    @Modifying
    @Transactional
    @Query("delete from DictDetailEntity m where m.dict=:dict")
    void deleteDetails(@Param("dict") DictEntity dict);
}
