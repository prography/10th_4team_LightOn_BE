package com.prography.lighton.auth.application.exception;

import com.prography.lighton.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class UnsupportedSocialLoginTypeException extends RuntimeException {

    private static final String MESSAGE = "지원하지 않는 소셜 로그인 타입입니다.";

    public UnsupportedSocialLoginTypeException(final String message) {
        super(message);
    }

    public UnsupportedSocialLoginTypeException() {
        this(MESSAGE);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.BAD_REQUEST, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }
}
