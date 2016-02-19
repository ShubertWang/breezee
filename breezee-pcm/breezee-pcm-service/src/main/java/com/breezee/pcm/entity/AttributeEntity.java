/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.pcm.entity;

import com.breezee.common.BaseInfo;
import com.breezee.pcm.api.domain.AttributeInfo;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Silence on 2016/2/7.
 */
@Entity
@Table(name = "PCM_TD_ATTRIBUTE")
@NamedQuery(name = "AttributeEntity.findAll", query = "SELECT m FROM AttributeEntity m")
public class AttributeEntity extends BaseInfo {

    /**
     * 属性的类型：目前支持String, Number, Integer, DateTime
     */
    protected String fieldType;

    /**
     * 参数
     * 例如枚举值得code，以json格式标识
     */
    protected String arguments;

    /**
     * 单位描述，如：GB
     */
    protected String unitCode;

    /**
     * 排序
     */
    protected int orderNo;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ATTR_ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "ATTR_NAME", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    @Column(name = "ATTR_CODE", unique = true, updatable = false, nullable = false, length = 64)
    public String getCode() {
        return code;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getArguments() {
        return arguments;
    }

    public void setArguments(String arguments) {
        this.arguments = arguments;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public String getRemark() {
        return remark;
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

    public AttributeInfo toInfo(){
        AttributeInfo info = new AttributeInfo();
        cloneAttribute(info);
        info.setArguments(this.getArguments());
        info.setFieldType(this.getFieldType());
        info.setOrderNo(this.getOrderNo());
        info.setUnitCode(this.getUnitCode());
        return info;
    }

    public AttributeEntity parse(AttributeInfo info){
        info.cloneAttribute(this);
        this.setUnitCode(info.getUnitCode());
        this.setOrderNo(info.getOrderNo());
        this.setArguments(info.getArguments());
        this.setFieldType(info.getFieldType());
        return this;
    }
}
