package com.prography.lighton.performance.common.domain.entity.profile;

import com.prography.lighton.performance.common.domain.entity.enums.Seat;
import com.prography.lighton.performance.common.domain.entity.enums.Type;
import com.prography.lighton.performance.common.domain.entity.vo.Payment;
import com.prography.lighton.performance.common.domain.entity.vo.SeatInventory;
import java.util.List;

public record PerformanceProfile(
        Type type,
        Payment payment,
        List<Seat> seats,
        SeatInventory seatInventory
) {

    public static PerformanceProfile concert(Payment payment, List<Seat> seats, int totalSeats) {
        return new PerformanceProfile(
                Type.CONCERT,
                payment,
                List.copyOf(seats),
                SeatInventory.ofTotal(totalSeats)
        );
    }

    public static PerformanceProfile busking() {
        return new PerformanceProfile(
                Type.BUSKING,
                Payment.free(),
                List.of(Seat.STANDING),
                SeatInventory.unlimited()
        );
    }
}
