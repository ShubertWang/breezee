package com.breezee.sodexo.entity;

import com.breezee.sodexo.api.domain.OtherRequestInfo;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Silence on 2016/3/5.
 */
@Entity
@Table(name = "SDX_TF_REQUEST")
public class OtherRequestEntity extends OtherRequestInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REQ_ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "REQ_NAME", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    @Column(name = "REQ_CODE", unique = true, updatable = false, nullable = false, length = 64)
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

    public String getUpdator() {
        return updator;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public String getUserName() {
        return userName;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public OtherRequestInfo toInfo(){
        OtherRequestInfo info = new OtherRequestInfo();
        cloneAttributeTo(info);
        return info;
    }

    public OtherRequestEntity parse(OtherRequestInfo info){
        info.cloneAttributeTo(this);
        return this;
    }
}
