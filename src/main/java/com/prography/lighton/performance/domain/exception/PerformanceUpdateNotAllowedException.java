package com.prography.lighton.performance.domain.exception;

import com.prography.lighton.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class PerformanceUpdateNotAllowedException extends RuntimeException {
    private static final String message = "공연 시작일 3일 전까지만 수정이 가능합니다.";

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.CONFLICT, message);
    }

    public HttpStatus status() {
        return HttpStatus.CONFLICT;
    }
}
