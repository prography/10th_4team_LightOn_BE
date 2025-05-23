package com.prography.lighton.performance.users.presentation.exception;

import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import com.prography.lighton.performance.common.domain.exception.MasterArtistCannotBeRemovedException;
import com.prography.lighton.performance.common.domain.exception.PerformanceNotApprovedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PerformanceExceptionHandler {

    @ExceptionHandler(PerformanceNotApprovedException.class)
    public ResponseEntity<ApiResult<?>> performanceNotApprovedException(
            PerformanceNotApprovedException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }

    @ExceptionHandler(MasterArtistCannotBeRemovedException.class)
    public ResponseEntity<ApiResult<?>> masterArtistCannotBeRemovedException(
            MasterArtistCannotBeRemovedException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }
}
