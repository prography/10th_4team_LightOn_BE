package com.prography.lighton.auth.domain.vo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VerificationCodeTest {

    @Test
    @DisplayName("VerificationCode를 생성할 수 있다.")
    void should_create_verification_code() {
        // Given
        String code = "123456";

        // When
        VerificationCode verificationCode = new VerificationCode(code);

        // Then
        assertEquals(code, verificationCode.getValue());
    }

    @Test
    @DisplayName("6자리 숫자 코드가 아닌 경우 예외가 발생한다.")
    void should_throw_exception_for_invalid_code() {
        // Given
        String invalidCode = "12345";

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            new VerificationCode(invalidCode);
        });
    }

    @Test
    @DisplayName("generateCode 메서드를 통해 6자리 숫자 코드를 생성할 수 있다.")
    void should_generate_6_digit_code() {
        // When
        VerificationCode verificationCode = VerificationCode.generateCode();

        // Then
        String codeValue = verificationCode.getValue();
        assertEquals(6, codeValue.length());
        assertTrue(codeValue.matches("\\d{6}"));
    }

}
