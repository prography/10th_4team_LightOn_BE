package com.prography.lighton.performance.common.domain.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.prography.lighton.performance.common.domain.entity.vo.Payment;
import com.prography.lighton.performance.common.domain.exception.InvalidPaymentInfoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PaymentTest {

    @Test
    @DisplayName("Payment 객체를 정상적으로 생성할 수 있다.")
    void should_create_paid_payment() {
        Payment payment = Payment.of(true, "1234-5678", "카카오뱅크", "홍길동", 10000);

        assertTrue(payment.getIsPaid());
        assertEquals("1234-5678", payment.getAccount());
        assertEquals("카카오뱅크", payment.getBank());
        assertEquals("홍길동", payment.getAccountHolder());
        assertEquals(10000, payment.getFee());
    }

    @Test
    @DisplayName("Payment 생성 시 기본값은 유료 형식이어야한다.")
    void should_be_paid_by_default_when_created_with_of() {
        Payment payment = Payment.of(true, "1234-5678", "은행", "예금주", 5000);
        assertTrue(payment.getIsPaid());
    }

    @Test
    @DisplayName("계좌가 빈 값이면 에러가 발생한다.")
    void should_throw_exception_when_account_is_blank() {
        assertThrows(InvalidPaymentInfoException.class, () ->
                Payment.of(true, "", "은행", "예금주", 1000)
        );
        assertThrows(InvalidPaymentInfoException.class, () ->
                Payment.of(true, " ", "은행", "예금주", 1000)
        );
    }

    @Test
    @DisplayName("은행이 빈 값이면 에러가 발생한다.")
    void should_throw_exception_when_bank_is_blank() {
        assertThrows(InvalidPaymentInfoException.class, () ->
                Payment.of(true, "계좌", "", "예금주", 1000)
        );
        assertThrows(InvalidPaymentInfoException.class, () ->
                Payment.of(true, "계좌", " ", "예금주", 1000)
        );
    }

    @Test
    @DisplayName("예금주가 빈 값이면 에러가 발생한다.")
    void should_throw_exception_when_account_holder_is_blank() {
        assertThrows(InvalidPaymentInfoException.class, () ->
                Payment.of(true, "계좌", "은행", "", 1000)
        );
        assertThrows(InvalidPaymentInfoException.class, () ->
                Payment.of(true, "계좌", "은행", " ", 1000)
        );
    }

    @Test
    @DisplayName("금액이 0 이하이면 에러가 발생한다.")
    void should_throw_exception_when_fee_is_zero_or_negative() {
        assertThrows(InvalidPaymentInfoException.class, () ->
                Payment.of(true, "계좌", "은행", "예금주", 0)
        );
        assertThrows(InvalidPaymentInfoException.class, () ->
                Payment.of(true, "계좌", "은행", "예금주", -100)
        );
    }

    @Test
    @DisplayName("금액이 null이면 에러가 발생한다.")
    void should_throw_exception_when_fee_is_null() {
        assertThrows(InvalidPaymentInfoException.class, () ->
                Payment.of(true, "계좌", "은행", "예금주", null)
        );
    }

    @Test
    @DisplayName("무료 Payment 객체를 정상적으로 생성할 수 있다.")
    void should_create_free_payment() {
        Payment freePayment = Payment.free();

        assertFalse(freePayment.getIsPaid());
        assertNull(freePayment.getAccount());
        assertNull(freePayment.getBank());
        assertNull(freePayment.getAccountHolder());
        assertEquals(0, freePayment.getFee());
    }
}
