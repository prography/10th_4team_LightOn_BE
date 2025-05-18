package com.prography.lighton.performance.domain.entity.vo;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Payment {

    private String account;

    private String bank;

    private String accountHolder;

    private Integer fee;

    // todo 나중에 값 검증 로직 구현하기
    public static Payment of(String account, String bank, String accountHolder, Integer fee) {
        return new Payment(account, bank, accountHolder, fee);
    }
}

