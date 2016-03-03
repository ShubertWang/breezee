package com.breezee.sodexo.entity;

import com.breezee.sodexo.api.domain.CommentInfo;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by Silence on 2016/3/3.
 */
public class CommentEntity extends CommentInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FDL_ID", unique = true, nullable = false)
    public Long getId() {
        return id;
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

    public int getStatus() {
        return status;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserType() {
        return userType;
    }

    public String getObjectType() {
        return objectType;
    }

    public String getObjectId() {
        return objectId;
    }

    public Integer getValue() {
        return value;
    }

    public String getOperType() {
        return operType;
    }

    public CommentInfo toInfo(){
        CommentInfo info = new CommentInfo();
        BeanUtils.copyProperties(this,info);
        return info;
    }

    public CommentEntity parse(CommentInfo info){
        BeanUtils.copyProperties(info,this);
        return this;
    }
}
