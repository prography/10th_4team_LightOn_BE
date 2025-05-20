package com.prography.lighton.common.domain;

import org.springframework.util.StringUtils;

public class DomainValidator {

    public static String requireNonBlank(String value, Class<?> entityClass, String fieldName) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException(
                    String.format("[%s] %s 는 빈 문자열을 넣을 수 없습니다.", entityClass.getSimpleName(), fieldName)
            );
        }
        return value;
    }
}
