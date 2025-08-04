package com.prography.lighton.member.users.presentation.dto.request;

import com.prography.lighton.member.users.validation.annotation.ValidPhone;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CompleteMemberProfileRequest(
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
