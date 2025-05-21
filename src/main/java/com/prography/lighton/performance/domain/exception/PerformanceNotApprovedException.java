package com.prography.lighton.performance.domain.exception;

import com.prography.lighton.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class PerformanceNotApprovedException extends RuntimeException {
    private final String message = "승인되지 않은 공연입니다.";

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.CONFLICT, message);
    }

    public HttpStatus status() {
        return HttpStatus.CONFLICT;
    }
}
