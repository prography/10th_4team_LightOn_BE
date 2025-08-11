package com.prography.lighton.performance.common.domain.exception;

import com.prography.lighton.common.exception.base.BusinessConflictException;

public class PerformanceUpdateNotAllowedException extends BusinessConflictException {
    private static final String message = "공연 시작일 3일 전까지만 수정이 가능합니다.";

    public PerformanceUpdateNotAllowedException() {
        super(message);
    }
}
