package com.prography.lighton.member.exception;

import org.springframework.http.HttpStatus;

import com.prography.lighton.common.exception.base.InvalidException;
import com.prography.lighton.common.utils.ApiUtils;

public class InvalidMemberException extends InvalidException {

	private static final String MESSAGE = "잘못된 회원의 정보입니다." ;

	public InvalidMemberException () {
		super (MESSAGE);
	}

	public InvalidMemberException (String message) {
		super (message);
	}
}
