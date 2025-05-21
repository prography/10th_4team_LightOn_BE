package com.prography.lighton.artist.domain.entity.exception;

import com.prography.lighton.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class ArtistNotApprovedException extends RuntimeException {
    private final String message = "승인되지 않은 아티스트입니다.";

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.CONFLICT, message);
    }

    public HttpStatus status() {
        return HttpStatus.CONFLICT;
    }
}
