package com.prices.exception.error;

import java.io.Serializable;

public interface ErrorCode extends Serializable {
    int getHttpStatusCode();

    int getErrorCode();
}
