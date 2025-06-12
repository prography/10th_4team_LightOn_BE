package com.prography.lighton.auth.application;


import io.jsonwebtoken.Claims;

public interface TokenProvider {

    String createAccessToken(String payload, String authority);

    String createRefreshToken(String payload, String authority);

    Claims getClaims(String token);

}
