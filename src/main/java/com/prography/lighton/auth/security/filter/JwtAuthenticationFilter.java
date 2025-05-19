package com.prography.lighton.auth.security.filter;

import static com.prography.lighton.common.constant.JwtConstants.AUTHORIZATION_HEADER;
import static com.prography.lighton.common.constant.JwtConstants.BEARER_PREFIX;
import static com.prography.lighton.common.constant.JwtConstants.ROLE_PREFIX;

import com.prography.lighton.auth.application.TokenProvider;
import com.prography.lighton.auth.application.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (isPermitAllRequest(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        authenticateRequest(request);
        filterChain.doFilter(request, response);

    }

    private void authenticateRequest(HttpServletRequest request) {
        Claims claims = tokenProvider.getClaims(extractToken(request));
        String memberId = claims.getSubject();
        String role = claims.get("roles", String.class);

        validateRole(role);

        var authority = new SimpleGrantedAuthority(ROLE_PREFIX + role);
        var authentication = new UsernamePasswordAuthenticationToken(memberId, null, List.of(authority));
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String extractToken(HttpServletRequest request) {
        String bearer = request.getHeader(AUTHORIZATION_HEADER);
        if (!StringUtils.hasText(bearer)) {
            throw new InvalidTokenException("Authorization 헤더가 없습니다.");
        }
        if (!bearer.startsWith(BEARER_PREFIX)) {
            throw new InvalidTokenException("토큰 형식이 잘못되었습니다.");
        }
        String token = bearer.substring(BEARER_PREFIX.length());
        if (!StringUtils.hasText(token)) {
            throw new InvalidTokenException("토큰이 비어있습니다.");
        }
        return token;
    }

    private void validateRole(String role) {
        if (!StringUtils.hasText(role)) {
            throw new InvalidTokenException("사용자 역할 정보가 없습니다.");
        }
    }

    private boolean isPermitAllRequest(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.equals("/health") ||
                uri.equals("/api/members") ||
                uri.equals("/api/members/login") ||
                uri.matches("^/api/members/\\d+/info$");
    }
}
