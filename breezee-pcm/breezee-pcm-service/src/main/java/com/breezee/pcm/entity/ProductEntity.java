/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.pcm.entity;

import com.breezee.common.BaseInfo;
import com.breezee.common.types.Amount;
import com.breezee.pcm.api.domain.ProductInfo;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by Silence on 2016/2/7.
 */
@Entity
@Table(name = "PCM_TF_PRODUCT")
public class ProductEntity extends BaseInfo {

    protected CategoryEntity category;

    protected Set<ProductDataEntity> data;

    private Double basePrice;

    private String currencyCode;

    private boolean recommend;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "PRODUCT_NAME", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    @Column(name = "PRODUCT_CODE", unique = true, updatable = false, nullable = false, length = 64)
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

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "CATE_ID", referencedColumnName = "CATE_ID")
    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    public Set<ProductDataEntity> getData() {
        return data;
    }

    public void setData(Set<ProductDataEntity> data) {
        this.data = data;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public ProductInfo toInfo() {
        ProductInfo info = new ProductInfo();
        cloneAttributeTo(info);
        if (this.getCategory() != null) {
            info.setCateId(this.getCategory().getId());
            info.setCateName(this.getCategory().getName());
        }
        if (this.getData() != null && this.getData().size() > 0) {
            this.getData().forEach(a -> {
                info.getProductData().put(a.getAttribute().getId().toString(), a.getAttrValue());
            });
        }
        info.setBasePrice(new Amount(this.currencyCode, this.basePrice));
        info.setRecommend(this.recommend);
        return info;
    }

    public ProductEntity parse(ProductInfo info) {
        info.cloneAttributeTo(this);
        if (info.getBasePrice() != null) {
            this.setBasePrice(info.getBasePrice().getValue());
            if (info.getBasePrice().getCurrencyCode() != null)
                this.setCurrencyCode(info.getBasePrice().getCurrencyCode());
            else
                this.setCurrencyCode("RMB");
        }
        this.setRecommend(info.isRecommend());
        return this;
    }
}
