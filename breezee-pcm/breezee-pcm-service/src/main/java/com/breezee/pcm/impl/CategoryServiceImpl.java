/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.pcm.impl;

import com.breezee.common.*;
import com.breezee.common.util.Callback;
import com.breezee.pcm.api.domain.CategoryInfo;
import com.breezee.pcm.api.service.ICategoryService;
import com.breezee.pcm.entity.CategoryEntity;
import com.breezee.pcm.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 品类服务实现类
 * Created by Silence on 2016/2/7.
 */
@Service("categoryService")
public class CategoryServiceImpl implements ICategoryService {

    @Qualifier("categoryRepository")
    @Autowired
    protected CategoryRepository categoryRepository;

    @Override
    public List<CategoryInfo> findCategoryByParentId(Long id) {
        if(id == -1){
            List<CategoryEntity> l = categoryRepository.findTop();
            List<CategoryInfo> _l = new ArrayList<>(l.size());
            l.forEach(a->{
                _l.add(a.toInfo(false));
            });
            return _l;
        }
        CategoryEntity en = categoryRepository.findOne(id);
        if(en==null)
            return new ArrayList<>();
        return en.toInfo(true).getChildren();
    }

    @Override
    public CategoryInfo saveInfo(CategoryInfo categoryInfo) {
        CategoryEntity entity = new CategoryEntity().parse(categoryInfo);
        if(categoryInfo.getParent()!=null && categoryInfo.getParent().getId()!=null){
            entity.setParent(categoryRepository.findOne(categoryInfo.getParent().getId()));
        }
        categoryRepository.save(entity);
        return SuccessInfo.build(CategoryInfo.class);
    }

    @Override
    public CategoryInfo findInfoById(Long id) {
        CategoryEntity en = categoryRepository.findOne(id);
        if(en==null)
            return NullInfo.build(CategoryInfo.class);
        return en.toInfo(false);
    }

    @Override
    public List<CategoryInfo> listAll(Map<String, Object> m) {
        List<CategoryEntity> l = (List<CategoryEntity>) categoryRepository.findAll();
        return new InfoList<>(l, (Callback<CategoryEntity, CategoryInfo>) (categoryEntity, categoryInfo) -> categoryEntity.toInfo(false));
    }

    @Override
    public PageResult<CategoryInfo> pageAll(Map<String, Object> m, PageInfo pageInfo) {
        return null;
    }
}
