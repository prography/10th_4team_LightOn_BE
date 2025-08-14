package com.prography.lighton.performance.common.domain.entity.policy;

import com.prography.lighton.artist.admin.domain.exception.InvalidApproveStatusTransitionException;
import com.prography.lighton.artist.admin.domain.exception.SameApproveStatusException;
import com.prography.lighton.performance.common.domain.entity.enums.ApproveStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApprovalPolicy {

    public static ApproveStatus next(ApproveStatus current, ApproveStatus target) {
        if (current == target) {
            throw new SameApproveStatusException("동일한 상태로는 변경할 수 없습니다.");
        }
        if (!isAllowed(current, target)) {
            throw new InvalidApproveStatusTransitionException("현재 상태에서는 해당 상태로 변경할 수 없습니다.");
        }
        return target;
    }

    private static boolean isAllowed(ApproveStatus from, ApproveStatus to) {
        return switch (from) {
            case PENDING -> (to == ApproveStatus.APPROVED || to == ApproveStatus.REJECTED);
            case APPROVED -> (to == ApproveStatus.PENDING);
            case REJECTED -> false;
        };
    }

    public static boolean requiresApprovedAt(ApproveStatus from, ApproveStatus to) {
        return (from != ApproveStatus.APPROVED) && (to == ApproveStatus.APPROVED);
    }
}
