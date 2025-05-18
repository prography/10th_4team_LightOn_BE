package com.prography.lighton.auth.presentation.filter;

import com.prography.lighton.auth.application.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final TokenProvider tokenProvider;

	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String BEARER_PREFIX = "Bearer ";
	private static final String ROLE_PREFIX = "ROLE_";

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {

		String token = resolveToken(request);
		if (token != null) {
			try {
				tokenProvider.validateToken(token); // 토큰 유효성 검증

				String memberId = tokenProvider.getPayload(token); // subject (예: memberId)
				String role = tokenProvider.getRole(token);        // 단일 role (예: "ADMIN")

				var authority = new org.springframework.security.core.authority.SimpleGrantedAuthority(ROLE_PREFIX + role);
				var authorities = List.of(authority);

				var authentication = new UsernamePasswordAuthenticationToken(
						memberId,  // principal
						null,      // credentials
						authorities
				);

				authentication.setDetails(
						new WebAuthenticationDetailsSource().buildDetails(request)
				);

				SecurityContextHolder.getContext().setAuthentication(authentication);

			} catch (Exception e) {
				log.error("JWT authentication failed: {}", e.getMessage());
				SecurityContextHolder.clearContext();
			}
		}

		filterChain.doFilter(request, response);
	}

	private String resolveToken(HttpServletRequest request) {
		String bearer = request.getHeader(AUTHORIZATION_HEADER);
		if (StringUtils.hasText(bearer) && bearer.startsWith(BEARER_PREFIX)) {
			return bearer.substring(BEARER_PREFIX.length());
		}
		return null;
	}
}

