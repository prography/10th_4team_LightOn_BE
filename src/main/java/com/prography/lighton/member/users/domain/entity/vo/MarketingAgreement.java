package com.prography.lighton.member.users.domain.entity.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MarketingAgreement {

    @Column(nullable = false, name = "sms_agreement")
    private Boolean sms;

    @Column(nullable = false, name = "push_agreement")
    private Boolean push;

    @Column(nullable = false, name = "email_agreement")
    private Boolean email;

    public static MarketingAgreement of(Boolean sms, Boolean push, Boolean email) {
        return new MarketingAgreement(sms, push, email);
    }
}
