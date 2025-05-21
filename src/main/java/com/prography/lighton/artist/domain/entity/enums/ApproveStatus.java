package com.prography.lighton.artist.domain.entity.enums;

import com.prography.lighton.member.users.domain.exception.UnsupportedApproveStatusTypeException;

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
