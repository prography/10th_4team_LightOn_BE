package com.prography.lighton.member.exception;

import org.springframework.http.HttpStatus;

import com.prography.lighton.common.exception.base.NotFoundException;
import com.prography.lighton.common.utils.ApiUtils;

public class NoSuchMemberException extends NotFoundException {

	private static final String MESSAGE = "존재하지 않는 회원입니다.";

	public NoSuchMemberException () {
		super (MESSAGE);
	}

	public NoSuchMemberException (String message) {
		super (message);
	}

}
