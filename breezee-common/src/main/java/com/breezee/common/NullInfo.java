/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.common;

/**
 * 空对象的界面信息
 * Created by Silence on 2016/2/9.
 */
public class NullInfo {


    public static <T extends BaseInfo> T build(Class<T> cla){
        T t = null;
        try {
            t = cla.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        t.setId(-1L);
        return t;
    }

    public static <T extends BaseInfo> T build(T t){
        t.setId(-1L);
        return t;
    }
}
