package com.prography.lighton.auth.presentation.exception;

import com.prography.lighton.auth.application.exception.IdTokenParseException;
import com.prography.lighton.auth.application.exception.MemberProfileIncompleteException;
import com.prography.lighton.auth.application.exception.PhoneNotVerifiedException;
import com.prography.lighton.auth.application.exception.PhoneVerificationFailedException;
import com.prography.lighton.auth.application.exception.UnsupportedSocialLoginTypeException;
import com.prography.lighton.auth.infrastructure.sms.exception.SmsSendFailedException;
import com.prography.lighton.auth.security.exception.ForbiddenException;
import com.prography.lighton.auth.security.exception.UnauthorizedException;
import com.prography.lighton.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> handleUnauthorizedException(UnauthorizedException e) {
        return ResponseEntity.status(e.status()).body(ApiUtils.error(e.status(), e.getMessage()));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<?> handleForbiddenException(ForbiddenException e) {
        return ResponseEntity.status(e.status()).body(ApiUtils.error(e.status(), e.getMessage()));
    }

    @ExceptionHandler(MemberProfileIncompleteException.class)
    public ResponseEntity<?> handleMemberProfileIncompleteException(MemberProfileIncompleteException e) {
        return ResponseEntity.status(e.status()).body(ApiUtils.error(e.status(), e.getMessage()));
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<?> handleUnsupportedOperationException(UnsupportedOperationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiUtils.error(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(UnsupportedSocialLoginTypeException.class)
    public ResponseEntity<?> handleUnsupportedSocialLoginTypeException(UnsupportedSocialLoginTypeException e) {
        return ResponseEntity.status(e.status()).body(ApiUtils.error(e.status(), e.getMessage()));
    }

    @ExceptionHandler(IdTokenParseException.class)
    public ResponseEntity<?> handleIdTokenParseException(IdTokenParseException e) {
        return ResponseEntity.status(e.status()).body(ApiUtils.error(e.status(), e.getMessage()));
    }

    @ExceptionHandler(SmsSendFailedException.class)
    public ResponseEntity<ApiUtils.ApiResult<?>> smsSendFailedException(SmsSendFailedException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }

    @ExceptionHandler(PhoneVerificationFailedException.class)
    public ResponseEntity<ApiUtils.ApiResult<?>> phoneVerificationFailedException(
            PhoneVerificationFailedException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }

    @ExceptionHandler(PhoneNotVerifiedException.class)
    public ResponseEntity<ApiUtils.ApiResult<?>> phoneNotVerifiedException(
            PhoneNotVerifiedException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }

}
