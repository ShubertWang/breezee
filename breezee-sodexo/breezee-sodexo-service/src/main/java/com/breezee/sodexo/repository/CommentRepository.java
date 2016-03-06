package com.breezee.sodexo.repository;

import com.breezee.sodexo.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Silence on 2016/3/3.
 */
@Repository
public interface CommentRepository extends PagingAndSortingRepository<CommentEntity,Long>,
        JpaSpecificationExecutor<CommentEntity> {

    CommentEntity findByUserIdAndObjectIdAndCommentTime(String userId,String objectId,String commentTime);

    CommentEntity findByCode(String code);

    @Query(value = "select count(t.cmt_id) from sdx_td_comments t where t.object_id=:objectId and t.value=:value", nativeQuery = true)
    long countObject(@Param("objectId") String objectId, @Param("value") Integer value);
}
