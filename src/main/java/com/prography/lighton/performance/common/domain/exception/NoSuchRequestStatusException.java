package com.prography.lighton.performance.common.domain.exception;

public class NoSuchRequestStatusException extends NotFoundPerformanceException {
    private static final String MESSAGE = "존재하지 않는 요청 상태입니다.";

    public NoSuchRequestStatusException() {
        super(MESSAGE);
    }

}
