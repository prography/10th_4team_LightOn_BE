package com.prography.lighton.artist.admin.presentation.exception;

import com.prography.lighton.artist.admin.domain.exception.InvalidApproveStatusTransitionException;
import com.prography.lighton.artist.admin.domain.exception.SameApproveStatusException;
import com.prography.lighton.common.utils.ApiUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ArtistExceptionHandler {

    @ExceptionHandler(InvalidApproveStatusTransitionException.class)
    public ApiUtils.ApiResult<?> handleInvalidApproveStatusTransitionException(
            InvalidApproveStatusTransitionException exception) {
        return ApiUtils.error(exception.status(), exception.getMessage());
    }

    @ExceptionHandler(SameApproveStatusException.class)
    public ApiUtils.ApiResult<?> handleSameApproveStatusException(SameApproveStatusException exception) {
        return ApiUtils.error(exception.status(), exception.getMessage());
    }
}
