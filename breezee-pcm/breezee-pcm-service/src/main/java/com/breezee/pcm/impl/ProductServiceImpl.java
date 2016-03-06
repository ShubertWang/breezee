/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.pcm.impl;

import com.breezee.common.*;
import com.breezee.common.types.Quantity;
import com.breezee.common.util.Callback;
import com.breezee.common.util.ContextUtil;
import com.breezee.oms.api.domain.InventoryInfo;
import com.breezee.oms.api.service.InventoryService;
import com.breezee.pcm.api.domain.ProductInfo;
import com.breezee.pcm.api.service.IProductService;
import com.breezee.pcm.entity.CategoryEntity;
import com.breezee.pcm.entity.ProductDataEntity;
import com.breezee.pcm.entity.ProductEntity;
import com.breezee.pcm.repository.AttributeRepository;
import com.breezee.pcm.repository.CategoryRepository;
import com.breezee.pcm.repository.ProductRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.ws.rs.PathParam;
import java.util.*;

/**
 * 产品服务
 * Created by Silence on 2016/2/7.
 */
@Service("productService")
public class ProductServiceImpl implements IProductService, InitializingBean {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AttributeRepository attributeRepository;

    @Resource
    private InventoryService inventoryService;

    @Autowired
    private DataSource dataSource;

    @Override
    public List<ProductInfo> findProductsByCateId(Long cateId, boolean rec) {
        CategoryEntity categoryEntity = categoryRepository.findOne(cateId);
        List<ProductEntity> l = new ArrayList<>();
        Set<ProductEntity> s = categoryEntity.getProducts();
        if (s != null && s.size() > 0) {
            l.addAll(s);
        }
        List<ProductInfo> ll = new ArrayList<>();
        l.forEach(a -> {
            ll.add(a.toInfo());
        });
        if (rec) {
            Set<CategoryEntity> ss = categoryEntity.getChildren();
            if (ss != null && ss.size() > 0) {
                ss.forEach(a -> {
                    ll.addAll(findProductsByCateId(a.getId(), true));
                });
            }
        }
        return ll;
    }

    @Override
    public List<ProductInfo> findProductsByCateCode(String cateCode) {
        CategoryEntity categoryEntity = categoryRepository.findByCode(cateCode);
        List<ProductEntity> l = new ArrayList<>();
        Set<ProductEntity> s = categoryEntity.getProducts();
        if (s != null && s.size() > 0) {
            l.addAll(s);
        }
        List<ProductInfo> ll = new ArrayList<>();
        l.forEach(a -> {
            ll.add(a.toInfo());
        });
        return ll;
    }

    @Override
    public ProductInfo findByCode(String code) {
        ProductEntity entity = productRepository.findByCode(code);
        if (entity == null)
            return ErrorInfo.build(ProductInfo.class);
        return entity.toInfo();
    }

    @Override
    public List<ProductInfo> findProductByLocationId(String locationId) {
        List<InventoryInfo> l = inventoryService.findInventoryByLocationId(locationId);
        List<ProductInfo> ll = new ArrayList<>(l.size());
        ProductEntity entity;
        for (InventoryInfo inventoryInfo : l) {
            entity = productRepository.findByCode(inventoryInfo.getSkuId());
            if (entity != null) {
                ProductInfo productInfo = entity.toInfo();
                productInfo.setQuantity(inventoryInfo.getQuantity());
                ll.add(productInfo);
            }
        }
        return ll;
    }

    @Override
    public void updateRecommend(Long id, boolean recommend) {
        ProductEntity entity = productRepository.findOne(id);
        if (entity != null) {
            entity.setRecommend(recommend);
            productRepository.save(entity);
        }
    }

    @Override
    public List<ProductInfo> findRecomProductByCateId(String cateId) {
        CategoryEntity en = categoryRepository.findByCode(cateId);
        if (en == null) {
            return new ArrayList<>();
        }
        Set<CategoryEntity> ch = en.getChildren();
        List<ProductEntity> l = new ArrayList<>();
        if (ch != null) {
            ch.forEach(a -> {
                l.addAll(productRepository.findRecom(a));
            });
        }
        return new InfoList<>(l, (Callback<ProductEntity, ProductInfo>) (productEntity, productInfo) -> {
            ProductInfo info = productEntity.toInfo();
            return setStock(info);
        });
    }

    private ProductInfo setStock(ProductInfo info) {
        List<InventoryInfo> l1 = inventoryService.findInventoryBySkuId(info.getCode());
        int sum = 0;
        for (InventoryInfo a : l1) {
            sum = sum + a.getQuantity().getValue();
        }
        info.setQuantity(new Quantity("", sum));
        return info;
    }

    @Override
    public ProductInfo saveInfo(ProductInfo productInfo) {
        if (productInfo.getId() == null) {
            productInfo.setCode(Long.valueOf(System.currentTimeMillis()).toString());
        }
        ProductEntity entity = productRepository.findByCode(productInfo.getCode());
        //如果新增的账号已经存在，则返回错误信息
        if (productInfo.getId() == null && entity != null) {
            return ErrorInfo.build(productInfo, ContextUtil.getMessage("duplicate.key", new String[]{productInfo.getCode()}));
        }
        if (entity == null) {
            entity = new ProductEntity();
        }
        entity.parse(productInfo);
        entity.setCategory(categoryRepository.findOne(productInfo.getCateId()));
        Set<ProductDataEntity> set = new HashSet<>();
        final ProductEntity finalEntity = entity;
        productInfo.getProductData().forEach((a, b) -> {
            if(StringUtils.hasText(b.toString())) {
                ProductDataEntity e = new ProductDataEntity();
                e.setAttribute(attributeRepository.findOne(Long.parseLong(a)));
                e.setAttrValue(b.toString());
                e.setProduct(finalEntity);
                set.add(e);
            }
        });
        if (entity.getData() == null)
            entity.setData(set);
        else
            entity.getData().addAll(set);
        productRepository.save(entity);
        return SuccessInfo.build(ProductInfo.class);
    }

    @Override
    public ProductInfo deleteById(Long id) {
        return null;
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
        pageInfo = new PageInfo(pageInfo, m);
        pageInfo.setSort(new Sort(Sort.Direction.DESC,"recommend","updateTime"));
        Page<ProductEntity> page = productRepository.findAll(DynamicSpecifications.createSpecification(m), pageInfo);
        return new PageResult<>(page, ProductInfo.class, (productEntity, productInfo) -> {
            ProductInfo info = productEntity.toInfo();
            return setStock(info);
        });
    }

    @Override
    public void updateStatus(@PathParam("id") Long id, @PathParam("status") int status) {
        ProductEntity entity = productRepository.findOne(id);
        if (entity != null) {
            entity.setStatus(status);
            productRepository.save(entity);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
