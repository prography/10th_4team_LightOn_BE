package com.prography.lighton.performance.common.domain.exception;

import com.prography.lighton.common.exception.base.DuplicateException;

public class DuplicatePerformanceException extends DuplicateException {

    public DuplicatePerformanceException(String message) {
        super(message);
    }
}
