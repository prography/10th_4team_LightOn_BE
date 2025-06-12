package com.prography.lighton.auth.application.impl.token;

import com.prography.lighton.auth.application.RefreshTokenService;
import com.prography.lighton.auth.application.TokenProvider;
import com.prography.lighton.auth.application.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtTokenProvider implements TokenProvider {

    private final static String ROLE_KEY = "roles";

    private final RefreshTokenService refreshTokenService;

    private final SecretKey key;
    private final long accessTokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;

    public JwtTokenProvider(RefreshTokenService refreshTokenService,
                            @Value("${security.jwt.token.secret-key}") final String secretKey,
                            @Value("${security.jwt.token.access.expire-length}") final long accessTokenValidityInMilliseconds,
                            @Value("${security.jwt.token.refresh.expire-length}") final long refreshTokenValidityInMilliseconds) {
        this.refreshTokenService = refreshTokenService;
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.accessTokenValidityInMilliseconds = accessTokenValidityInMilliseconds;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInMilliseconds;
    }

    @Override
    public String createAccessToken(String payload, String authority) {
        return createToken(payload, accessTokenValidityInMilliseconds, authority);
    }

    @Override
    public String createRefreshToken(String payload, String authority) {
        String refreshToken = createToken(payload, refreshTokenValidityInMilliseconds, authority);
        refreshTokenService.saveRefreshToken(payload, refreshToken);
        return refreshToken;
    }

    @Override
    public Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException();
        }
    }


    private String createToken(String payload, Long validityInMilliseconds, String roles) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setSubject(payload)
                .claim(ROLE_KEY, roles)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
