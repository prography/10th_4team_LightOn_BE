package com.prography.lighton.performance.domain.entity.exception;

import com.prography.lighton.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class MasterArtistCannotBeRemovedException extends RuntimeException {
    private static final String message = "공연 등록 아티스트는 삭제할 수 없습니다.";

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.CONFLICT, message);
    }

    public HttpStatus status() {
        return HttpStatus.CONFLICT;
    }
}
