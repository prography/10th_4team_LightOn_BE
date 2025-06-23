package com.prography.lighton.performance.admin.presentation.exception;

import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import com.prography.lighton.performance.admin.application.exception.PerformanceAlreadyProcessedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AdminPerformanceExceptionHandler {

    @ExceptionHandler(PerformanceAlreadyProcessedException.class)
    public ResponseEntity<ApiResult<?>> performanceAlreadyProcessedException(
            PerformanceAlreadyProcessedException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }
}
