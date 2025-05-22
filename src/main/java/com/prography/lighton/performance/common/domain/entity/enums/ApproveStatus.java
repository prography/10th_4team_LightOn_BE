package com.prography.lighton.performance.common.domain.entity.enums;

public enum ApproveStatus {
    PENDING, APPROVED, REJECTED;

    public static ApproveStatus from(String value) {
        try {
            return ApproveStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(); // 추후 예외 변경 예정 - 관리자 & 아티스트 머지 후
        }
    }
}
