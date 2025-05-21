package com.prography.lighton.member.domain.exception;

import com.prography.lighton.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class UnsupportedApproveStatusTypeException extends RuntimeException {

    private static final String MESSAGE = "지원하지 않는 신청 상태입니다.";

    public UnsupportedApproveStatusTypeException(final String message) {
        super(message);
    }

    public UnsupportedApproveStatusTypeException() {
        this(MESSAGE);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.BAD_REQUEST, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }
}
