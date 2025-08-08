package com.prography.lighton.auth.application.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.prography.lighton.auth.domain.enums.SocialLoginType;
import com.prography.lighton.member.common.domain.exception.DuplicateMemberException;
import com.prography.lighton.member.common.infrastructure.repository.MemberRepository;
import com.prography.lighton.member.common.infrastructure.repository.TemporaryMemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DuplicateEmailValidatorTest {

    @Mock
    MemberRepository memberRepository;

    @Mock
    TemporaryMemberRepository temporaryMemberRepository;

    @InjectMocks
    DuplicateEmailValidator validator;

    @Test
    @DisplayName("이메일 중복 검증을 통과한다.")
    void should_pass_duplicate_email_validation() {
        // Given
        String email = "example@example.com";
        when(memberRepository.existsConflictingLoginTypeByEmail(anyString(), any()))
                .thenReturn(false);
        when(temporaryMemberRepository.existsConflictingLoginTypeByEmail(anyString(), any()))
                .thenReturn(false);

        // When & Then
        assertDoesNotThrow(() ->
                validator.validateConflictingLoginType(email, SocialLoginType.DEFAULT));
    }

    @Test
    @DisplayName("해당 이메일로 가입된 회원이 존재하는 경우 이메일 중복 검증에 실패한다.")
    void should_fail_duplicate_email_validation_when_member_exist() {
        // given
        when(memberRepository.existsConflictingLoginTypeByEmail("test@example.com", SocialLoginType.DEFAULT))
                .thenReturn(true);

        // when & then
        assertThrows(DuplicateMemberException.class, () ->
                validator.validateConflictingLoginType("test@example.com", SocialLoginType.DEFAULT));
    }

    @Test
    @DisplayName("해당 이메일로 가입된 임시 회원이 존재하는 경우 이메일 중복 검증에 실패한다.")
    void should_fail_duplicate_email_validation_when_temporary_member_exist() {
        // given
        when(memberRepository.existsConflictingLoginTypeByEmail("test@example.com", SocialLoginType.DEFAULT))
                .thenReturn(false);
        when(temporaryMemberRepository.existsConflictingLoginTypeByEmail("test@example.com", SocialLoginType.DEFAULT))
                .thenReturn(true);

        // when & then
        assertThrows(DuplicateMemberException.class, () ->
                validator.validateConflictingLoginType("test@example.com", SocialLoginType.DEFAULT));
    }
}