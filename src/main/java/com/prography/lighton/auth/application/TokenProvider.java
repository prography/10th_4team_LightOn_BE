package com.prography.lighton.auth.application;


import com.prography.lighton.auth.application.dto.TokenDTO;
import io.jsonwebtoken.Claims;

public interface TokenProvider {

    String createAccessToken(final String payload, final String authority);

    String createRefreshToken(final String payload, final String authority);

    Claims getClaims(final String token);

    String getPayload(final String token);

    String getRole(final String token);

    void validateToken(final String token);

    TokenDTO reissueTokens(final String refreshToken);

    void expireTokens(final Long id);
}
