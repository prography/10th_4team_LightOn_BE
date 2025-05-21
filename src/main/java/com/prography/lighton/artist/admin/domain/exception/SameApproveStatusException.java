package com.prography.lighton.artist.admin.domain.exception;

import com.prography.lighton.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class SameApproveStatusException extends RuntimeException {
    private static final String MESSAGE = "이미 승인된 아티스트입니다.";

    public SameApproveStatusException() {
        super(MESSAGE);
    }

    public SameApproveStatusException(String message) {
        super(message);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.BAD_REQUEST, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }

}

