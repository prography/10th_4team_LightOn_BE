package com.prography.lighton.announcement.common.domain.exception;

import com.prography.lighton.common.exception.base.InvalidException;

public class InvalidAnnouncementException extends InvalidException {

    private static final String MESSAGE = "유효하지 않은 공지사항입니다.";

    public InvalidAnnouncementException(final String message) {
        super(message);
    }

    public InvalidAnnouncementException() {
        this(MESSAGE);
    }
}
