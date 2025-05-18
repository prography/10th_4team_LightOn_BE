package com.prography.lighton.artist.domain.entity.exception;

import com.prography.lighton.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class NotAMasterArtistException extends RuntimeException {
    private final String message = "공연 등록 아티스트가 아닙입니다.";

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.CONFLICT, message);
    }

    public HttpStatus status() {
        return HttpStatus.CONFLICT;
    }
}
