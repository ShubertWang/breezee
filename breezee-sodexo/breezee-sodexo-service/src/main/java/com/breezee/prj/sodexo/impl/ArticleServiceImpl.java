package com.breezee.prj.sodexo.impl;

import com.breezee.common.*;
import com.breezee.common.util.Callback;
import com.breezee.prj.sodexo.domain.ArticleInfo;
import com.breezee.prj.sodexo.entity.ArticleEntity;
import com.breezee.prj.sodexo.entity.ModelEntity;
import com.breezee.prj.sodexo.repository.ArticleRepository;
import com.breezee.prj.sodexo.repository.ModelRepository;
import com.breezee.prj.sodexo.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Silence on 2016/2/27.
 */
@Service("articleService")
public class ArticleServiceImpl implements IArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Override
    public PageResult<ArticleInfo> findByModelId(Long modelId, PageInfo page) {
        if(page==null){
            page = new PageInfo();
        }
        page.setSort(new Sort(Sort.Direction.DESC,"updateTime"));
        ModelEntity entity = modelRepository.findOne(modelId);
        if (entity != null) {
            Page<ArticleEntity> pa = articleRepository.findByModel(entity, page);
            return new PageResult<>(pa, ArticleInfo.class, (articleEntity, articleInfo) -> articleEntity.toInfo());
        }
        return new PageResult<>();
    }

    @Override
    public PageResult<ArticleInfo> findByModelCode(String modelCode, PageInfo page) {
        if(page==null){
            page = new PageInfo();
        }
        page.setSort(new Sort(Sort.Direction.DESC,"updateTime"));
        ModelEntity entity = modelRepository.findByCode(modelCode);
        if (entity != null) {
            Page<ArticleEntity> pa = articleRepository.findByModel(entity, page);
            return new PageResult<>(pa, ArticleInfo.class, (articleEntity, articleInfo) -> articleEntity.toInfo());
        }
        return new PageResult<>();
    }

    @Override
    public ArticleInfo saveInfo(ArticleInfo articleInfo) {
        ArticleEntity entity;
        if (articleInfo.getId() != null) {
            entity = articleRepository.findOne(articleInfo.getId());
            entity.parse(articleInfo);
        } else {
            entity = new ArticleEntity().parse(articleInfo);
            ModelEntity modelEntity = modelRepository.findOne(articleInfo.getModelId());
            entity.setModel(modelEntity);
        }
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        articleRepository.save(entity);
        return SuccessInfo.build(ArticleInfo.class);
    }

    @Override
    public ArticleInfo deleteById(Long id) {
        articleRepository.delete(id);
        return SuccessInfo.build(ArticleInfo.class);
    }

    @Override
    public ArticleInfo findInfoById(Long id) {
        ArticleEntity entity = articleRepository.findOne(id);
        if (entity == null)
            return ErrorInfo.build(ArticleInfo.class);
        return entity.toInfo();
    }

    @Override
    public List<ArticleInfo> listAll(Map<String, Object> m) {
        List<ArticleEntity> l = articleRepository.findAll(DynamicSpecifications.createSpecification(m));
        return new InfoList<>(l, (Callback<ArticleEntity, ArticleInfo>) (articleEntity, info) -> articleEntity.toInfo());
    }

    @Override
    public PageResult<ArticleInfo> pageAll(Map<String, Object> m, PageInfo pageInfo) {
        Page<ArticleEntity> pa = articleRepository.findAll(DynamicSpecifications.createSpecification(m), new PageInfo(pageInfo,m));
        return new PageResult<>(pa, ArticleInfo.class, (articleEntity, articleInfo) -> articleEntity.toInfo());
    }

    @Override
    public void updateStatus(Long id, int status) {

    }
}
