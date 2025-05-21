package com.prography.lighton.auth.application.exception;

import com.prography.lighton.common.exception.base.UnsupportedTypeException;

public class UnsupportedSocialLoginTypeException extends UnsupportedTypeException {

    private static final String MESSAGE = "지원하지 않는 소셜 로그인 타입입니다.";

    public UnsupportedSocialLoginTypeException(final String message) {
        super(message);
    }

    public UnsupportedSocialLoginTypeException() {
        this(MESSAGE);
    }

}
