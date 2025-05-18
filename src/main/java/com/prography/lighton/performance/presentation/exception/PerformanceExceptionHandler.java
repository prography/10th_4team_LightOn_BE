package com.prography.lighton.performance.presentation.exception;

import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import com.prography.lighton.performance.domain.exeption.PerformanceNotApprovedException;
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
}
