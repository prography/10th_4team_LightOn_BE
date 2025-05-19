package com.prography.lighton.auth.exception;

import com.prography.lighton.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class MemberProfileIncompleteException extends RuntimeException {

    private static final String MESSAGE = "필수 개인 정보 입력이 완료되지 않았습니다.";

    public MemberProfileIncompleteException(final String message) {
        super(message);
    }

    public MemberProfileIncompleteException() {
        this(MESSAGE);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.FORBIDDEN, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.FORBIDDEN;
    }

}
