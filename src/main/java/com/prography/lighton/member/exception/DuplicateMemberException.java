package com.prography.lighton.member.exception;

public class DuplicateMemberException extends RuntimeException{

	public DuplicateMemberException(final String message) {
		super(message);
	}

	public DuplicateMemberException() {
		this("이미 존재하는 회원입니다.");
	}
}
