/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.common.exception;

/**
 * 远程调用的异常
 * Created by Silence on 2016/2/11.
 */
public class RemoteRequestException extends BreezeeSystemException {

    public RemoteRequestException(String message) {
        super(message);
    }

    public RemoteRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
