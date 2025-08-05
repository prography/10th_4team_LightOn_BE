package com.prography.lighton.auth.application.service;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.prography.lighton.auth.application.exception.PhoneVerificationFailedException;
import com.prography.lighton.auth.application.fake.FakeAuthVerificationService;
import com.prography.lighton.auth.application.fake.FakeSmsSender;
import com.prography.lighton.auth.presentation.dto.request.VerifyPhoneRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PhoneVerificationServiceTest {

    private FakeSmsSender fakeSmsSender;
    private FakeAuthVerificationService fakeAuthVerificationService;
    private PhoneVerificationService phoneVerificationService;

    @BeforeEach
    void setUp() {
        fakeSmsSender = new FakeSmsSender();
        fakeAuthVerificationService = new FakeAuthVerificationService();
        phoneVerificationService = new PhoneVerificationService(fakeSmsSender, fakeAuthVerificationService);
    }

    @Test
    @DisplayName("인증 코드를 전송할 수 있다.")
    void should_send_auth_code() {
        // given
        String phoneNumber = "01012345678";

        // when
        phoneVerificationService.sendAuthCode(phoneNumber);

        // then
        assertTrue(fakeSmsSender.wasSmsSentTo("01012345678"));
    }

    @Test
    @DisplayName("인증 코드를 검증할 수 있다.")
    void should_verify_phone() {
        // given
        String phoneNumber = "01012345678";
        String code = "123456";
        fakeAuthVerificationService.saveCode(phoneNumber, code);

        // when
        phoneVerificationService.verifyPhone(new VerifyPhoneRequest(phoneNumber, code));

        // then
        assertDoesNotThrow(() -> fakeAuthVerificationService.checkIsVerified(phoneNumber));
    }

    @Test
    @DisplayName("인증 코드 검증 실패 시 예외가 발생한다.")
    void should_throw_exception_when_verification_fails() {
        // given
        String phoneNumber = "01012345678";
        String code = "123456";
        String wrongCode = "654321";
        fakeAuthVerificationService.saveCode(phoneNumber, code);

        // when & then
        assertThrows(PhoneVerificationFailedException.class,
                () -> phoneVerificationService.verifyPhone(new VerifyPhoneRequest(phoneNumber, wrongCode)));

    }

}
