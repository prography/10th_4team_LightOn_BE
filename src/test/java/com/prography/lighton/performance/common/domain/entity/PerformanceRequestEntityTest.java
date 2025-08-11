package com.prography.lighton.performance.common.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.common.domain.entity.enums.RequestStatus;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PerformanceRequestEntityTest {

    @Test
    @DisplayName("정상적으로 performanceRequest 객체를 생성할 수 있다.")
    void should_create_performance_request() {
        // given
        Member member = mock(Member.class);
        Performance perf = mock(Performance.class);
        int seats = 4;
        int fee = 5000;

        // when
        PerformanceRequest req = PerformanceRequest.of(member, perf, seats, fee);

        // then
        assertThat(req.getMember()).isEqualTo(member);
        assertThat(req.getPerformance()).isEqualTo(perf);
        assertThat(req.getRequestStatus()).isEqualTo(RequestStatus.PENDING);
        assertThat(req.getRequestedSeats()).isEqualTo(seats);
        assertThat(req.getFee()).isEqualTo(fee);
        assertThat(req.getRequestedAt()).isNotNull();
        assertThat(req.getRequestedAt()).isBeforeOrEqualTo(LocalDateTime.now());
    }

    @Test
    @DisplayName("updateRequestStatus 호출 시, requestStatus가 변경된다")
    void should_update_request_status() {
        // given
        PerformanceRequest req = PerformanceRequest.of(
                mock(Member.class), mock(Performance.class), 1, 1000
        );

        // when
        req.updateRequestStatus(RequestStatus.APPROVED);

        // then
        assertThat(req.getRequestStatus()).isEqualTo(RequestStatus.APPROVED);
    }

    @Test
    @DisplayName("inactivate 호출 시, requestStatus가 REJECTED로 설정된다")
    void should_inactivate_sets_status_to_rejected() {
        // given
        PerformanceRequest req = PerformanceRequest.of(
                mock(Member.class), mock(Performance.class), 2, 2000
        );

        // when
        req.inactivate();

        // then
        assertThat(req.getRequestStatus()).isEqualTo(RequestStatus.REJECTED);
    }
}
