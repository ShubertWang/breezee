/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sysmgr.api.domain;

import com.breezee.common.BaseInfo;

/**
 * Created by Silence on 2016/2/12.
 */
public class MediaInfo extends BaseInfo {

    /**
     * 文件原名
     */
    protected String original;

    protected String filePath;

    /**
     * Media的类型，例如图片，附件，安装包等
     */
    protected String type;

    protected String bizId;

    /**
     * MD5校验码
     */
    protected String md5summs;

    protected Double fileSize;

    /**
     * 文件扩展名
     */
    protected String extension;

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getMd5summs() {
        return md5summs;
    }

    public void setMd5summs(String md5summs) {
        this.md5summs = md5summs;
    }

    public Double getFileSize() {
        return fileSize;
    }

    public void setFileSize(Double fileSize) {
        this.fileSize = fileSize;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
