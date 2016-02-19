/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sysmgr.entity;

import com.breezee.common.BaseInfo;
import com.breezee.sysmgr.api.domain.DictInfo;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Silence on 2016/2/10.
 */
@Entity
@Table(name = "SYM_TF_DICT")
public class DictEntity extends BaseInfo {

    protected Set<DictDetailEntity> dictDetails;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DICT_ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "DICT_NAME", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    @Column(name = "DICT_CODE", unique = true, updatable = false, nullable = false, length = 64)
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

    @OneToMany(mappedBy = "dict", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, orphanRemoval = true)
    @OrderBy(value = "orderNo ASC")
    public Set<DictDetailEntity> getDictDetails() {
        return dictDetails;
    }

    public void setDictDetails(Set<DictDetailEntity> dictDetails) {
        this.dictDetails = dictDetails;
    }

    public void addDictDetail(DictDetailEntity dictDetail) {
        dictDetail.setDict(this);
        this.dictDetails.add(dictDetail);
    }

    /**
     * 渲染为info对象
     *
     * @return
     */
    public DictInfo toInfo() {
        DictInfo info = new DictInfo();
        cloneAttribute(info);
        if (this.getDictDetails() != null && this.getDictDetails().size() > 0) {
            this.getDictDetails().forEach(a -> {
                info.getDetailInfos().add(a.toInfo());
            });
        }
        return info;
    }

    public DictEntity parse(DictInfo info) {
        info.cloneAttribute(this);
        if (info.getDetailInfos().size() > 0) {
            if (this.dictDetails == null)
                this.dictDetails = new HashSet<>();
            info.getDetailInfos().forEach(a -> {
                this.addDictDetail(new DictDetailEntity().parse(a));
            });
        }
        return this;
    }
}
