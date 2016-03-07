package com.breezee.sodexo.entity;

import com.breezee.sodexo.api.domain.VisitCountInfo;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Silence on 2016/3/7.
 */
@Entity
@Table(name = "sdx_tf_visit_count")
public class VisitCountEntity extends VisitCountInfo {

    @Id
    @Column(name = "vtc_code", unique = true, nullable = false)
    public String getCode() {
        return code;
    }

    @Column(name = "page_id", nullable = false)
    public String getPageId() {
        return pageId;
    }

    @Column(name = "page_name",nullable = false)
    public String getPageName() {
        return pageName;
    }

    @Column(name = "reader",nullable = false)
    public String getReader() {
        return reader;
    }

    @Column(name = "reader_type",nullable = false)
    public String getReaderType() {
        return readerType;
    }

    @Column(name = "read_count",nullable = false)
    public int getReadCount() {
        return readCount;
    }

    public VisitCountEntity parse(VisitCountInfo info){
        BeanUtils.copyProperties(info,this);
        return this;
    }

}
