package com.breezee.prj.sodexo.domain;

import com.breezee.common.TreeObject;

import java.util.List;

/**
 * Created by Silence on 2016/2/27.
 */
public class ModelInfo extends TreeObject<ModelInfo> {

    protected String type="leaf";

    /**
     * 营运网点
     */
    private String site;

    private List<ArticleInfo> articles;

    public String getType() {
        return leaf?"leaf":"folder";
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public List<ArticleInfo> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleInfo> articles) {
        this.articles = articles;
    }
}
