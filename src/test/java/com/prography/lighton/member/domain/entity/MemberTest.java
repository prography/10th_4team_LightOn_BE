package com.prography.lighton.member.domain.entity;

import static com.prography.lighton.common.fixture.MemberTestFixture.TEST_AGREEMENT;
import static com.prography.lighton.common.fixture.MemberTestFixture.TEST_EMAIL;
import static com.prography.lighton.common.fixture.MemberTestFixture.TEST_LOGIN_TYPE;
import static com.prography.lighton.common.fixture.MemberTestFixture.TEST_NAME;
import static com.prography.lighton.common.fixture.MemberTestFixture.TEST_PHONE;
import static com.prography.lighton.common.fixture.MemberTestFixture.TEST_REGION;
import static com.prography.lighton.common.fixture.MemberTestFixture.createNormalMember;
import static com.prography.lighton.common.fixture.MemberTestFixture.createNormalMemberWith;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.member.common.domain.entity.association.PreferredGenre;
import com.prography.lighton.member.common.domain.entity.enums.Authority;
import com.prography.lighton.member.common.domain.entity.vo.Password;
import com.prography.lighton.member.common.domain.exception.InvalidMemberException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

class MemberTest {

    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

    @Test
    @DisplayName("일반 회원을 생성할 수 있다.")
    void should_create_member_with_normal_authority() {
        // Given & When
        Member member = createNormalMember(passwordEncoder);

        // Then
        assertEquals(Authority.NORMAL, member.getAuthority());
        assertEquals(TEST_EMAIL, member.getEmail());
        assertEquals(TEST_NAME, member.getName());
    }

    @Test
    @DisplayName("일반 회원인 경우, 권한이 NORMAL로 설정된다.")
    void should_have_normal_authority() {
        // Given
        Member member = createNormalMember(passwordEncoder);

        // When & Then
        assertFalse(member.isAdmin());
    }

    @Test
    @DisplayName("비밀번호를 성공적으로 검증한다.")
    void should_validate_password_successfully() {
        // Given
        when(passwordEncoder.matches(anyString(), any())).thenReturn(true);
        Password actualPassword = Password.encodeAndCreate("Aa123!aaaa", passwordEncoder);
        Member member = createNormalMemberWith(TEST_EMAIL, actualPassword, TEST_REGION, TEST_NAME, TEST_PHONE,
                TEST_LOGIN_TYPE, TEST_AGREEMENT);

        // When & Then
        assertDoesNotThrow(() -> member.validatePassword("Aa123!aaaa", passwordEncoder));
    }

    @Test
    @DisplayName("비밀번호가 잘못된 경우 예외를 던진다.")
    void should_throw_when_password_invalid() {
        // Given
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        when(encoder.matches(anyString(), anyString())).thenReturn(false);
        Member member = createNormalMember(passwordEncoder);

        // When & Then
        assertThrows(InvalidMemberException.class, () -> member.validatePassword("wrong", encoder));
    }


    @Test
    @DisplayName("회원 정보를 수정할 수 있다.")
    void should_update_preferred_genres() {
        // Given
        Member member = createNormalMember(passwordEncoder);
        PreferredGenre genre1 = mock(PreferredGenre.class);
        PreferredGenre genre2 = mock(PreferredGenre.class);

        // When
        member.editPreferredGenres(List.of(genre1, genre2));

        // Then
        assertEquals(2, member.getPreferredGenres().size());
    }

    @Test
    @DisplayName("회원 탈퇴 시 이메일과 전화번호를 마스킹한다.")
    void should_mask_email_and_phone_on_withdraw() {
        // Given
        Member member = createNormalMember(passwordEncoder);
        setField(member, "id", 1L);

        // When
        member.withdraw();

        // Then
        assertTrue(member.getEmail().getValue().contains("_DELETED_1"));
        assertTrue(member.getPhone().getValue().contains("_DELETED_1"));
    }
}
