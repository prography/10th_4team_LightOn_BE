package com.prography.lighton.performance.common.domain.entity.vo;

import com.prography.lighton.performance.common.domain.exception.InvalidSeatCountException;
import com.prography.lighton.performance.users.application.exception.BadPerformanceRequestException;
import com.prography.lighton.performance.users.application.exception.NotEnoughSeatsException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SeatInventory {

    private static final int UNLIMITED = 0;
    private static final int MAX_REQUESTED_SEATS = 10;
    private static final int MIN_REQUESTED_SEATS = 1;

    @Column
    @ColumnDefault("0")
    private Integer totalSeatsCount = 0;

    @Column
    @ColumnDefault("0")
    private Integer bookedSeatCount = 0;

    public static SeatInventory ofTotal(int totalSeatsCount) {
        if (totalSeatsCount <= 0) {
            return unlimited();
        }
        return new SeatInventory(totalSeatsCount, 0);
    }

    public static SeatInventory unlimited() {
        return new SeatInventory(UNLIMITED, 0);
    }

    public void updateSeat(int totalSeatsCount) {
        validTotalSeatCount(totalSeatsCount);
        this.totalSeatsCount = totalSeatsCount;
    }

    private void validTotalSeatCount(int totalSeatsCount) {
        if (totalSeatsCount > 0 && totalSeatsCount < bookedSeatCount) {
            throw new InvalidSeatCountException();
        }
    }

    private boolean isUnlimited() {
        return totalSeatsCount == UNLIMITED;
    }

    public void reserve(int applySeats) {
        validRequest(applySeats);

        if (this.totalSeatsCount - this.bookedSeatCount < applySeats) {
            throw new NotEnoughSeatsException("예매 가능한 공연 좌석이 부족합니다.");
        }
        bookedSeatCount += applySeats;
    }

    public void cancel(int applySeats) {
        validRequest(applySeats);

        if (this.bookedSeatCount < applySeats) {
            throw new NotEnoughSeatsException("취소 요청 좌석 수가 현재 예약 좌석 수를 초과했습니다.");
        }
        bookedSeatCount -= applySeats;
    }

    private void validRequest(int applySeats) {
        if (applySeats < MIN_REQUESTED_SEATS || applySeats > MAX_REQUESTED_SEATS) {
            throw new BadPerformanceRequestException();
        }

        if (isUnlimited()) {
            throw new BadPerformanceRequestException("별도의 신청이 필요하지 않은 공연입니다.");
        }
    }
}
