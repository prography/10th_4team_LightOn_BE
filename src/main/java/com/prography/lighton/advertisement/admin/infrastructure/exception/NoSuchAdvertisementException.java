package com.prography.lighton.advertisement.admin.infrastructure.exception;

import com.prography.lighton.common.exception.base.NotFoundException;

public class NoSuchAdvertisementException extends NotFoundException {

    private static final String DEFAULT_MESSAGE = "존재하지 않는 광고 혹은 이미지 입니다.";

    public NoSuchAdvertisementException(String message) {
        super(message);
    }

    public NoSuchAdvertisementException() {
        super(DEFAULT_MESSAGE);
    }
}
