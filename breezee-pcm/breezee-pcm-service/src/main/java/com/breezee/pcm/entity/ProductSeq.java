package com.breezee.pcm.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Silence on 2016/2/27.
 */
@Entity
@Table(name = "PCM_TF_PRODUCT_SEQ")
public class ProductSeq implements Serializable {

    private Long id;

    private String value;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ_ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

