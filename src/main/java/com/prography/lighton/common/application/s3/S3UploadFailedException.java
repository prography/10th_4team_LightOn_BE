package com.prography.lighton.common.application.s3;

import com.prography.lighton.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class S3UploadFailedException extends RuntimeException {

    private static final String MESSAGE = "S3 업로드 실패";

    public S3UploadFailedException(final String message) {
        super(message);
    }

    public S3UploadFailedException() {
        this(MESSAGE);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
