package com.prography.lighton.common.exception.base;

import com.prography.lighton.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class DuplicateException extends RuntimeException {

    private static final String MESSAGE = "중복된 요청입니다.";

    public DuplicateException(final String message) {
        super(message);
    }

    public DuplicateException() {
        this(MESSAGE);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.BAD_REQUEST, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }
}
