package com.prography.lighton.common.constant;

public final class SecurityWhitelist {

    // 정확히 일치해야 하는 경로들
    public static final String[] EXACT_MATCH = {
            "/health",
            "/api/members",
            "/api/auth/login",
            "/api/members/duplicate-check",
            "/swagger-ui.html",
            "/api/auth/token/refresh",
    };

    // 접두사로 매칭되는 경로들
    public static final String[] PREFIX_MATCH = {
            "/swagger-ui",
            "/v3/api-docs",
            "/docs",
            "/swagger-resources",
            "/webjars",
            "/api/oauth"
    };

    // 정규식으로 매칭되는 경로들
    public static final String[] REGEX_MATCH = {
            "^/api/members/\\d+/info$"
    };

    private SecurityWhitelist() {
    }
}
