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
}
