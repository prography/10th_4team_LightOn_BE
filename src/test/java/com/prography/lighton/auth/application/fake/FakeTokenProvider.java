package com.prography.lighton.auth.application.fake;

import com.prography.lighton.auth.application.port.TokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import java.util.HashMap;
import java.util.Map;

public class FakeTokenProvider implements TokenProvider {

    private final Map<String, Claims> fakeClaimsStore = new HashMap<>();

    @Override
    public String createAccessToken(String payload, String authority) {
        String token = "fake-access-token-" + payload;
        storeClaims(token, payload, authority);
        return token;
    }

    @Override
    public String createRefreshToken(String payload, String authority) {
        String token = "fake-refresh-token-" + payload;
        storeClaims(token, payload, authority);
        return token;
    }

    @Override
    public Claims getClaims(String token) {
        Claims claims = fakeClaimsStore.get(token);
        if (claims == null) {
            throw new IllegalArgumentException("토큰 없음: " + token);
        }
        return claims;
    }

    public String getSubject(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    private void storeClaims(String token, String subject, String role) {
        Claims claims = new DefaultClaims();
        claims.setSubject(subject);
        claims.put("roles", role);
        fakeClaimsStore.put(token, claims);
    }
}