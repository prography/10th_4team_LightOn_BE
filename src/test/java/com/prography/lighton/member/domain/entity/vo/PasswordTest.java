package com.prography.lighton.member.domain.entity.vo;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.prography.lighton.member.common.domain.entity.vo.Password;
import com.prography.lighton.member.common.domain.exception.InvalidMemberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

class PasswordTest {

    PasswordEncoder encoder = mock(PasswordEncoder.class);

    @Test
    @DisplayName("유효한 비밀번호로 생성 시 암호화된 Password 객체가 생성된다")
    void create_valid_password() {
        String rawPassword = "Aa1!aaaa";
        when(encoder.encode(rawPassword)).thenReturn("encodedPwd");
        when(encoder.matches(anyString(), anyString())).thenReturn(true);

        Password password = Password.encodeAndCreate(rawPassword, encoder);

        assertNotNull(password);
        assertTrue(password.matches(rawPassword, encoder));
        verify(encoder).encode(rawPassword);
    }

    @Test
    @DisplayName("비밀번호가 형식에 맞지 않으면 예외가 발생한다")
    void invalid_password_format_throws_exception() {
        String invalidPassword = "1234";

        assertThrows(InvalidMemberException.class, () ->
                Password.encodeAndCreate(invalidPassword, encoder)
        );
    }

    @Test
    @DisplayName("공백 또는 빈 문자열은 예외를 발생시킨다")
    void blank_password_throws_exception() {
        assertThrows(InvalidMemberException.class, () ->
                Password.encodeAndCreate("   ", encoder)
        );
    }

    @Test
    @DisplayName("소셜 로그인용 비밀번호는 UUID로 생성된다")
    void create_social_login_password() {
        Password password = Password.forSocialLogin();

        assertNotNull(password);
    }
}