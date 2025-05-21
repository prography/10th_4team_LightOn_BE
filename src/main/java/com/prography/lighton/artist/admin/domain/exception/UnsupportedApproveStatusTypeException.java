package com.prography.lighton.artist.admin.domain.exception;

import com.prography.lighton.common.exception.base.UnsupportedTypeException;

public class UnsupportedApproveStatusTypeException extends UnsupportedTypeException {

    private static final String MESSAGE = "지원하지 않는 신청 상태입니다.";

    public UnsupportedApproveStatusTypeException(final String message) {
        super(message);
    }

    public UnsupportedApproveStatusTypeException() {
        this(MESSAGE);
    }

}
