package com.prography.lighton.performance.users.application.exception;

import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import org.springframework.http.HttpStatus;

public class BadPerformanceRequestException extends RuntimeException {
    private static final String message = "잘못된 공연 신청입니다.";

    public ApiResult<String> body() {
        return ApiUtils.error(HttpStatus.BAD_REQUEST, message);
    }

    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }
}
