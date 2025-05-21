package com.prography.lighton.artist.admin.domain.exception;

import com.prography.lighton.common.exception.base.UnsupportedTypeException;

// TODO 이 부분은 소셜 로그인 PR 머지 후, ExceptionHandler에 추가할 예정입니다.
// (거기도 비슷한 예외 존재해서 공통 부모 클래스 만드려고 합니당)
public class UnsupportedApproveStatusTypeException extends UnsupportedTypeException {

    private static final String MESSAGE = "지원하지 않는 신청 상태입니다.";

    public UnsupportedApproveStatusTypeException(final String message) {
        super(message);
    }

    public UnsupportedApproveStatusTypeException() {
        this(MESSAGE);
    }

}
