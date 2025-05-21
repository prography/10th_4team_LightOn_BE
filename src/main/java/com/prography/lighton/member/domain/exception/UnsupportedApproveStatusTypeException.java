package com.prography.lighton.member.domain.exception;

import com.prography.lighton.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

// TODO 이 부분은 소셜 로그인 PR 머지 후, ExceptionHandler에 추가할 예정입니다.
// (거기도 비슷한 예외 존재해서 공통 부모 클래스 만드려고 합니당)
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
