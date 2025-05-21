package com.prography.lighton.artist.users.application.exception;

import com.prography.lighton.common.exception.base.NotFoundException;

public class NoSuchArtistException extends NotFoundException {

    private static final String MESSAGE = "존재하지 않는 아티스트입니다.";

    public NoSuchArtistException() {
        super(MESSAGE);
    }

    public NoSuchArtistException(String message) {
        super(message);
    }

}
