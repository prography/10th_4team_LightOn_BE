package com.prography.lighton.member.users.application.query;

import static com.prography.lighton.common.fixture.MemberTestFixture.TEST_EMAIL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.prography.lighton.member.common.infrastructure.repository.MemberRepository;
import com.prography.lighton.member.users.presentation.dto.response.CheckDuplicateEmailResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CheckEmailExistServiceTest {

    @Mock
    MemberRepository memberRepository;

    CheckEmailExistService service;

    @Test
    @DisplayName("이메일 중복 확인 서비스가 정상적으로 작동한다.")
    void should_check_email_existence() {
        // Given
        service = new CheckEmailExistService(memberRepository);
        when(memberRepository.existsByEmail(TEST_EMAIL.getValue())).thenReturn(false);

        // When
        CheckDuplicateEmailResponse res = service.handle(TEST_EMAIL.getValue());

        // Then
        verify(memberRepository).existsByEmail(anyString());
        assertThat(res.isDuplicate()).isFalse();
    }

    @Test
    @DisplayName("이메일이 중복된 경우, 중복 응답을 반환한다.")
    void should_return_duplicate_response_when_email_exists() {
        // Given
        service = new CheckEmailExistService(memberRepository);
        when(memberRepository.existsByEmail(TEST_EMAIL.getValue())).thenReturn(true);

        // When
        CheckDuplicateEmailResponse res = service.handle(TEST_EMAIL.getValue());

        // Then
        verify(memberRepository).existsByEmail(anyString());
        assertThat(res.isDuplicate()).isTrue();
    }

}