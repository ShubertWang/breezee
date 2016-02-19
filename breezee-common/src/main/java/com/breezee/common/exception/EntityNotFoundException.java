/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.common.exception;

/**
 * 实体未找到的异常
 * Created by Silence on 2016/2/11.
 */
public class EntityNotFoundException extends PersistenceException {
    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
