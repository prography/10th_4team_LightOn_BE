package com.prography.lighton.performance.common.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.common.domain.entity.enums.ApproveStatus;
import com.prography.lighton.performance.common.domain.entity.fixture.PerformanceFixture;
import com.prography.lighton.performance.common.domain.exception.NotAuthorizedPerformanceRequestException;
import com.prography.lighton.performance.common.domain.exception.PerformanceNotApprovedException;
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
        assertThat(p.getSeatInventory().getBookedSeatCount()).isEqualTo(applySeats);
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
        ReflectionTestUtils.setField(p.getSeatInventory(), "totalSeatsCount", totalSeats);
        p.createRequest(totalSeats, PerformanceFixture.defaultMember());

        int remainingSeats = p.getSeatInventory().getTotalSeatsCount() - p.getSeatInventory().getBookedSeatCount();

        assertThatThrownBy(() -> p.createRequest(remainingSeats + 1, PerformanceFixture.defaultMember()))
                .isInstanceOf(NotEnoughSeatsException.class);
    }

    @Test
    @DisplayName("공연 취소시 bookedSeatCount가 정확히 감소한다")
    void should_decrease_bookedSeatCount_on_cancel() {
        Performance p = PerformanceFixture.defaultPerformance();
        p.managePerformanceApplication(ApproveStatus.APPROVED);

        int applySeat = 2;
        PerformanceRequest request = p.createRequest(applySeat, PerformanceFixture.defaultMember());

        p.cancelRequest(request, request.getMember());
        assertThat(p.getSeatInventory().getBookedSeatCount()).isEqualTo(0);
    }

    @Test
    @DisplayName("공연 취소시 취소 좌석수가 예약 좌석수보다 많으면 에러가 발생한다.")
    void should_throw_cancelSeatCount_is_more_then_bookedSeatCount() {
        Member member = PerformanceFixture.defaultMember();
        Performance p = PerformanceFixture.defaultPerformance();
        p.managePerformanceApplication(ApproveStatus.APPROVED);

        int applySeat = 2;
        int wrongCancelSeat = 5;
        PerformanceRequest request = p.createRequest(applySeat, member);
        ReflectionTestUtils.setField(request, "requestedSeats", wrongCancelSeat);

        assertThatThrownBy(() -> p.cancelRequest(request, member))
                .isInstanceOf(NotEnoughSeatsException.class);
    }

    @Test
    @DisplayName("공연 취소시 공연 신청자와 공연 취소 요청자가 일치하지 않으면 에러가 발생한다.")
    void should_throw_is_not_same_request_member() {
        Member member = PerformanceFixture.defaultMember();
        Performance p = PerformanceFixture.defaultPerformance();
        p.managePerformanceApplication(ApproveStatus.APPROVED);

        int applySeat = 2;
        PerformanceRequest request = p.createRequest(applySeat, member);
        Member wrongMember = mock(Member.class);

        assertThatThrownBy(() -> p.cancelRequest(request, wrongMember))
                .isInstanceOf(NotAuthorizedPerformanceRequestException.class);
    }
}
