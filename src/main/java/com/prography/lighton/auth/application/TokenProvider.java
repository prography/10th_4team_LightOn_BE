package com.prography.lighton.auth.application;


import io.jsonwebtoken.Claims;

public interface TokenProvider {

    String createAccessToken(final String payload, final String authority);

    String createRefreshToken(final String payload, final String authority);

    Claims getClaims(final String token);

}
