package com.prography.lighton.artist.presentation.exception;

import com.prography.lighton.artist.domain.entity.exception.ArtistRegistrationNotAllowedException;
import com.prography.lighton.common.utils.ApiUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ArtistExceptionHandler {

    @ExceptionHandler(ArtistRegistrationNotAllowedException.class)
    public ResponseEntity<ApiUtils.ApiResult<?>> artistRegistrationNotAllowedException(
            ArtistRegistrationNotAllowedException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }
}
