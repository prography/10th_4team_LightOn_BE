package com.prography.lighton.member.exception;

public class NoSuchMemberException extends RuntimeException {

	public NoSuchMemberException(final String message) {
		super(message);
	}

	public NoSuchMemberException() {
		this("존재하지 않는 회원입니다.");
	}
}
