package com.prography.lighton.member.exception;

import org.springframework.http.HttpStatus;

public class NoSuchMemberException extends RuntimeException {

	private final HttpStatus httpStatus;

	public NoSuchMemberException(final String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}

	public NoSuchMemberException(HttpStatus httpStatus) {
		this("존재하지 않는 회원입니다.", httpStatus);
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
