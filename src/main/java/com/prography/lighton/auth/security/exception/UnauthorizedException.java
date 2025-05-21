package com.prography.lighton.auth.security.exception;

import com.prography.lighton.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends RuntimeException {
    private static final String MESSAGE = "인증되지 않은 사용자입니다.";

    public UnauthorizedException(final String message) {
        super(message);
    }

    public UnauthorizedException() {
        this(MESSAGE);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.UNAUTHORIZED, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.UNAUTHORIZED;
    }
}
