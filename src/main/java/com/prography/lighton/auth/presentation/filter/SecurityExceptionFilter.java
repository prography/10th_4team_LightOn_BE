package com.prography.lighton.auth.presentation.filter;


import static com.prography.lighton.common.constant.JwtConstants.CONTENT_TYPE;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prography.lighton.auth.exception.InvalidTokenException;
import com.prography.lighton.common.utils.ApiUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
@Slf4j
public class SecurityExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response); // 다음 필터 실행
        } catch (InvalidTokenException ex) {
            log.warn("Security Error: {}", ex.getMessage());
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (SecurityException ex) {
            log.warn("Security-related error: {}", ex.getMessage());
            sendErrorResponse(response, HttpStatus.FORBIDDEN, "접근이 거부되었습니다.");
        }
    }

    private void sendErrorResponse(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType(CONTENT_TYPE);
        String json = objectMapper.writeValueAsString(ApiUtils.error(status, message));
        response.getWriter().write(json);
    }
}
