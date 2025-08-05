package com.prography.lighton.auth.domain.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.prography.lighton.auth.application.exception.UnsupportedSocialLoginTypeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SocialLoginTypeTest {

    @Test
    @DisplayName("소셜 로그인 타입을 문자열로부터 변환할 수 있다.")
    void should_return_social_login_type_from_string() {
        // Given
        String google = "GOOGLE";
        String kakao = "KAKAO";
        String apple = "APPLE";
        String defaultType = "DEFAULT";

        // When
        SocialLoginType googleType = SocialLoginType.from(google);
        SocialLoginType kakaoType = SocialLoginType.from(kakao);
        SocialLoginType appleType = SocialLoginType.from(apple);
        SocialLoginType defaultTypeValue = SocialLoginType.from(defaultType);

        // Then
        assertEquals(SocialLoginType.GOOGLE, googleType);
        assertEquals(SocialLoginType.KAKAO, kakaoType);
        assertEquals(SocialLoginType.APPLE, appleType);
        assertEquals(SocialLoginType.DEFAULT, defaultTypeValue);
    }

    @Test
    @DisplayName("지원하지 않는 소셜 로그인 타입 문자열에 대해 예외가 발생한다.")
    void should_throw_exception_for_unsupported_social_login_type() {
        // Given
        String unsupported = "UNKNOWN";

        // When & Then
        assertThrows(UnsupportedSocialLoginTypeException.class, () -> {
            SocialLoginType.from(unsupported);
        });
    }
}
