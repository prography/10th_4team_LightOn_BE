package com.prography.lighton.advertisement.admin.application.exception;

import com.prography.lighton.common.exception.base.DuplicateException;

public class DuplicateDisplayOrderException extends DuplicateException {

    private static final String DEFAULT_MESSAGE = "이미 존재하는 광고의 디스플레이 순서입니다.";

    public DuplicateDisplayOrderException() {
        super(DEFAULT_MESSAGE);
    }
}
