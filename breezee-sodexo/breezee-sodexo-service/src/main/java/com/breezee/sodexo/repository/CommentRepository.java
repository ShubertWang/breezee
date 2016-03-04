package com.breezee.sodexo.repository;

import com.breezee.sodexo.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Silence on 2016/3/3.
 */
@Repository
public interface CommentRepository extends PagingAndSortingRepository<CommentEntity,Long>,
        JpaSpecificationExecutor<CommentEntity> {

    CommentEntity findByUserIdAndObjectIdAndCommentTime(String userId,String objectId,String commentTime);
}
