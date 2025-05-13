package com.prography.lighton.member.domain.entity.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Phone {

    @Column(nullable = false, length = 11, unique = true)
    private String phoneNumber;
}
