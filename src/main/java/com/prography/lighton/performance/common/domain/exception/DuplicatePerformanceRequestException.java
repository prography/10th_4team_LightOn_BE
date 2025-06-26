package com.prography.lighton.performance.common.domain.exception;

import com.prography.lighton.common.exception.base.DuplicateException;

public class DuplicatePerformanceRequestException extends DuplicateException {
    private static final String MESSAGE = "이미 신청한 공연입니다.";

    public DuplicatePerformanceRequestException() {
        super(MESSAGE);
    }


}
