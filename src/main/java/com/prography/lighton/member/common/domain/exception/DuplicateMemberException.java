package com.prography.lighton.member.common.domain.exception;

import com.prography.lighton.common.exception.base.DuplicateException;

public class DuplicateMemberException extends DuplicateException {

    private static final String MESSAGE = "이미 존재하는 회원입니다.";

    public DuplicateMemberException() {
        super(MESSAGE);
    }

    public DuplicateMemberException(String message) {
        super(message);
    }

}
