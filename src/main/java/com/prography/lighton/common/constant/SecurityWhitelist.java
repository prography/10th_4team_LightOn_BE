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
            "/api/auth/phones/code",
            "/api/auth/phones/code/verify",
            "/api/members/performances",
            "/api/members/performances/nearby",
            "/api/members/performances/popular",
            "/api/members/performances/recent",
            "/api/members/advertisements",
            "/api/members/performances/trending"
    };

    // 접두사로 매칭되는 경로들
    public static final String[] PREFIX_MATCH = {
            "/swagger-ui",
            "/v3/api-docs",
            "/docs",
            "/swagger-resources",
            "/webjars",
            "/api/oauth",
            "/api/members/announcements"
    };

    // 정규식으로 매칭되는 경로들
    public static final String[] REGEX_MATCH = {
            "^/api/members/\\d+/info$",
            "^/api/members/performances/\\d+",
            "^/api/members/performances/\\d+/payment$"
    };

    private SecurityWhitelist() {
    }
}
