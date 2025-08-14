package com.prography.lighton.performance.common.domain.exception;

import com.prography.lighton.common.exception.base.NotAuthorizedException;

public class NotAuthorizedPerformanceException extends NotAuthorizedException {

    public NotAuthorizedPerformanceException(String message) {
        super(message);
    }
}
