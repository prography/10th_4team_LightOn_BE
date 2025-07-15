package com.prography.lighton.common.application.s3;

import com.prography.lighton.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class ImageUploadFailedException extends RuntimeException {

    private static final String MESSAGE = "이미지 업로드 실패";

    public ImageUploadFailedException(final String message) {
        super(message);
    }

    public ImageUploadFailedException() {
        this(MESSAGE);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
