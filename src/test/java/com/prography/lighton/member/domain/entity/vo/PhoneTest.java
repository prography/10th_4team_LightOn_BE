package com.prography.lighton.member.domain.entity.vo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.prography.lighton.member.common.domain.entity.vo.Phone;
import com.prography.lighton.member.common.domain.exception.InvalidMemberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PhoneTest {

    @Test
    @DisplayName("올바른 전화번호 형식이면 Phone 객체가 생성된다")
    void should_create_phone_when_format_is_valid() {
        Phone phone = Phone.of("01012341234");
        assertEquals("01012341234", phone.getValue());
    }

    @Test
    @DisplayName("전화번호 형식이 올바르지 않으면 예외가 발생한다")
    void should_throw_exception_when_phone_format_is_invalid() {
        assertThrows(InvalidMemberException.class, () -> {
            Phone.of("010111a");
        });
    }

    @Test
    @DisplayName("전화번호가 빈 값이면 예외가 발생한다")
    void should_throw_exception_when_phone_is_blank() {
        assertThrows(InvalidMemberException.class, () -> {
            Phone.of("");
        });
    }

    @Test
    @DisplayName("전화번호가 null이면 예외가 발생한다")
    void should_throw_exception_when_phone_is_null() {
        assertThrows(InvalidMemberException.class, () -> {
            Phone.of(null);
        });
    }

    @Test
    @DisplayName("같은 전화번호 값이면 두 객체는 동등하다")
    void should_be_equal_when_phones_have_same_value() {
        Phone phone1 = Phone.of("01012341234");
        Phone phone2 = Phone.of("01012341234");

        assertEquals(phone1, phone2);
    }

    @Test
    @DisplayName("withdrawMasked는 전화번호에 마스킹 정보를 포함하여 반환한다")
    void should_mask_phone_number_on_withdraw() {
        Phone phone = Phone.of("01012341234");
        Phone masked = phone.withdrawMasked(1L);
        assertEquals("01012341234_DELETED_1", masked.getValue());
    }
}
