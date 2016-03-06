/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.common;

/**
 * 成功对象的界面信息
 * Created by Silence on 2016/2/9.
 */
public class SuccessInfo {

    public static <T extends BaseInfo> T build(Class<T> cla) {
        T t = null;
        try {
            t = cla.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        t.setId(100L);
        return t;
    }

    public static <T extends BaseInfo> T build(T t) {
        if (t.getId() == null || t.getId() < 0)
            t.setId(100L);
        return t;
    }
}
