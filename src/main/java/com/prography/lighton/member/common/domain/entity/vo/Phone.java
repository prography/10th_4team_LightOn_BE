package com.prography.lighton.member.common.domain.entity.vo;

import static io.micrometer.common.util.StringUtils.isBlank;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Phone {

    private static final String PHONE_NUMBER_PATTERN = "^\\d{3}\\d{3,4}\\d{4}$";

    @Column(nullable = false, length = 20, unique = true, name = "phone")
    private String value;

    public static Phone of(String phoneNumber) {
        if (isBlank(phoneNumber) || !phoneNumber.matches(PHONE_NUMBER_PATTERN)) {
            throw new IllegalArgumentException("전화번호 형식이 올바르지 않습니다.");
        }
        return new Phone(phoneNumber);
    }
}
