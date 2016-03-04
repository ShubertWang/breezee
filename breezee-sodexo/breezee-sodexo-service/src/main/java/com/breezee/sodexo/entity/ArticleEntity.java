package com.breezee.sodexo.entity;

import com.breezee.common.BaseInfo;
import com.breezee.sodexo.api.domain.ArticleInfo;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Silence on 2016/2/27.
 */
@Entity
@Table(name = "SDX_TD_ARTICLE")
public class ArticleEntity extends BaseInfo {

    /**
     * 标题
     */
    protected String title;

    /**
     * 副标题
     */
    protected String subtitle;

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

    protected ModelEntity model;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ARTC_ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "ARTC_NAME", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    @Column(name = "ARTC_CODE", nullable = true, length = 64)
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

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "MODEL_ID", referencedColumnName = "MODEL_ID")
    public ModelEntity getModel() {
        return model;
    }

    public void setModel(ModelEntity model) {
        this.model = model;
    }

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

    @Lob
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

    public ArticleInfo toInfo(){
        ArticleInfo info = new ArticleInfo();
        BeanUtils.copyProperties(this,info,new String[]{"model"});
        if(this.getModel()!=null){
            info.setModelId(this.getModel().getId());
            info.setModelName(this.getModel().getName());
        }
        return info;
    }

    public ArticleEntity parse(ArticleInfo info){
        BeanUtils.copyProperties(info,this,new String[]{"cateId"});
        return this;
    }
}
