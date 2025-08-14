package com.prography.lighton.performance.common.domain.exception;

import com.prography.lighton.common.exception.base.NotFoundException;

public class NotFoundPerformanceException extends NotFoundException {

    public NotFoundPerformanceException(String message) {
        super(message);
    }
}
