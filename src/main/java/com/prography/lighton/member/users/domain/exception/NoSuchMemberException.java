package com.prography.lighton.member.users.domain.exception;

import com.prography.lighton.common.exception.base.NotFoundException;

public class NoSuchMemberException extends NotFoundException {

    private static final String MESSAGE = "존재하지 않는 회원입니다.";

    public NoSuchMemberException() {
        super(MESSAGE);
    }

    public NoSuchMemberException(String message) {
        super(message);
    }

}
