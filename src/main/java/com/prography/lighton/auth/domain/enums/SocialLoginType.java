package com.prography.lighton.auth.domain.enums;

import com.prography.lighton.auth.application.exception.UnsupportedSocialLoginTypeException;

public enum SocialLoginType {
    GOOGLE,
    KAKAO,
    APPLE,
    DEFAULT;

    public static SocialLoginType from(String value) {
        try {
            return SocialLoginType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UnsupportedSocialLoginTypeException("지원하지 않는 소셜 로그인 타입입니다.");
        }
    }
}
