package com.prography.lighton.member.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import com.prography.lighton.auth.domain.enums.SocialLoginType;
import com.prography.lighton.member.common.domain.entity.TemporaryMember;
import com.prography.lighton.member.common.domain.entity.vo.Email;
import com.prography.lighton.member.common.domain.entity.vo.Password;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

public class TemporaryMemberTest {

    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

    private final Email email = Email.of("test@example.com");
    private final Password password = Password.encodeAndCreate("Password123!@", passwordEncoder);

    @Test
    @DisplayName("임시 일반 회원을 생성할 수 있다.")
    void should_create_temporary_normal_member() {
        // Given & When
        TemporaryMember temporaryMember = TemporaryMember.of(email, password);

        // Then
        assertEquals(email, temporaryMember.getEmail());
        assertEquals(password, temporaryMember.getPassword());
        assertEquals(SocialLoginType.DEFAULT, temporaryMember.getLoginType());
        assertFalse(temporaryMember.isRegistered());
    }

    @Test
    @DisplayName("임시 소셜 로그인 회원을 생성할 수 있다.")
    void should_create_temporary_social_login_member() {
        // Given & When
        TemporaryMember temporaryMember = TemporaryMember.socialLoginMemberOf(email, SocialLoginType.KAKAO);

        // Then
        assertEquals(email, temporaryMember.getEmail());
        assertEquals(SocialLoginType.KAKAO, temporaryMember.getLoginType());
        assertFalse(temporaryMember.isRegistered());
    }


    @Test
    @DisplayName("회원 등록 시 isRegistered 값이 true로 변경된다.")
    void should_mark_member_as_registered() {
        // Given
        TemporaryMember temporaryMember = TemporaryMember.of(email, password);

        // When
        temporaryMember.markAsRegistered();

        // Then
        assertTrue(temporaryMember.isRegistered());
    }
}
