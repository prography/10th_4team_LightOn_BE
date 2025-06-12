package com.prography.lighton.auth.application.exception;

import com.prography.lighton.common.exception.base.InvalidException;

public class ExpiredTokenException extends InvalidException {

    private final static String DEFAULT_MESSAGE = "토큰이 만료되었습니다.";

    public ExpiredTokenException(final String message) {
        super(message);
    }

    public ExpiredTokenException() {
        this(DEFAULT_MESSAGE);
    }
}
