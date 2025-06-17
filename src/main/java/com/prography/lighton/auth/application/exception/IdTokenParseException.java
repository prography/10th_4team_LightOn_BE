package com.prography.lighton.auth.application.exception;

import com.prography.lighton.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class IdTokenParseException extends RuntimeException {

    private final static String DEFAULT_MESSAGE = "ID 토큰 파싱에 실패했습니다.";

    public IdTokenParseException(final String message) {
        super(message);
    }

    public IdTokenParseException() {
        this(DEFAULT_MESSAGE);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

}
