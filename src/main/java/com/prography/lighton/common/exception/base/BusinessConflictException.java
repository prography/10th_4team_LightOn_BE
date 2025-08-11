package com.prography.lighton.common.exception.base;

import com.prography.lighton.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class BusinessConflictException extends RuntimeException {

    private static final String MESSAGE = "처리 불가한 요청입니다.";

    public BusinessConflictException(final String message) {
        super(message);
    }

    public BusinessConflictException() {
        this(MESSAGE);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.CONFLICT, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.CONFLICT;
    }
}
