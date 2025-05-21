package com.prography.lighton.member.presentation.dto.request;

import com.prography.lighton.member.validation.annotation.ValidPhone;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CompleteMemberProfileRequestDTO(
        @NotBlank String name,
        @ValidPhone String phone,
        @NotNull @Positive Integer regionCode,

        AgreementsDTO agreements
) {
    public record AgreementsDTO(
            boolean terms,
            boolean privacy,
            boolean over14,
            MarketingDTO marketing
    ) {
        public record MarketingDTO(
                boolean sms,
                boolean push,
                boolean email
        ) {
        }
    }
}
