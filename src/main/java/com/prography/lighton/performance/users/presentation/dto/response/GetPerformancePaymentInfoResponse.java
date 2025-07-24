package com.prography.lighton.performance.users.presentation.dto.response;

import com.prography.lighton.performance.common.domain.entity.Performance;

public record GetPerformancePaymentInfoResponse(
        String account,
        String bank,
        String accountHolder,
        Integer fee
) {
    public static GetPerformancePaymentInfoResponse of(Performance performance, Integer requestedSeats) {
        return new GetPerformancePaymentInfoResponse(
                performance.getPayment().getAccount(),
                performance.getPayment().getBank(),
                performance.getPayment().getAccountHolder(),
                getTotalFee(performance, requestedSeats)
        );
    }

    public static int getTotalFee(Performance performance, Integer requestedSeats) {
        if (!performance.getPayment().getIsPaid()) {
            return 0;
        }
        return performance.getPayment().getFee() * requestedSeats;
    }
}

