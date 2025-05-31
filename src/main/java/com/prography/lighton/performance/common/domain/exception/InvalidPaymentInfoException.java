package com.prography.lighton.performance.common.domain.exception;

import com.prography.lighton.common.exception.base.InvalidException;

public class InvalidPaymentInfoException extends InvalidException {

    private static final String MESSAGE = "유료 공연일 경우 계좌 정보, 은행명, 예금주, 금액은 모두 필수입니다.";

    public InvalidPaymentInfoException() {
        super(MESSAGE);
    }

    public InvalidPaymentInfoException(String message) {
        super(message);
    }
}
