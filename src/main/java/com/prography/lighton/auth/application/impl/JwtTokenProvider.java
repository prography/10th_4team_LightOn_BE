package com.prography.lighton.auth.application.impl;

import com.prography.lighton.auth.application.TokenProvider;
import com.prography.lighton.auth.application.dto.TokenDTO;
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

    private final RedisTokenService redisTokenService;

    private final SecretKey key;
    private final long accessTokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;

    public JwtTokenProvider(RedisTokenService redisTokenService,
                            @Value("${security.jwt.token.secret-key}") final String secretKey,
                            @Value("${security.jwt.token.access.expire-length}") final long accessTokenValidityInMilliseconds,
                            @Value("${security.jwt.token.refresh.expire-length}") final long refreshTokenValidityInMilliseconds) {
        this.redisTokenService = redisTokenService;
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.accessTokenValidityInMilliseconds = accessTokenValidityInMilliseconds;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInMilliseconds;
    }

    @Override
    public String createAccessToken(final String payload, final String authority) {
        return createToken(payload, accessTokenValidityInMilliseconds, authority);
    }

    @Override
    public String createRefreshToken(final String payload, final String authority) {
        String refreshToken = createToken(payload, refreshTokenValidityInMilliseconds, authority);
        redisTokenService.saveRefreshToken(payload, refreshToken);
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


    @Override
    public String getPayload(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    @Override
    public String getRole(final String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get(ROLE_KEY, String.class);
    }


    @Override
    public void validateToken(final String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
        } catch (final JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException();
        }
    }

    @Override
    public TokenDTO reissueTokens(String refreshToken) {
        Claims claims = getClaims(refreshToken);

        String role = claims.get(ROLE_KEY, String.class);
        String subject = claims.getSubject();

        redisTokenService.validateRefreshToken(subject, refreshToken);

        return new TokenDTO(
                createAccessToken(subject, role),
                createRefreshToken(subject, role)
        );
    }

    @Override
    public void expireTokens(Long id) {
        redisTokenService.deleteRefreshToken(id.toString());
    }

}
