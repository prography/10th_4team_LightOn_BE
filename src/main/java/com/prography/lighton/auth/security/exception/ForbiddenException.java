package com.prography.lighton.auth.security.exception;

import com.prography.lighton.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class ForbiddenException extends RuntimeException {

    private static final String MESSAGE = "접근이 금지된 사용자입니다.";

    public ForbiddenException(final String message) {
        super(message);
    }

    public ForbiddenException() {
        this(MESSAGE);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.UNAUTHORIZED, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.UNAUTHORIZED;
    }
}
