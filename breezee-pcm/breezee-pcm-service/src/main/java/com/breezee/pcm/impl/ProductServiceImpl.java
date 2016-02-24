/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.pcm.impl;

import com.breezee.common.InfoList;
import com.breezee.common.PageInfo;
import com.breezee.common.PageResult;
import com.breezee.common.SuccessInfo;
import com.breezee.common.util.Callback;
import com.breezee.common.DynamicSpecifications;
import com.breezee.pcm.api.domain.ProductInfo;
import com.breezee.pcm.api.service.IProductService;
import com.breezee.pcm.entity.CategoryEntity;
import com.breezee.pcm.entity.ProductDataEntity;
import com.breezee.pcm.entity.ProductEntity;
import com.breezee.pcm.repository.AttributeRepository;
import com.breezee.pcm.repository.CategoryRepository;
import com.breezee.pcm.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 产品服务
 * Created by Silence on 2016/2/7.
 */
@Service("productService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AttributeRepository attributeRepository;

    @Override
    public List<ProductInfo> findProductsByCateId(Long cateId, boolean rec){
        CategoryEntity categoryEntity = categoryRepository.findOne(cateId);
        List<ProductEntity> l = new ArrayList<>();
        Set<ProductEntity> s = categoryEntity.getProducts();
        if(s != null && s.size()>0){
            l.addAll(s);
        }
        List<ProductInfo> ll = new ArrayList<>();
        l.forEach(a->{
            ll.add(a.toInfo());
        });
        if(rec){
            Set<CategoryEntity> ss = categoryEntity.getChildren();
            if(ss != null && ss.size()>0){
                ss.forEach(a->{
                    ll.addAll(findProductsByCateId(a.getId(),true));
                });
            }
        }
        return ll;
    }

    @Override
    public ProductInfo saveInfo(ProductInfo productInfo) {
        ProductEntity entity = new ProductEntity().parse(productInfo);
        entity.setCategory(categoryRepository.findOne(productInfo.getCateId()));
        Set<ProductDataEntity> set = new HashSet<>();
        productInfo.getValues().forEach((a,b)->{
            ProductDataEntity e = new ProductDataEntity();
            e.setAttribute(attributeRepository.findByCode(a));
            e.setAttrValue(b.toString());
            e.setProduct(entity);
            set.add(e);
        });
        entity.setData(set);
        productRepository.save(entity);
        return SuccessInfo.build(ProductInfo.class);
    }

    @Override
    public ProductInfo findInfoById(Long id) {
        ProductEntity entity = productRepository.findOne(id);
        ProductInfo info = entity.toInfo();
        return info;
    }

    @Override
    public List<ProductInfo> listAll(Map<String, Object> m) {
        List<ProductEntity> l = productRepository.findAll(DynamicSpecifications.createSpecification(m));
        return new InfoList<>(l, (Callback<ProductEntity, ProductInfo>) (productEntity, productInfo) -> productEntity.toInfo());
    }

    @Override
    public PageResult<ProductInfo> pageAll(Map<String, Object> m, PageInfo pageInfo) {
        Page<ProductEntity> page = productRepository.findAll(DynamicSpecifications.createSpecification(m),pageInfo);
        return new PageResult<>(page, ProductInfo.class, (productEntity, productInfo) -> productEntity.toInfo());
    }
}
