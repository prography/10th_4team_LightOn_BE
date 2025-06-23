package com.prography.lighton.performance.users.presentation.dto.response;

import com.prography.lighton.performance.common.domain.entity.Performance;

public record RequestPerformanceResponseDTO(
        String account,
        String bank,
        String accountHolder,
        Integer fee
) {
    public static RequestPerformanceResponseDTO of(Performance performance) {
        return new RequestPerformanceResponseDTO(
                performance.getPayment().getAccount(),
                performance.getPayment().getBank(),
                performance.getPayment().getAccountHolder(),
                performance.getPayment().getFee()
        );
    }
}
