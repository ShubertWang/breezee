package com.breezee.sodexo.impl;

import com.breezee.common.*;
import com.breezee.common.util.Callback;
import com.breezee.sodexo.api.domain.ArticleInfo;
import com.breezee.sodexo.entity.ArticleEntity;
import com.breezee.sodexo.entity.ModelEntity;
import com.breezee.sodexo.repository.ArticleRepository;
import com.breezee.sodexo.repository.CommentRepository;
import com.breezee.sodexo.repository.ModelRepository;
import com.breezee.sodexo.api.service.IArticleService;
import com.breezee.sysmgr.api.domain.AccountInfo;
import com.breezee.sysmgr.api.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Resource
    private IAccountService accountService;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public PageResult<ArticleInfo> findByModelId(Long modelId, PageInfo page) {
        if (page == null) {
            page = new PageInfo();
        }
        page.setSort(new Sort(Sort.Direction.DESC, "updateTime"));
        ModelEntity entity = modelRepository.findOne(modelId);
        if (entity != null) {
            Page<ArticleEntity> pa = articleRepository.findByModel(entity, page);
            return new PageResult<>(pa, ArticleInfo.class, (articleEntity, articleInfo) -> articleEntity.toInfo());
        }
        return new PageResult<>();
    }

    @Override
    public PageResult<ArticleInfo> findByModelCode(String modelCode, PageInfo page) {
        if (page == null) {
            page = new PageInfo();
        }
        page.setSort(new Sort(Sort.Direction.ASC, "orderNo"));
        ModelEntity entity = modelRepository.findByCode(modelCode);
        if (entity != null) {
            Page<ArticleEntity> pa = articleRepository.findByModel(entity, page);
            return new PageResult<>(pa, ArticleInfo.class, (articleEntity, articleInfo) -> {
                ArticleInfo info = articleEntity.toInfo();
                AccountInfo accountInfo = accountService.findByCode(info.getUpdator());
                if (accountInfo != null)
                    info.setUserName(accountInfo.getName());
                info.getProperties().put("yc",commentRepository.countObject(info.getId().toString(),1));
                info.getProperties().put("nc",commentRepository.countObject(info.getId().toString(),0));
                return info;
            });
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
        pageInfo = new PageInfo(m);
        Page<ArticleEntity> pa = articleRepository.findAll(DynamicSpecifications.createSpecification(m), pageInfo);
        return new PageResult<>(pa, ArticleInfo.class, (articleEntity, articleInfo) -> articleEntity.toInfo());
    }

    @Override
    public void updateStatus(Long id, int status) {

    }
}
