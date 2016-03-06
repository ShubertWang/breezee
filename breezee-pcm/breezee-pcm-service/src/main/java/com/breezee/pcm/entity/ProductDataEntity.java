/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.pcm.entity;

import com.breezee.common.BaseInfo;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Silence on 2016/2/7.
 */
@Entity
@Table(name = "PCM_TF_PRODUCT_DATA")
@NamedQuery(name = "ProductEntity.findAll", query = "SELECT m FROM ProductEntity m")
public class ProductDataEntity extends BaseInfo {

    private Long dataId;

    private String attrValue;

    private AttributeEntity attribute;

    private ProductEntity product;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="DATA_ID", unique=true, nullable=false)
    public Long getDataId() {
        return dataId;
    }

    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }

    @Lob
    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }


    @ManyToOne(cascade = CascadeType.REFRESH, fetch=FetchType.LAZY)
    @JoinColumn(name="ATTR_ID", referencedColumnName = "ATTR_ID", nullable=false)
    public AttributeEntity getAttribute() {
        return attribute;
    }

    public void setAttribute(AttributeEntity attribute) {
        this.attribute = attribute;
    }

    @ManyToOne(cascade = CascadeType.REFRESH, fetch=FetchType.LAZY)
    @JoinColumn(name="PRODUCT_ID", referencedColumnName = "PRODUCT_ID", nullable=false)
    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }
}
