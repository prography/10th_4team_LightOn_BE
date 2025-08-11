package com.prography.lighton.performance.common.domain.exception;

public class InvalidScheduleException extends InvalidPerformanceException {

    private static final String MESSAGE = "유효하지 않은 날짜/시간입니다.";

    public InvalidScheduleException() {
        super(MESSAGE);
    }

    public InvalidScheduleException(String message) {
        super(message);
    }
}
