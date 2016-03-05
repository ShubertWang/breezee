package com.breezee.sodexo.repository;

import com.breezee.sodexo.entity.OtherRequestEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Silence on 2016/3/5.
 */
@Repository("otherRequestRepository")
public interface OtherRequestRepository extends PagingAndSortingRepository<OtherRequestEntity,Long>,
        JpaSpecificationExecutor<OtherRequestEntity> {
}
