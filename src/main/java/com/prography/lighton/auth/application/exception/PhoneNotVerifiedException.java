package com.prography.lighton.auth.application.exception;

import com.prography.lighton.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class PhoneNotVerifiedException extends RuntimeException {
    private final String message = "아직 휴대폰 인증이 완료되지 않았습니다.";

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.BAD_REQUEST, message);
    }

    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }
}
