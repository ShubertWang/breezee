/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.pcm.entity;

import com.breezee.common.BaseInfo;
import com.breezee.common.util.ContextUtil;
import com.breezee.pcm.api.domain.CateAttrInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.io.IOException;
import java.util.Map;

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

    public CateAttrEntity() {
    }

    public CateAttrEntity(CategoryEntity category, AttributeEntity attribute) {
        this.category = category;
        this.attribute = attribute;
    }

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

    public CateAttrInfo toInfo(){
        CateAttrInfo cateAttrInfo = new CateAttrInfo();
        cloneAttribute(cateAttrInfo);
        if(this.getAttribute()!=null) {
            cateAttrInfo.setAttrId(this.getAttribute().getId());
            cateAttrInfo.setCode(this.getAttribute().getCode());
            cateAttrInfo.setAttrType(this.getAttribute().getFieldType());
            cateAttrInfo.setUnitCode(this.getAttribute().getUnitCode());
            if(cateAttrInfo.getAttrType().equals("dict")) {
                ObjectMapper objectMapper = ContextUtil.getBean("objectMapper",ObjectMapper.class);
                try {
                    Map<String,Object> m = objectMapper.readValue(this.getAttribute().getArguments(),Map.class);
                    cateAttrInfo.setEnumCode(m.get("enumCode")+"");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        cateAttrInfo.setDefaultValue(this.getDefaultValue());
        cateAttrInfo.setDisplay(this.isDisplay());
        cateAttrInfo.setInheritable(this.isInheritable());
        cateAttrInfo.setNullable(this.isNullable());
        cateAttrInfo.setOrderNo(this.getOrderNo());
        if(this.getCategory()!=null)
            cateAttrInfo.setSourceCateId(this.getCategory().getId());
        return cateAttrInfo;
    }

    public CateAttrEntity parse(CateAttrInfo info){
        info.cloneAttribute(this);
        this.setId(null);
        this.setDefaultValue(info.getDefaultValue());
        this.setDisplay(info.isDisplay());
        this.setInheritable(info.isInheritable());
        this.setNullable(info.isNullable());
        this.setOrderNo(info.getOrderNo());
        return this;
    }
}
