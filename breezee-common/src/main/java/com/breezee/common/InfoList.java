/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.common;

import com.breezee.common.util.Callback;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Silence on 2016/2/12.
 */
public class InfoList<E> extends ArrayList {

    public <T> InfoList(Collection<T> c, Callback<T,E> callback) {
        super(c.size());
        c.forEach(a->{
            this.add(callback.call(a,null));
        });
    }
}
