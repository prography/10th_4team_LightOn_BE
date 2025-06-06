package com.prography.lighton.announcement.common.application.exception;

import com.prography.lighton.common.exception.base.NotFoundException;

public class NoSuchAnnouncementException extends NotFoundException {

    private static final String MESSAGE = "존재하지 않는 공지사항입니다.";

    public NoSuchAnnouncementException() {
        super(MESSAGE);
    }

    public NoSuchAnnouncementException(String message) {
        super(message);
    }
}
