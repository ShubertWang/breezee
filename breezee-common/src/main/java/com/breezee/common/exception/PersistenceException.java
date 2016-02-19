/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.common.exception;

/**
 * 持久化出现异常
 * Created by Silence on 2016/2/11.
 */
public class PersistenceException extends BreezeeSystemException {

    public PersistenceException(String message) {
        super(message);
    }

    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
