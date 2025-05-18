package com.prography.lighton.auth.application;

import java.util.List;

import com.prography.lighton.member.domain.entity.enums.Authority;

public interface TokenProvider {

	String createAccessToken(final String payload, final Authority authority);

	String createRefreshToken(final String payload, final Authority authority);

	String getPayload(final String token);

	String getRole(final String token);

	void validateToken(final String token);
}
