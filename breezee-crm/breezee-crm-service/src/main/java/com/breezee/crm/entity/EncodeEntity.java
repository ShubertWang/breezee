package com.breezee.crm.entity;

import com.breezee.crm.api.domain.EncodeInfo;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Silence on 2016/2/28.
 */
@Entity
@Table(name = "CRM_TF_ENCODE")
public class EncodeEntity extends EncodeInfo {

    public String getSite() {
        return site;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ENC_ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "ENC_NAME", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    @Column(name = "ENC_CODE", unique = true, updatable = false, nullable = false, length = 64)
    public String getCode() {
        return code;
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

    public String getRemark() {
        return remark;
    }

    public EncodeInfo toInfo(){
        EncodeInfo info = new EncodeInfo();
        BeanUtils.copyProperties(this,info);
        return info;
    }

    public EncodeEntity parse(EncodeInfo info){
        BeanUtils.copyProperties(this,info);
        return this;
    }

}
