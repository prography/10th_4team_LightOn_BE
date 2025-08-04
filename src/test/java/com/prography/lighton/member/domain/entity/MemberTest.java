package com.prography.lighton.member.domain.entity;

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

import com.prography.lighton.auth.domain.enums.SocialLoginType;
import com.prography.lighton.common.domain.vo.RegionInfo;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.member.common.domain.entity.association.PreferredGenre;
import com.prography.lighton.member.common.domain.entity.enums.Authority;
import com.prography.lighton.member.common.domain.entity.vo.Email;
import com.prography.lighton.member.common.domain.entity.vo.MarketingAgreement;
import com.prography.lighton.member.common.domain.entity.vo.Password;
import com.prography.lighton.member.common.domain.entity.vo.Phone;
import com.prography.lighton.member.common.domain.exception.InvalidMemberException;
import com.prography.lighton.region.domain.entity.Region;
import com.prography.lighton.region.domain.entity.SubRegion;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

class MemberTest {

    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

    private final Email email = Email.of("test@example.com");
    private final Password password = Password.encodeAndCreate("Password123!@", passwordEncoder);
    private final RegionInfo region = RegionInfo.of(mock(Region.class), mock(SubRegion.class));
    private final String name = "홍길동";
    private final Phone phone = Phone.of("01012345678");
    private final SocialLoginType loginType = SocialLoginType.KAKAO;
    private final MarketingAgreement agreement = MarketingAgreement.of(true, true, true);

    @Test
    @DisplayName("일반 회원을 생성할 수 있다.")
    void should_create_member_with_normal_authority() {
        // Given (모든 값은 필드에서 설정)

        // When
        Member member = Member.toNormalMember(email, password, region, name, phone, loginType, agreement);

        // Then
        assertEquals(Authority.NORMAL, member.getAuthority());
        assertEquals(email, member.getEmail());
        assertEquals(name, member.getName());
    }

    @Test
    @DisplayName("일반 회원인 경우, 권한이 NORMAL로 설정된다.")
    void should_have_normal_authority() {
        // Given
        Member member = Member.toNormalMember(email, password, region, name, phone, loginType, agreement);

        // When & Then
        assertFalse(member.isAdmin());
    }

    @Test
    @DisplayName("비밀번호를 성공적으로 검증한다.")
    void should_validate_password_successfully() {
        // Given
        when(passwordEncoder.matches(anyString(), any())).thenReturn(true);
        Password actualPassword = Password.encodeAndCreate("Aa123!aaaa", passwordEncoder);
        Member member = Member.toNormalMember(email, actualPassword, region, name, phone, loginType, agreement);

        // When & Then
        assertDoesNotThrow(() -> member.validatePassword("Aa123!aaaa", passwordEncoder));
    }

    @Test
    @DisplayName("비밀번호가 잘못된 경우 예외를 던진다.")
    void should_throw_when_password_invalid() {
        // Given
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        when(encoder.matches(anyString(), anyString())).thenReturn(false);
        Member member = Member.toNormalMember(email, password, region, name, phone, loginType, agreement);

        // When & Then
        assertThrows(InvalidMemberException.class, () -> member.validatePassword("wrong", encoder));
    }


    @Test
    @DisplayName("회원 정보를 수정할 수 있다.")
    void should_update_preferred_genres() {
        // Given
        Member member = Member.toNormalMember(email, password, region, name, phone, loginType, agreement);
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
        Member member = Member.toNormalMember(email, password, region, name, phone, loginType, agreement);
        setField(member, "id", 1L);

        // When
        member.withdraw();

        // Then
        assertTrue(member.getEmail().getValue().contains("_DELETED_1"));
        assertTrue(member.getPhone().getValue().contains("_DELETED_1"));
    }
}
