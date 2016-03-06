package com.breezee.sodexo.entity;

import com.breezee.sodexo.api.domain.CommentInfo;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Silence on 2016/3/3.
 */
@Entity
@Table(name = "SDX_TD_COMMENTS")
public class CommentEntity extends CommentInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CMT_ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "CMT_CODE", unique = true, updatable = false, nullable = false, length = 64)
    public String getCode() {
        return code;
    }

    public String getTenantId() {
        return tenantId;
    }

    public String getRemark() {
        return remark;
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

    public String getCommentTime() {
        return commentTime;
    }

    public CommentInfo toInfo(){
        CommentInfo info = new CommentInfo();
        BeanUtils.copyProperties(this,info);
        return info;
    }

    public CommentEntity parse(CommentInfo info){
        BeanUtils.copyProperties(info,this);
        this.setCreateTime(new Date());
        return this;
    }
}
