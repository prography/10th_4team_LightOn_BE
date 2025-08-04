package com.prography.lighton.member.common.domain.entity.vo;

import static io.micrometer.common.util.StringUtils.isBlank;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.UUID;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Password {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()\\-_=+{}\\[\\]:;\"'<>,.?/\\\\|]).{8,20}$"
    );

    @Column(nullable = false, name = "password")
    private String value;

    public static Password encodeAndCreate(String rawPassword, PasswordEncoder encoder) {
        if (!PASSWORD_PATTERN.matcher(rawPassword).matches() || isBlank(rawPassword)) {
            throw new IllegalArgumentException("비밀번호 형식이 올바르지 않습니다.");
        }
        return new Password(encoder.encode(rawPassword));
    }

    public static Password forSocialLogin() {
        return new Password(UUID.randomUUID().toString());

    }

    public boolean matches(String rawPassword, PasswordEncoder encoder) {
        return encoder.matches(rawPassword, this.value);
    }

}
