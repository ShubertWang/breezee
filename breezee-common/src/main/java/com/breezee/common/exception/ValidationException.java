/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.common.exception;

/**
 * 校验出现异常
 * Created by Silence on 2016/2/11.
 */
public class ValidationException extends DomainException {

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
