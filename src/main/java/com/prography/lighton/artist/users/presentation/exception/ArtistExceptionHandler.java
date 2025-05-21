package com.prography.lighton.artist.users.presentation.exception;

import com.prography.lighton.artist.users.domain.entity.exception.ArtistNotApprovedException;
import com.prography.lighton.artist.users.domain.entity.exception.ArtistRegistrationNotAllowedException;
import com.prography.lighton.artist.users.domain.entity.exception.NotAMasterArtistException;
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

    @ExceptionHandler(ArtistNotApprovedException.class)
    public ResponseEntity<ApiUtils.ApiResult<?>> artistUpdateNotAllowedException(
            ArtistNotApprovedException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }

    @ExceptionHandler(NotAMasterArtistException.class)
    public ResponseEntity<ApiUtils.ApiResult<?>> notAMasterArtistException(
            NotAMasterArtistException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }
}
