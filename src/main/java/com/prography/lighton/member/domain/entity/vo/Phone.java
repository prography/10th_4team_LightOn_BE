package com.prography.lighton.member.domain.entity.vo;

import static io.micrometer.common.util.StringUtils.*;

import java.util.Objects;

import ch.qos.logback.core.util.StringUtil;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Phone {

    private static final String PHONE_NUMBER_PATTERN = "^\\d{3}\\d{3,4}\\d{4}$";

    @Column(nullable = false, length = 11, unique = true, name = "phone")
    private String value;

    protected Phone() {}

    private Phone (String value) {
        this.value = value;
    }

    public static Phone of(String phoneNumber) {
        if (isBlank(phoneNumber) || !phoneNumber.matches(PHONE_NUMBER_PATTERN)) {
            throw new IllegalArgumentException("전화번호 형식이 올바르지 않습니다.");
        }
        return new Phone(phoneNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Phone phone)) return false;
		return Objects.equals(value, phone.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }



}
