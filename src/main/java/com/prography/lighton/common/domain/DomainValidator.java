package com.prography.lighton.common.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@Slf4j
public class DomainValidator {

    public static String requireNonBlank(String value) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("해당 필드는 빈 문자열을 넣을 수 없습니다.");
        }
        return value;
    }
}
