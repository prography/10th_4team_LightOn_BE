package com.prography.lighton.member.users.application.command;

import static com.prography.lighton.common.fixture.MemberTestFixture.TEST_PASSWORD_STRING;
import static com.prography.lighton.common.fixture.MemberTestFixture.createTemporaryMember;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.prography.lighton.member.common.domain.entity.TemporaryMember;
import com.prography.lighton.member.common.domain.exception.InvalidMemberException;
import com.prography.lighton.member.common.infrastructure.repository.TemporaryMemberRepository;
import com.prography.lighton.member.users.presentation.dto.request.RegisterMemberRequest;
import com.prography.lighton.member.users.presentation.dto.response.RegisterMemberResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class RegisterMemberServiceTest {

    @Mock
    TemporaryMemberRepository temporaryMemberRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    RegisterMemberService service;

    @Test
    @DisplayName("임시 회원을 정상적으로 생성할 수 있다.")
    void should_create_temporary_member() {
        // Given
        RegisterMemberRequest req = new RegisterMemberRequest("user@example.com", "Password123!@");

        TemporaryMember savedEntity = createTemporaryMember();
        when(temporaryMemberRepository.save(any())).thenReturn(savedEntity);

        // When
        RegisterMemberResponse res = service.handle(req);

        // Then
        verify(temporaryMemberRepository).save(any());
    }

    @Test
    @DisplayName("올바르지 않은 이메일 입력 시, 예외가 발생한다.")
    void should_throw_exception_for_invalid_email() {
        // Given
        RegisterMemberRequest req = new RegisterMemberRequest("invalid-email", TEST_PASSWORD_STRING);

        // When & Then
        assertThrows(InvalidMemberException.class, () -> service.handle(req));
    }

    @Test
    @DisplayName("올바르지 않은 비밀번호 입력 시, 예외가 발생한다.")
    void should_throw_exception_for_invalid_password() {
        // Given
        RegisterMemberRequest req = new RegisterMemberRequest("user@example.com", "invalid");

        // When & Then
        assertThrows(InvalidMemberException.class, () -> service.handle(req));
    }

}
