package com.prography.lighton.common.exception.base;

import com.prography.lighton.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class UnsupportedTypeException extends RuntimeException {

    private static final String MESSAGE = "지원하지 않는 타입입니다.";

    public UnsupportedTypeException(final String message) {
        super(message);
    }

    public UnsupportedTypeException() {
        this(MESSAGE);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.BAD_REQUEST, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }

}
