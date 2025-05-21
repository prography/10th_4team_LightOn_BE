package com.prography.lighton.auth.application.exception;

import org.springframework.http.HttpStatus;

public class ExternalApiCallException extends RuntimeException {

    private static final String MESSAGE = "외부 API 호출에 실패했습니다.";

    public ExternalApiCallException(final String message) {
        super(message);
    }

    public ExternalApiCallException() {
        this(MESSAGE);
    }

    public String body() {
        return getMessage();
    }

    public HttpStatus status() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
