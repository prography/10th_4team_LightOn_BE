package com.prography.lighton.common.exception.base;

import com.prography.lighton.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class NotAuthorizedException extends RuntimeException {

    private static final String MESSAGE = "해당 리소스에 대한 접근 권한이 없습니다..";

    public NotAuthorizedException(final String message) {
        super(message);
    }

    public NotAuthorizedException() {
        this(MESSAGE);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.FORBIDDEN, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.FORBIDDEN;
    }
}
