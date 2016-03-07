package com.breezee.sodexo.repository;

import com.breezee.sodexo.entity.VisitCountEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Silence on 2016/3/7.
 */
@Repository("visitCountRepository")
public interface VisitCountRepository extends PagingAndSortingRepository<VisitCountEntity,String>,
        JpaSpecificationExecutor<VisitCountEntity> {
}
