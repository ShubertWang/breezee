/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sysmgr.repository;

import com.breezee.sysmgr.entity.AccountEntity;
import com.breezee.sysmgr.entity.OrganizationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Silence on 2016/2/11.
 */
@Repository("accountRepository")
public interface AccountRepository extends PagingAndSortingRepository<AccountEntity,Long>,
        JpaSpecificationExecutor<AccountEntity>{

    @Query("select c from AccountEntity c where c.organization=:org")
    Page<AccountEntity> findAccountsByOrg(@Param("org") OrganizationEntity org, Pageable page);

    AccountEntity findAccountByCode(String code);
}
