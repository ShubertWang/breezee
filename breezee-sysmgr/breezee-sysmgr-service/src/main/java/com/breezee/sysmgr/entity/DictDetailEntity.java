/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sysmgr.entity;

import com.breezee.common.BaseInfo;
import com.breezee.sysmgr.api.domain.DictDetailInfo;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Silence on 2016/2/10.
 */
@Entity
@Table(name = "SYM_TF_DICT_DETAIL")
public class DictDetailEntity extends BaseInfo {

    private Integer orderNo;

    private DictEntity dict;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DETAIL_ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "DETAIL_NAME", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    @Column(name = "DETAIL_CODE", length = 64)
    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
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

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "DICT_ID", nullable = false)
    public DictEntity getDict() {
        return dict;
    }

    public void setDict(DictEntity dict) {
        this.dict = dict;
    }

    public DictDetailInfo toInfo(){
        DictDetailInfo info = new DictDetailInfo();
        cloneAttributeTo(info);
        return info;
    }

    public DictDetailEntity parse(DictDetailInfo info){
        info.cloneAttributeTo(this);
        this.orderNo = info.getOrderNo();
        return this;
    }
}
