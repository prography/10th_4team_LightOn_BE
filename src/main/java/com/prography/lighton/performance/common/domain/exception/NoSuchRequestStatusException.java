package com.prography.lighton.performance.common.domain.exception;

import com.prography.lighton.common.exception.base.NotFoundException;

public class NoSuchRequestStatusException extends NotFoundException {
    private static final String MESSAGE = "존재하지 않는 요청 상태입니다.";

    public NoSuchRequestStatusException() {
        super(MESSAGE);
    }

}
