package com.prography.lighton.performance.domain.entity.exception;

import com.prography.lighton.common.exception.base.InvalidException;

public class InvalidScheduleException extends InvalidException {

    private static final String MESSAGE = "유효하지 않은 날짜/시간입니다.";

    public InvalidScheduleException() {
        super(MESSAGE);
    }

    public InvalidScheduleException(String message) {
        super(message);
    }
}
