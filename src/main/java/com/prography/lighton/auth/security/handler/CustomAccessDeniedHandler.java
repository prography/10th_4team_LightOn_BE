package com.prography.lighton.auth.security.handler;

import static com.prography.lighton.common.constant.JwtConstants.CONTENT_TYPE;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prography.lighton.common.utils.ApiUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(CONTENT_TYPE);
        String json = objectMapper.writeValueAsString(
                ApiUtils.error(HttpStatus.FORBIDDEN, "접근이 거부되었습니다.")
        );
        response.getWriter().write(json);
    }
}
