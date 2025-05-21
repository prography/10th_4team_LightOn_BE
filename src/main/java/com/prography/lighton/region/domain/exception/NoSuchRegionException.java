package com.prography.lighton.region.domain.exception;

import com.prography.lighton.common.exception.base.NotFoundException;

public class NoSuchRegionException extends NotFoundException {

    private static final String MESSAGE = "존재하지 않는 지역입니다.";

    public NoSuchRegionException() {
        super(MESSAGE);
    }

    public NoSuchRegionException(String message) {
        super(message);
    }

}
