package com.prography.lighton.member.domain.entity.vo;

import static io.micrometer.common.util.StringUtils.*;

import java.util.Objects;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;

import com.prography.lighton.member.exception.InvalidMemberException;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Email {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    @Column(nullable = false, unique = true, name = "email")
    private String value;

    protected Email() {}

    private Email(String value) {
        this.value = value;
    }

    public static Email of(String value) {
        if (isBlank(value)|| !EMAIL_PATTERN.matcher(value).matches()) {
            throw new InvalidMemberException("이메일 형식이 올바르지 않습니다.", HttpStatus.BAD_REQUEST);
        }
        return new Email(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email)) return false;
        Email email = (Email) o;
        return Objects.equals(value, email.value);
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
