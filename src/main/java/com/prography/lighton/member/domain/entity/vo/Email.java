package com.prography.lighton.member.domain.entity.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Email {

    @Column(nullable = false, unique = true)
    private String email;
}
