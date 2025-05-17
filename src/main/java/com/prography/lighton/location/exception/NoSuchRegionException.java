package com.prography.lighton.location.exception;

public class NoSuchRegionException extends RuntimeException {

	private static final String MESSAGE = "해당 지역은 존재하지 않습니다.";

	public NoSuchRegionException() {
		super(MESSAGE);
	}

	public NoSuchRegionException(String message) {
		super(message);
	}

}
