package com.prography.lighton.performance.common.domain.exception;

public class NoSuchPerformanceException extends NotFoundPerformanceException {

    private static final String MESSAGE = "존재하지 않는 공연입니다.";

    public NoSuchPerformanceException() {
        super(MESSAGE);
    }

    public NoSuchPerformanceException(String message) {
        super(message);
    }

}
