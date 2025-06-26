package com.prography.lighton.performance.common.domain.exception;

import com.prography.lighton.common.exception.base.NotFoundException;

public class NoSuchPerformanceRequestException extends NotFoundException {

    private static final String MESSAGE = "존재하지 않는 공연 신청입니다.";

    public NoSuchPerformanceRequestException() {
        super(MESSAGE);
    }
}
