package com.prography.lighton.performance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.prography.lighton.artist.admin.domain.exception.InvalidApproveStatusTransitionException;
import com.prography.lighton.artist.admin.domain.exception.SameApproveStatusException;
import com.prography.lighton.performance.common.domain.entity.Performance;
import com.prography.lighton.performance.common.domain.entity.enums.ApproveStatus;
import com.prography.lighton.performance.domain.fixture.PerformanceFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PerformanceManageTest {

    @Test
    @DisplayName("처음 상태(PENDING)에서 APPROVED로 전환 시 상태가 APPROVED가 되고 approvedAt이 설정된다")
    void should_approve_from_pending() {
        Performance p = PerformanceFixture.defaultPerformance();
        assertThat(p.getApproveStatus()).isEqualTo(ApproveStatus.PENDING);
        assertThat(p.getApprovedAt()).isNull();

        p.managePerformanceApplication(ApproveStatus.APPROVED);

        assertThat(p.getApproveStatus()).isEqualTo(ApproveStatus.APPROVED);
        assertThat(p.getApprovedAt()).isNotNull();
    }

    @Test
    @DisplayName("처음 상태(PENDING)에서 REJECTED로 전환 시 상태가 REJECTED가 된다")
    void should_reject_from_pending() {
        Performance p = PerformanceFixture.defaultPerformance();
        p.managePerformanceApplication(ApproveStatus.REJECTED);
        assertThat(p.getApproveStatus()).isEqualTo(ApproveStatus.REJECTED);
        assertThat(p.getApprovedAt()).isNull();
    }

    @Test
    @DisplayName("동일한 상태로 전환 시 에러가 발생한다")
    void should_throw_if_same_status_on_pending() {
        Performance p = PerformanceFixture.defaultPerformance();
        assertThatThrownBy(() ->
                p.managePerformanceApplication(ApproveStatus.PENDING)
        ).isInstanceOf(SameApproveStatusException.class);
    }

    @Test
    @DisplayName("APPROVED 상태에서 PENDING으로 전환 시 상태가 PENDING으로 돌아간다")
    void should_return_to_pending_from_approved() {
        Performance p = PerformanceFixture.defaultPerformance();
        p.managePerformanceApplication(ApproveStatus.APPROVED);
        p.managePerformanceApplication(ApproveStatus.PENDING);
        assertThat(p.getApproveStatus()).isEqualTo(ApproveStatus.PENDING);
    }

    @Test
    @DisplayName("APPROVED 상태에서 REJECTED로 전환 시 에러가 발생한다")
    void should_throw_if_from_approved_to_rejected() {
        Performance p = PerformanceFixture.defaultPerformance();
        p.managePerformanceApplication(ApproveStatus.APPROVED);
        assertThatThrownBy(() ->
                p.managePerformanceApplication(ApproveStatus.REJECTED)
        ).isInstanceOf(InvalidApproveStatusTransitionException.class);
    }

    @Test
    @DisplayName("REJECTED 상태에서 APPROVED로 전환 시 에러가 발생한다")
    void should_throw_if_from_rejected_to_approved() {
        Performance p = PerformanceFixture.defaultPerformance();
        p.managePerformanceApplication(ApproveStatus.REJECTED);
        assertThatThrownBy(() ->
                p.managePerformanceApplication(ApproveStatus.APPROVED)
        ).isInstanceOf(InvalidApproveStatusTransitionException.class);
    }
}
