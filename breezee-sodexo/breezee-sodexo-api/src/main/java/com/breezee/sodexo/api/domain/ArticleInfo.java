package com.breezee.sodexo.api.domain;

import com.breezee.common.BaseInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

/**
 * Created by Silence on 2016/2/27.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleInfo extends BaseInfo {

    /**
     * 标题
     */
    protected String title;

    /**
     * 副标题
     */
    protected String subtitle;

    /**
     * content是我们的系统关键字，过滤掉
     */
    @JsonIgnore
    protected String content;

    protected String source;

    /**
     * 新闻语言
     */
    protected String lang;

    /**
     * 营运网点
     */
    protected String site;

    protected Long modelId;

    protected String modelName;

    protected String userName;

    protected Long updateTimeLong;

    protected String uedit;

    protected int orderNo;

    protected Date activeTime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public String getUedit() {
        return content;
    }

    public void setUedit(String uedit) {
        this.content = uedit;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Long getUpdateTimeLong() {
        return this.getUpdateTime()!=null?this.getUpdateTime().getTime():new Date().getTime();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public Date getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
    }
}
