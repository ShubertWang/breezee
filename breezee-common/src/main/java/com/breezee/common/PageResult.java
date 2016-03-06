/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.common;

import com.breezee.common.util.Callback;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分页的查询结果
 * <p>
 * Created by Silence on 2016/1/22.
 */
public class PageResult<V> implements Serializable {

    @JsonIgnore
    private transient Page<? extends Serializable> page;

    private List<V> content;

    private Long total;

    private transient Map<String,Object> properties;

    public PageResult() {
    }


    /**
     * 我们必须提供回调来实现
     * @param page
     * @param cla
     * @param <E>
     */
    protected  <E> PageResult(Page<E> page, Class<V> cla) {
        this(page, cla, null);
    }

    public <E> PageResult(Page<E> page, Class<V> cla, Callback<E, V> callback) {
        this.page = (Page<? extends Serializable>) page;
        this.total = page.getTotalElements();
        content = new ArrayList<>();
        page.getContent().forEach(a -> {
            if (callback != null) {
                try {
                    content.add(callback.call(a,cla.newInstance()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public List<V> getContent() {
        return content;
    }

    public void setContent(List<V> content) {
        this.content = content;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public String toString() {
        return page.toString();
    }

    @JsonAnyGetter
    public Map<String, Object> getProperties() {
        if(this.properties==null)
            this.properties=new HashMap<>();
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
}
