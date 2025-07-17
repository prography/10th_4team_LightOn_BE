package com.prography.lighton.performance.users.presentation.dto.request;


import jakarta.validation.constraints.NotNull;

public record PaymentDTO(

        @NotNull(message = "비용 유뮤는 필수입니다.")
        Boolean isPaid,

        Integer price,

        String account,

        String bank,

        String accountHolder

) {
}
