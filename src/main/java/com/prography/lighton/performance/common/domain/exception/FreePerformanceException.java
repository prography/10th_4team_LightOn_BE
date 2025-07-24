package com.prography.lighton.performance.common.domain.exception;

import com.prography.lighton.common.exception.base.InvalidException;

public class FreePerformanceException extends InvalidException {

    private static final String MESSAGE = "해당 공연은 무료 공연입니다. 결제 정보가 존재하지 않습니다.";

    public FreePerformanceException() {
        super(MESSAGE);
    }

    public FreePerformanceException(String message) {
        super(message);
    }
}
