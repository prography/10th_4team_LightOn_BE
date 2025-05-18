package com.prography.lighton.common.exception.base;

import org.springframework.http.HttpStatus;

import com.prography.lighton.common.utils.ApiUtils;

public class InvalidException extends RuntimeException {

	private static final String MESSAGE = "유효하지 않은 요청입니다.";

	public InvalidException(final String message) {
		super(message);
	}

	public InvalidException() {
		this(MESSAGE);
	}

	public ApiUtils.ApiResult<?> body() {
		return ApiUtils.error(HttpStatus.BAD_REQUEST, getMessage());
	}

	public HttpStatus status () {
		return HttpStatus.BAD_REQUEST;
	}
}
