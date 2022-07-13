package com.prices.exception.error.impl;

import com.prices.exception.error.ErrorCode;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum ErrorCodeImpl implements ErrorCode {
    GENERAL(200, HttpStatus.BAD_REQUEST.value()),
    INVALID_ARGUMENT(201, HttpStatus.BAD_REQUEST.value()),
    BAD_REQUEST(400, HttpStatus.BAD_REQUEST.value()),
    NOT_FOUND(404, HttpStatus.NOT_FOUND.value()),
    FORBIDDEN(403, HttpStatus.FORBIDDEN.value()),
    INTERNAL_SERVER_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR.value());

    private final int errorCode;
    private final int httpStatusCode;

    @Override
    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    @Override
    public int getErrorCode() {
        return errorCode;
    }
}
