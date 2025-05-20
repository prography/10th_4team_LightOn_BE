package com.prography.lighton.performance.domain.entity.vo;

import jakarta.persistence.Embeddable;

@Embeddable
public class Payment {

    private String account;

    private String bank;

    private String accountHolder;

    private Integer fee;

    // todo 나중에 값 검증 로직 구현하기
}

