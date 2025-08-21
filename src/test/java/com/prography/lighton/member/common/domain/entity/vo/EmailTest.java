package com.prography.lighton.member.common.domain.entity.vo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.prography.lighton.member.common.domain.exception.InvalidMemberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EmailTest {

    @Test
    @DisplayName("올바른 이메일 형식이면 Email 객체가 생성된다")
    void should_create_email_when_format_is_valid() {
        Email email = Email.of("test@example.com");
        assertEquals("test@example.com", email.getValue());
    }

    @Test
    @DisplayName("이메일이 null이면 예외가 발생한다")
    void should_throw_exception_when_email_is_null() {
        assertThrows(InvalidMemberException.class, () -> {
            Email.of(null);
        });
    }


    @Test
    @DisplayName("이메일 형식이 올바르지 않으면 예외가 발생한다")
    void should_throw_exception_when_email_format_is_invalid() {
        assertThrows(InvalidMemberException.class, () -> {
            Email.of("invalid-email");
        });
    }

    @Test
    @DisplayName("이메일이 빈 값이면 예외가 발생한다")
    void should_throw_exception_when_email_is_blank() {
        assertThrows(InvalidMemberException.class, () -> {
            Email.of("");
        });
    }

    @Test
    @DisplayName("같은 이메일 값이면 두 객체는 동등하다")
    void should_be_equal_when_emails_have_same_value() {
        Email email1 = Email.of("a@example.com");
        Email email2 = Email.of("a@example.com");

        assertEquals(email1, email2);
    }

    @Test
    @DisplayName("withdrawMasked는 이메일에 마스킹 정보를 포함하여 반환한다")
    void should_mask_email_on_withdraw() {
        Email email = Email.of("a@example.com");
        Email masked = email.withdrawMasked(1L);
        assertEquals("a@example.com_DELETED_1", masked.getValue());
    }
}
