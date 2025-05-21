package com.prography.lighton.artist.common.domain.entity.exception;

import com.prography.lighton.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class NotAMasterArtistException extends RuntimeException {
    private final String message = "공연을 등록한 아티스트가 아닙니다.";

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.FORBIDDEN, message);
    }

    public HttpStatus status() {
        return HttpStatus.FORBIDDEN;
    }
}
