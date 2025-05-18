package com.prography.lighton.member.exception;

import org.springframework.http.HttpStatus;

import com.prography.lighton.common.utils.ApiUtils;

public class DuplicateMemberException extends RuntimeException {

	private static final String MESSAGE = "이미 존재하는 회원입니다." ;

	public DuplicateMemberException() {
		super (MESSAGE) ;
	}

	public DuplicateMemberException(String message) {
		super (message) ;
	}

	public ApiUtils.ApiResult<?> body() {
		return ApiUtils.error(HttpStatus.CONFLICT, getMessage());
	}

	public HttpStatus status () {
		return HttpStatus.CONFLICT;
	}

}
