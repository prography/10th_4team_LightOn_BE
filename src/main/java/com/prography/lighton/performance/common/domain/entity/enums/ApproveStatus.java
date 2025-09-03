package com.prography.lighton.performance.common.domain.entity.enums;

import com.prography.lighton.artist.admin.domain.exception.UnsupportedApproveStatusTypeException;

public enum ApproveStatus {
    PENDING, APPROVED, REJECTED, CANCELED;

    public static ApproveStatus from(String value) {
        try {
            return ApproveStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UnsupportedApproveStatusTypeException();
        }
    }
}
