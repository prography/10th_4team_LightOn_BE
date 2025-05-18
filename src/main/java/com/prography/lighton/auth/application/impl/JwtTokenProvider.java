package com.prography.lighton.auth.application.impl;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.prography.lighton.auth.application.TokenProvider;
import com.prography.lighton.auth.exception.InvalidTokenException;
import com.prography.lighton.member.domain.entity.enums.Authority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider implements TokenProvider {

	private final static String ROLE_KEY = "roles";

	private final SecretKey key;
	private final long accessTokenValidityInMilliseconds;
	private final long refreshTokenValidityInMilliseconds;

	public JwtTokenProvider(@Value("${security.jwt.token.secret-key}") final String secretKey,
			@Value("${security.jwt.token.access.expire-length}") final long accessTokenValidityInMilliseconds,
			@Value("${security.jwt.token.refresh.expire-length}") final long refreshTokenValidityInMilliseconds) {
		this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
		this.accessTokenValidityInMilliseconds = accessTokenValidityInMilliseconds;
		this.refreshTokenValidityInMilliseconds = refreshTokenValidityInMilliseconds;
	}

	@Override
	public String createAccessToken(final String payload, final Authority authority) {
		return createToken(payload, accessTokenValidityInMilliseconds, authority.toString());
	}

	@Override
	public String createRefreshToken(final String payload, final Authority authority) {
		return createToken(payload, refreshTokenValidityInMilliseconds, authority.toString());
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
	public String getRole(String token) {
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

}
