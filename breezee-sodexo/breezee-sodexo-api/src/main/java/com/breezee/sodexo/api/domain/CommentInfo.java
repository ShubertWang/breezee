package com.breezee.sodexo.api.domain;

import com.breezee.common.BaseInfo;

/**
 * 对象的评价领域对象
 * Created by Silence on 2016/3/3.
 */
public class CommentInfo extends BaseInfo{

    protected String userId;

    protected String userType;

    protected String objectType;

    protected String objectId;

    protected Integer value;

    /**
     * 操作类型：评价，阅读，订购等
     */
    protected String operType;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }
}
