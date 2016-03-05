/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.pcm.entity;

import com.breezee.common.BaseInfo;
import com.breezee.pcm.api.domain.CategoryInfo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

/**
 * 对应数据库的实体类
 * Created by Silence on 2016/2/7.
 */
@Entity
@Table(name = "PCM_TD_CATEGORY")
public class CategoryEntity extends BaseInfo {

    protected String icon;

    protected CategoryEntity parent;

    protected Set<CategoryEntity> children;

    protected Set<CateAttrEntity> cateAttrs;

    protected Set<ProductEntity> products;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATE_ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "CATE_NAME", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    @Column(name = "CATE_CODE", unique = true, updatable = false, nullable = false, length = 64)
    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }

    public String getTenantId() {
        return tenantId;
    }

    public String getCreator() {
        return creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getUpdator() {
        return updator;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public String getRemark() {
        return remark;
    }

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @OrderBy(value="orderNo ASC")
    public Set<CateAttrEntity> getCateAttrs() {
        return cateAttrs;
    }

    public void setCateAttrs(Set<CateAttrEntity> cateAttrs) {
        this.cateAttrs = cateAttrs;
    }

    @OneToMany(mappedBy = "category", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @OrderBy(value="id DESC")
    public Set<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductEntity> products) {
        this.products = products;
    }

    @ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name="PARENT_ID")
    public CategoryEntity getParent() {
        return parent;
    }

    public void setParent(CategoryEntity parent) {
        this.parent = parent;
    }

    @OneToMany(mappedBy="parent", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @OrderBy(value="id DESC")
    public Set<CategoryEntity> getChildren() {
        return children;
    }

    public void setChildren(Set<CategoryEntity> children) {
        this.children = children;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * 实体映射到领域对象上
     * @param loadChild
     * @return
     */
    public CategoryInfo toInfo(boolean loadChild){
        CategoryInfo info = new CategoryInfo();
        cloneAttributeTo(info);
        if(this.getChildren()!=null && this.getChildren().size()>0){
            if(loadChild) {
                info.setChildren(new ArrayList<>());
                this.getChildren().forEach(a -> {
                    info.getChildren().add(a.toInfo(false));
                });
            }
            info.setLeaf(false);
        }
        if(this.getParent()!=null){
            CategoryInfo pInfo = new CategoryInfo();
            this.getParent().cloneAttributeTo(pInfo);
            info.setParent(pInfo);
        }
        info.setIcon(this.getIcon());
        return info;
    }

    public CategoryEntity parse(CategoryInfo categoryInfo){
        categoryInfo.cloneAttributeTo(this);
        this.setIcon(categoryInfo.getIcon());
        return this;
    }
}
