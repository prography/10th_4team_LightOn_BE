package com.prography.lighton.artist.admin.domain.exception;

import com.prography.lighton.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class InvalidApproveStatusTransitionException extends RuntimeException {
    private static final String MESSAGE = "현재 상태에서는 해당 상태로 변경할 수 없습니다.";

    public InvalidApproveStatusTransitionException() {
        super(MESSAGE);
    }

    public InvalidApproveStatusTransitionException(String message) {
        super(message);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.BAD_REQUEST, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }
}