package com.breezee.sodexo.entity;

import com.breezee.common.BaseInfo;
import com.breezee.sodexo.api.domain.ModelInfo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Silence on 2016/2/27.
 */
@Entity
@Table(name = "SDX_TD_MODEL")
public class ModelEntity extends BaseInfo {

    private ModelEntity parent;
    private Set<ArticleEntity> articles;
    private Set<ModelEntity> children;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MODEL_ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "MODEL_NAME", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    @Column(name = "MODEL_CODE", unique = true, updatable = false, nullable = false, length = 64)
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

    @OneToOne
    @JoinColumn(name = "PARENT_ID", referencedColumnName = "MODEL_ID")
    public ModelEntity getParent() {
        return parent;
    }

    public void setParent(ModelEntity parent) {
        this.parent = parent;
    }

    @OneToMany(mappedBy = "parent", cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    public Set<ModelEntity> getChildren() {
        return children;
    }

    public void setChildren(Set<ModelEntity> children) {
        this.children = children;
    }

    @OneToMany(mappedBy = "model", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<ArticleEntity> getArticles() {
        return articles;
    }

    public void setArticles(Set<ArticleEntity> articles) {
        this.articles = articles;
    }

    public void addAccount(ArticleEntity accountEntity){
        if(accountEntity==null)
            return;
        if(this.getArticles()==null)
            this.setArticles(new HashSet<>());
        this.getArticles().add(accountEntity);
    }

    public ModelInfo toInfo(boolean loadChild){
        ModelInfo info = new ModelInfo();
        cloneAttribute(info);
        if(this.getChildren()!=null && this.getChildren().size()>0){
            if(loadChild) {
                info.setChildren(new ArrayList<>());
                this.getChildren().forEach(a -> {
                    info.getChildren().add(a.toInfo(false));
                });
            }
            info.setLeaf(false);
        }
        if(this.getParent()!=null){
            ModelInfo pInfo = new ModelInfo();
            this.getParent().cloneAttribute(pInfo);
            info.setParent(pInfo);
        }
        return info;
    }

    public ModelEntity parse(ModelInfo info){
        info.cloneAttribute(this);
        return this;
    }
}
