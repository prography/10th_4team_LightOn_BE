package com.prography.lighton.auth.presentation.exception;

import com.prography.lighton.auth.application.exception.MemberProfileIncompleteException;
import com.prography.lighton.common.utils.ApiUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(MemberProfileIncompleteException.class)
    public ResponseEntity<?> handleMemberProfileIncompleteException(MemberProfileIncompleteException e) {
        return ResponseEntity.status(e.status()).body(ApiUtils.error(e.status(), e.getMessage()));
    }
}
