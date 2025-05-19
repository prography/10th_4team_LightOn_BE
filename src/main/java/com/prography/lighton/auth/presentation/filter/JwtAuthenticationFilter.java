package com.prography.lighton.auth.presentation.filter;

import static com.prography.lighton.common.constant.JwtConstants.AUTHORIZATION_HEADER;
import static com.prography.lighton.common.constant.JwtConstants.BEARER_PREFIX;
import static com.prography.lighton.common.constant.JwtConstants.CONTENT_TYPE;
import static com.prography.lighton.common.constant.JwtConstants.ROLE_PREFIX;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prography.lighton.auth.application.TokenProvider;
import com.prography.lighton.auth.exception.InvalidTokenException;
import com.prography.lighton.common.utils.ApiUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (isPermitAllRequest(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            authenticateRequest(request);
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            log.error("JWT authentication failed: {}", e.getMessage());
            sendUnauthorizedResponse(response, e.getMessage());
        }
    }

    private void authenticateRequest(HttpServletRequest request) {
        String token = extractToken(request);
        tokenProvider.validateToken(token);

        String memberId = tokenProvider.getPayload(token);
        String role = tokenProvider.getRole(token);

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

    private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(CONTENT_TYPE);

        String json = objectMapper.writeValueAsString(

                ApiUtils.error(HttpStatus.UNAUTHORIZED, message)
        );

        response.getWriter().write(json);
    }

    private boolean isPermitAllRequest(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.equals("/health") ||
                uri.equals("/api/members") ||
                uri.equals("/api/members/login") ||
                uri.matches("^/api/members/\\d+/info$");
    }
}
