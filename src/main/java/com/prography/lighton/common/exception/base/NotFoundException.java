package com.prography.lighton.common.exception.base;

import com.prography.lighton.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class NotFoundException extends RuntimeException {

    private static final String MESSAGE = "존재하지 않는 리소스입니다.";

    public NotFoundException(final String message) {
        super(message);
    }

    public NotFoundException() {
        this(MESSAGE);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.NOT_FOUND, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.NOT_FOUND;
    }

}
