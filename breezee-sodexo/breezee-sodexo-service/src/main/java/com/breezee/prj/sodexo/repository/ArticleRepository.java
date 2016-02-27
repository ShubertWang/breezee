package com.breezee.prj.sodexo.repository;

import com.breezee.prj.sodexo.entity.ArticleEntity;
import com.breezee.prj.sodexo.entity.ModelEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * fafa
 * Created by Zhong, An-Jing on 2015/12/20.
 */
@Repository("articleRepository")
public interface ArticleRepository extends PagingAndSortingRepository<ArticleEntity, Long>,
        JpaSpecificationExecutor<ArticleEntity> {

    Page<ArticleEntity> findByModel(ModelEntity categoryEntity, Pageable page);
}
