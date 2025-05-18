package com.prography.lighton.member.domain.entity.vo;

import static io.micrometer.common.util.StringUtils.*;

import java.util.Objects;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;

import com.prography.lighton.member.exception.InvalidMemberException;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Email {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    @Column(nullable = false, unique = true, name = "email")
    private String value;

    private Email(String value) {
        this.value = value;
    }

    public static Email of(String value) {
        if (isBlank(value) || !EMAIL_PATTERN.matcher(value).matches()) {
            throw new InvalidMemberException("이메일 형식이 올바르지 않습니다.");
        }
        return new Email(value);
    }
}
