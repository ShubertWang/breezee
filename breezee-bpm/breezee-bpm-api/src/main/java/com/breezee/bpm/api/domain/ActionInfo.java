package com.breezee.bpm.api.domain;

import java.io.Serializable;

/**
 * Created by Silence on 2016/3/1.
 */
public class ActionInfo implements Serializable {

    protected String code;

    protected String name;

    protected String callback;

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

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }
}
