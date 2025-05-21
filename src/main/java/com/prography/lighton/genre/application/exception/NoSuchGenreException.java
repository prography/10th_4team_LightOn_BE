package com.prography.lighton.genre.application.exception;

import com.prography.lighton.common.exception.base.NotFoundException;

public class NoSuchGenreException extends NotFoundException {

    private static final String MESSAGE = "존재하지 않는 장르 ID입니다.";

    public NoSuchGenreException() {
        super(MESSAGE);
    }

    public NoSuchGenreException(String message) {
        super(message);
    }

}
