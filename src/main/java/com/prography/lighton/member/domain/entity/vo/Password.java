package com.prography.lighton.member.domain.entity.vo;

import static io.micrometer.common.util.StringUtils.*;

import java.util.Objects;
import java.util.regex.Pattern;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.prography.lighton.member.exception.InvalidMemberException;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

	private Password(String encrypted) {
		this.value = encrypted;
	}

	public boolean matches(String rawPassword, PasswordEncoder encoder) {
		return encoder.matches(rawPassword, this.value);
	}

}
