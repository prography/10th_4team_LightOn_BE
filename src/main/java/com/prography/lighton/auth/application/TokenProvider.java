package com.prography.lighton.auth.application;


import com.prography.lighton.member.common.domain.entity.enums.Authority;
import io.jsonwebtoken.Claims;

public interface TokenProvider {

    String createAccessToken(final String payload, final Authority authority);

    String createRefreshToken(final String payload, final Authority authority);

    Claims getClaims(final String token);

    String getPayload(final String token);

    String getRole(final String token);

    void validateToken(final String token);
}
