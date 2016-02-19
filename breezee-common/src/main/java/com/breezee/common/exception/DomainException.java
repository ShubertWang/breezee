/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.common.exception;

/**
 * 领域对象构建出现异常
 * Created by Silence on 2016/2/11.
 */
public class DomainException extends BreezeeSystemException {
    public DomainException(String message) {
        super(message);
    }

    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
