package com.prography.lighton.common.domain;

import org.springframework.util.StringUtils;

public class DomainValidator {

    public static String requireNonBlank(String value) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("해당 필드는 빈 문자열을 넣을 수 없습니다.");
        }
        return value;
    }
}
