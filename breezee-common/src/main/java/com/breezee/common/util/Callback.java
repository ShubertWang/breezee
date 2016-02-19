/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.common.util;

/**
 * Call Back
 * Created by Silence on 2016/1/23.
 */
@FunctionalInterface
public interface Callback<P,R> {

    /**
     *
     * @param p
     * @param r
     * @return
     */
    R call(P p, R r);
}
