package com.prography.lighton.region.infrastructure.api;

public class ExternalApiCallException extends RuntimeException {

    private static final String MESSAGE = "API 호출에 실패하였습니다. ";

    public ExternalApiCallException() {
        super(MESSAGE);
    }

    public ExternalApiCallException(String message) {
        super(message);
    }

}
