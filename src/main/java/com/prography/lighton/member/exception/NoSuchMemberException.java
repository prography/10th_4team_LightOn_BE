package com.prography.lighton.member.exception;

import org.springframework.http.HttpStatus;

import com.prography.lighton.common.utils.ApiUtils;

public class NoSuchMemberException extends RuntimeException {

	private static final String MESSAGE = "존재하지 않는 회원입니다.";

	public NoSuchMemberException () {
		super (MESSAGE);
	}

	public NoSuchMemberException (String message) {
		super (message);
	}

	public ApiUtils.ApiResult<?> body() {
		return ApiUtils.error(HttpStatus.NOT_FOUND, getMessage());
	}

	public HttpStatus status () {
		return HttpStatus.NOT_FOUND;
	}
}
