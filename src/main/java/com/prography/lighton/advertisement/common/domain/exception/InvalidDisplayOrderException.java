package com.prography.lighton.advertisement.common.domain.exception;

import com.prography.lighton.common.exception.base.InvalidException;

public class InvalidDisplayOrderException extends InvalidException {

    private static final String DEFAULT_MESSAGE = "광고의 표시 순서는 0 이상이어야 합니다.";

    public InvalidDisplayOrderException() {
        super(DEFAULT_MESSAGE);
    }
}
