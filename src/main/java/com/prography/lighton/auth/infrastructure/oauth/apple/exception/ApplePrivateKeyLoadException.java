package com.prography.lighton.auth.infrastructure.oauth.apple.exception;

import com.prography.lighton.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class ApplePrivateKeyLoadException extends RuntimeException {

    private final static String DEFAULT_MESSAGE = "Apple 개인 키 로드에 실패했습니다.";

    public ApplePrivateKeyLoadException(final String message) {
        super(message);
    }

    public ApplePrivateKeyLoadException() {
        this(DEFAULT_MESSAGE);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
