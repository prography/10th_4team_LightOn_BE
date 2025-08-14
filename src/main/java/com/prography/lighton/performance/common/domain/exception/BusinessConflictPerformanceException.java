package com.prography.lighton.performance.common.domain.exception;

import com.prography.lighton.common.exception.base.BusinessConflictException;

public class BusinessConflictPerformanceException extends BusinessConflictException {

    public BusinessConflictPerformanceException(String message) {
        super(message);
    }
}
