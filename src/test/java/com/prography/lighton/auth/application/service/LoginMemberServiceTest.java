package com.prography.lighton.auth.application.service;

import static com.prography.lighton.common.fixture.MemberTestFixture.createNormalMember;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.prography.lighton.auth.application.fake.FakeTokenProvider;
import com.prography.lighton.auth.application.validator.DuplicateEmailValidator;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.member.common.domain.exception.InvalidMemberException;
import com.prography.lighton.member.users.infrastructure.repository.MemberRepository;
import com.prography.lighton.member.users.infrastructure.repository.TemporaryMemberRepository;
import com.prography.lighton.member.users.presentation.dto.request.LoginMemberRequestDTO;
import com.prography.lighton.member.users.presentation.dto.response.LoginMemberResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class LoginMemberServiceTest {

    @Mock
    MemberRepository memberRepository;
    @Mock
    TemporaryMemberRepository temporaryMemberRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    DuplicateEmailValidator duplicateEmailValidator;

    private FakeTokenProvider fakeTokenProvider;
    private LoginMemberService loginMemberService;

    @BeforeEach
    void setUp() {
        fakeTokenProvider = new FakeTokenProvider();
        loginMemberService = new LoginMemberService(
                memberRepository,
                temporaryMemberRepository,
                passwordEncoder,
                fakeTokenProvider,
                duplicateEmailValidator
        );
    }

    @Test
    @DisplayName("로그인 요청 시, 회원 정보를 조회하고 토큰을 발급한다.")
    void should_login_and_issue_tokens() {
        // Given
        String password = "Password123!@";
        Member member = createNormalMember(passwordEncoder);
        ReflectionTestUtils.setField(member, "id", 1L);
        when(memberRepository.getMemberByEmail(member.getEmail())).thenReturn(member);
        when(temporaryMemberRepository.existsByEmailAndNotRegistered(member.getEmail().getValue())).thenReturn(false);
        when(passwordEncoder.matches(password, member.getPassword().getValue())).thenReturn(true);

        // When
        LoginMemberResponseDTO response = loginMemberService.login(
                new LoginMemberRequestDTO(member.getEmail().getValue(), password)
        );

        // Then
        assertEquals(member.getId().toString(), fakeTokenProvider.getSubject(response.accessToken()));
        assertEquals(member.getId().toString(), fakeTokenProvider.getSubject(response.refreshToken()));
    }

    @Test
    @DisplayName("로그인 요청 시, 임시 회원인 경우 예외를 던진다.")
    void should_not_allow_temporary_member_to_login() {
        // Given
        Member member = createNormalMember(passwordEncoder);
        when(temporaryMemberRepository.existsByEmailAndNotRegistered(member.getEmail().getValue())).thenReturn(true);

        // When & Then
        assertThrows(InvalidMemberException.class, () ->
                loginMemberService.login(new LoginMemberRequestDTO(member.getEmail().getValue(), "anyPassword"))
        );
    }

    @Test
    @DisplayName("로그인 요청 시, 비밀번호가 일치하지 않으면 예외를 던진다.")
    void should_throw_exception_when_password_not_match() {
        // Given
        Member member = createNormalMember(passwordEncoder);
        when(memberRepository.getMemberByEmail(member.getEmail())).thenReturn(member);
        when(temporaryMemberRepository.existsByEmailAndNotRegistered(member.getEmail().getValue())).thenReturn(false);
        when(passwordEncoder.matches("WrongPassword", member.getPassword().getValue())).thenReturn(false);

        // When & Then
        assertThrows(InvalidMemberException.class, () ->
                loginMemberService.login(new LoginMemberRequestDTO(member.getEmail().getValue(), "WrongPassword"))
        );
    }
}