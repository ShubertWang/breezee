/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.common.exception;

/**
 * Created by Silence on 2016/2/11.
 */
public class BreezeeSystemException extends RuntimeException {

    public BreezeeSystemException(String message) {
        super(message);
    }

    public BreezeeSystemException(String message, Throwable cause) {
        super(message, cause);
    }
}
