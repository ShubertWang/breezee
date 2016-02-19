/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.pcm.entity;

import com.breezee.common.BaseInfo;

import javax.persistence.*;

/**
 * Created by Silence on 2016/2/7.
 */
@Entity
@Table(name = "PCM_TF_CATE_ATTR")
@NamedQuery(name = "CateAttrEntity.findAll", query = "SELECT m FROM CateAttrEntity m")
public class CateAttrEntity extends BaseInfo {

    /**
     * 所属模型
     */
    private CategoryEntity category;

    /**
     * 属性ID
     */
    private AttributeEntity attribute;

    /**
     * 可以被继承
     */
    private boolean inheritable = true;

    /**
     * 默认值
     */
    private String defaultValue = null;

    /**
     * 允许为空
     */
    private boolean nullable = false;

    /**
     * 是否显示
     */
    private boolean display = true;

    /**
     * 排序，如果小于0，则使用define的排序
     */
    private int orderNo = -1;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REL_ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public String getName() {
        return name == null ? getAttribute().getName() : name;
    }

    @Transient
    public String getCode() {
        return code;
    }

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "CATE_ID", referencedColumnName = "CATE_ID")
    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "ATTR_ID", referencedColumnName = "ATTR_ID")
    public AttributeEntity getAttribute() {
        return attribute == null ? new AttributeEntity() : attribute;
    }

    public void setAttribute(AttributeEntity attribute) {
        this.attribute = attribute;
    }

    public boolean isInheritable() {
        return inheritable;
    }

    public void setInheritable(boolean inheritable) {
        this.inheritable = inheritable;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public int getOrderNo() {
        return orderNo < 0 ? getAttribute().getOrderNo() : orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }
}
