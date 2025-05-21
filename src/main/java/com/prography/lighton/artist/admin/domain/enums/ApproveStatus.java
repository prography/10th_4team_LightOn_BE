package com.prography.lighton.artist.admin.domain.enums;

import com.prography.lighton.member.domain.exception.UnsupportedApproveStatusTypeException;

public enum ApproveStatus {
    PENDING, APPROVED, REJECTED;

    public static ApproveStatus from(String value) {
        try {
            return ApproveStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UnsupportedApproveStatusTypeException();
        }
    }
}
