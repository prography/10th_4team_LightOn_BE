package com.prography.lighton.performance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.prography.lighton.performance.common.domain.entity.Performance;
import com.prography.lighton.performance.common.domain.entity.PerformanceRequest;
import com.prography.lighton.performance.common.domain.entity.enums.ApproveStatus;
import com.prography.lighton.performance.common.domain.exception.PerformanceNotApprovedException;
import com.prography.lighton.performance.domain.fixture.PerformanceFixture;
import com.prography.lighton.performance.users.application.exception.BadPerformanceRequestException;
import com.prography.lighton.performance.users.application.exception.NotEnoughSeatsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class PerformanceRequestTest {

    @Test
    @DisplayName("APPROVED 상태에서 유효한 좌석 수로 요청을 생성하면 bookedSeatCount가 증가하고 PerformanceRequest가 반환된다")
    void should_create_request_and_increment_bookedSeatCount() {
        Performance p = PerformanceFixture.defaultPerformance();
        p.managePerformanceApplication(ApproveStatus.APPROVED);

        int applySeats = 3;
        PerformanceRequest req = p.createRequest(applySeats, PerformanceFixture.defaultMember());

        assertThat(req.getPerformance()).isEqualTo(p);
        assertThat(req.getRequestedSeats()).isEqualTo(applySeats);
        assertThat(req.getFee()).isEqualTo(PerformanceFixture.defaultPayment().getFee());
        assertThat(p.getBookedSeatCount()).isEqualTo(applySeats);
    }

    @Test
    @DisplayName("PENDING 상태에서 요청 생성 시 에러가 발생한다")
    void should_throw_if_request_when_not_approved() {
        Performance p = PerformanceFixture.defaultPerformance();

        assertThatThrownBy(() -> p.createRequest(1, PerformanceFixture.defaultMember()))
                .isInstanceOf(PerformanceNotApprovedException.class);
    }

    @Test
    @DisplayName("applySeats가 범위(1~10)를 벗어나면 에러가 발생한다")
    void should_throw_for_invalid_applySeats() {
        Performance p = PerformanceFixture.defaultPerformance();
        p.managePerformanceApplication(ApproveStatus.APPROVED);

        assertThatThrownBy(() -> p.createRequest(0, PerformanceFixture.defaultMember()))
                .isInstanceOf(BadPerformanceRequestException.class);
        assertThatThrownBy(() -> p.createRequest(11, PerformanceFixture.defaultMember()))
                .isInstanceOf(BadPerformanceRequestException.class);
    }

    @Test
    @DisplayName("남은 좌석 수보다 많은 좌석을 요청하면 에러가 발생한다")
    void should_throw_if_not_enough_seats() {
        Performance p = PerformanceFixture.defaultPerformance();
        p.managePerformanceApplication(ApproveStatus.APPROVED);

        int totalSeats = 10;
        ReflectionTestUtils.setField(p, "totalSeatsCount", totalSeats);
        p.createRequest(totalSeats, PerformanceFixture.defaultMember());

        int remainingSeats = p.getTotalSeatsCount() - p.getBookedSeatCount();

        assertThatThrownBy(() -> p.createRequest(remainingSeats + 1, PerformanceFixture.defaultMember()))
                .isInstanceOf(NotEnoughSeatsException.class);
    }

    @Test
    @DisplayName("공연 취소시 bookedSeatCount가 정확히 감소한다")
    void should_decrease_bookedSeatCount_on_cancel() {
        Performance p = PerformanceFixture.defaultPerformance();
        p.managePerformanceApplication(ApproveStatus.APPROVED);

        int applySeat = 2;
        int cancelSeat = 1;
        p.createRequest(applySeat, PerformanceFixture.defaultMember());

        p.cancelRequest(cancelSeat);
        assertThat(p.getBookedSeatCount()).isEqualTo(applySeat - cancelSeat);
    }

    @Test
    @DisplayName("공연 취소시 bookedSeatCount가 0 아래로 내려가면 0으로 고정된다")
    void should_floor_bookedSeatCount_at_zero() {
        Performance p = PerformanceFixture.defaultPerformance();
        p.managePerformanceApplication(ApproveStatus.APPROVED);

        int applySeat = 2;
        int wrongCancelSeat = 5;
        p.createRequest(applySeat, PerformanceFixture.defaultMember());

        p.cancelRequest(wrongCancelSeat);
        assertThat(p.getBookedSeatCount()).isZero();
    }
}
