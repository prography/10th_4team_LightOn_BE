package com.prography.lighton.performance.common.domain.entity.vo;

import com.prography.lighton.performance.common.domain.exception.InvalidPaymentInfoException;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Payment {

    @Column(nullable = false)
    private Boolean isPaid;

    private String account;

    private String bank;

    private String accountHolder;

    private Integer fee;

    public static Payment of(Boolean isPaid, String account, String bank, String accountHolder, Integer fee) {
        if (isInvalid(account, bank, accountHolder, fee)) {
            throw new InvalidPaymentInfoException();
        }

        return new Payment(isPaid, account, bank, accountHolder, fee);
    }

    private static boolean isInvalid(String account, String bank, String holder, Integer fee) {
        return StringUtils.isBlank(account)
                || StringUtils.isBlank(bank)
                || StringUtils.isBlank(holder)
                || fee == null || fee <= 0;
    }

    public static Payment free() {
        return new Payment(false, null, null, null, 0);
    }
}

