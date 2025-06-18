package com.prography.lighton.auth.presentation.dto.apple;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AppleIdTokenPayload(
        String email,
        @JsonProperty("email_verified") String emailVerified,
        @JsonProperty("is_private_email") String isPrivateEmail,
        @JsonProperty("auth_time") Long authTime,
        @JsonProperty("nonce_supported") Boolean nonceSupported,
        String iss,
        String sub,
        String aud,
        String iat,
        String exp,
        String at_hash
) {
}