package com.prography.lighton.artist.users.domain.entity.exception;

import com.prography.lighton.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class ArtistRegistrationNotAllowedException extends RuntimeException {
    private final String message = "이미 인증된 아티스트이거나 승인 대기/승인 상태입니다.";

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.CONFLICT, message);
    }

    public HttpStatus status() {
        return HttpStatus.CONFLICT;
    }
}
