package com.prography.lighton.member.domain.entity.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class MarketingAgreement {

    @Column(nullable = false, name = "sms_agreement")
    private Boolean sms;

    @Column(nullable = false, name = "push_agreement")
    private Boolean push;

    @Column(nullable = false, name = "email_agreement")
    private Boolean email;

    protected MarketingAgreement() {}

    private MarketingAgreement(Boolean sms, Boolean push, Boolean email) {
        this.sms = sms;
        this.push = push;
        this.email = email;
    }

    public static MarketingAgreement of(Boolean sms, Boolean push, Boolean email) {
        return new MarketingAgreement(sms, push, email);
    }
}
