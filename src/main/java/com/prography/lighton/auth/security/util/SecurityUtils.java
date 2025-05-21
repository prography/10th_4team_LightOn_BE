package com.prography.lighton.auth.security.util;

import com.prography.lighton.auth.security.exception.UnauthorizedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static Long getCurrentMemberId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("인증되지 않은 요청입니다.");
        }

        return Long.valueOf(authentication.getName()); // ID가 name 필드에 들어 있다고 가정
    }
}
