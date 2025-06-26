package com.prography.lighton.performance.common.domain.entity.enums;

import com.prography.lighton.performance.common.domain.exception.NoSuchRequestStatusException;

public enum RequestStatus {
    PENDING, APPROVED, REJECTED;

    public static RequestStatus from(String value) {
        try {
            return RequestStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new NoSuchRequestStatusException();
        }
    }
}
