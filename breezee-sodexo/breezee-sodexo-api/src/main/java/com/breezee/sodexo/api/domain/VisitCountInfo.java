package com.breezee.sodexo.api.domain;

import com.breezee.common.BaseInfo;

/**
 * Created by Silence on 2016/3/7.
 */
public class VisitCountInfo extends BaseInfo {

    /**
     * 页面标识，取ID
     */
    protected String pageId;

    /**
     * 页面名称，取标题
     */
    protected String pageName;

    /**
     * 阅读者标识
     */
    protected String reader;

    /**
     * 阅读者读此文章时候的类型
     */
    protected String readerType;

    protected int readCount;

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getReader() {
        return reader;
    }

    public void setReader(String reader) {
        this.reader = reader;
    }

    public String getReaderType() {
        return readerType;
    }

    public void setReaderType(String readerType) {
        this.readerType = readerType;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }
}
