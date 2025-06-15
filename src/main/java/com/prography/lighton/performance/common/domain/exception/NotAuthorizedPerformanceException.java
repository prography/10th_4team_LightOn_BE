package com.prography.lighton.performance.common.domain.exception;

import com.prography.lighton.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class NotAuthorizedPerformanceException extends RuntimeException {
    private final String message = "이 공연에 대한 권한이 없습니다.";

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.FORBIDDEN, message);
    }

    public HttpStatus status() {
        return HttpStatus.FORBIDDEN;
    }
}
