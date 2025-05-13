package com.prography.lighton.member.domain.entity.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class MarketingAgreement {

    @Column(nullable = false)
    private Boolean sms;

    @Column(nullable = false)
    private Boolean push;

    @Column(nullable = false)
    private Boolean email;
}
