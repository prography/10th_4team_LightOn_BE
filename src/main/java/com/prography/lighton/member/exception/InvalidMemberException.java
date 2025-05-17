package com.prography.lighton.member.exception;

import org.springframework.http.HttpStatus;

public class InvalidMemberException extends RuntimeException{

	private final HttpStatus httpStatus;

	public InvalidMemberException(final String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}

	public InvalidMemberException(HttpStatus httpStatus) {
		this("잘못된 회원의 정보입니다.", httpStatus);
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
