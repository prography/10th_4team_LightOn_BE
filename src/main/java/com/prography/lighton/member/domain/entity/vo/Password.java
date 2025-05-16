package com.prography.lighton.member.domain.entity.vo;

import java.util.regex.Pattern;

import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Password {

	private static final Pattern PASSWORD_PATTERN = Pattern.compile(
			"^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()\\-_=+{}\\[\\]:;\"'<>,.?/\\\\|]).{8,20}$"
	);

	@Column(nullable = false)
	private String value;

	protected Password() {}

	public static Password encodeAndCreate(String rawPassword, PasswordEncoder encoder) {
		if (!PASSWORD_PATTERN.matcher(rawPassword).matches()) {
			throw new IllegalArgumentException("비밀번호 형식이 올바르지 않습니다.");
		}
		return new Password(encoder.encode(rawPassword));
	}

	private Password(String encrypted) {
		this.value = encrypted;
	}

	public String getValue() {
		return value;
	}
}