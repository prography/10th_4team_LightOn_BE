package com.prography.lighton.performance.admin.application.exception;

import com.prography.lighton.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class PerformanceAlreadyProcessedException extends RuntimeException {

    private final String message = "해당 공연은 현재 승인 및 반려 할 수 없는 상태입니다.";

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.BAD_REQUEST, message);
    }

    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }

}
