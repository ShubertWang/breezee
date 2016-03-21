package com.breezee.common;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 领域对象的基本信息
 * 将被系统内的所有领域对象所继承
 * Created by Silence on 2016/2/6.
 */
public class BaseInfo implements Serializable {

    public static final int STATUS_ON = Consts.STATUS_ON;
    public static final int STATUS_OFF = Consts.STATUS_OFF;

    protected Long id;

    protected String code;

    protected String name;

    protected int status = STATUS_ON;

    protected String remark;

    protected String creator;

    protected Date createTime;

    protected String updator;

    protected Date updateTime;

    protected String tenantId;

    protected String locale;

    protected Map<String,Object> properties;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Map<String, Object> getProperties() {
        if(properties==null)
            this.properties = new HashMap<>();
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    /**
     * 复制对象
     * 我们不用BeanUtil的方式实现，而是自己来实现
     * 一方面减少包的依赖，另外也是提高效率
     */
    @Override
    public BaseInfo clone() {
        BaseInfo bi = new BaseInfo();
        cloneAttributeTo(bi);
        return bi;
    }

    /**
     * 将当前值赋予给指定的对象上
     * @param info
     */
    public void cloneAttributeTo(BaseInfo info) {
        if(this.code!=null) info.setCode(this.code);
        if(this.id!=null) info.setId(this.id);
        if(this.name!=null) info.setName(this.name);
        if(this.remark!=null) info.setRemark(this.remark);
        info.setStatus(this.status);
        if(this.tenantId!=null) info.setTenantId(this.tenantId);
        if(this.creator!=null) info.setCreator(this.creator);
        if(this.updator!=null) info.setUpdator(this.updator);
        if(this.createTime!=null) info.setCreateTime(this.createTime);
        if(this.updateTime!=null) info.setUpdateTime(this.updateTime);
    }
}
