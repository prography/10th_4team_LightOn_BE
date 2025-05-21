package com.prography.lighton.performance.domain.exception;

import com.prography.lighton.common.exception.base.NotFoundException;

public class NoSuchPerformanceException extends NotFoundException {

    private static final String MESSAGE = "존재하지 않는 공연입니다.";

    public NoSuchPerformanceException() {
        super(MESSAGE);
    }

    public NoSuchPerformanceException(String message) {
        super(message);
    }

}
