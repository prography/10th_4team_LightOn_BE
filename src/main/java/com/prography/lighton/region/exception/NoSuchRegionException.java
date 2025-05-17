package com.prography.lighton.region.exception;

import org.springframework.http.HttpStatus;

public class NoSuchRegionException extends RuntimeException {

	private final HttpStatus httpStatus;

	public NoSuchRegionException(HttpStatus httpStatus) {
		super("해당 지역은 존재하지 않습니다.");
		this.httpStatus = httpStatus;
	}

	public NoSuchRegionException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}

}
