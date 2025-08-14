package com.prography.lighton.performance.common.domain.exception;

public class InvalidSeatCountException extends InvalidPerformanceException {

    private static final String MESSAGE = "이미 예약된 좌석 수 미만으로 전체 좌석 수를 수정할 수 없습니다.";

    public InvalidSeatCountException() {
        super(MESSAGE);
    }

    public InvalidSeatCountException(String message) {
        super(message);
    }
}
