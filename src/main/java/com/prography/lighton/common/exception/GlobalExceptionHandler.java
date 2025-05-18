package com.prography.lighton.common.exception;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.prography.lighton.common.exception.base.NotFoundException;
import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.member.exception.InvalidMemberException;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.status(e.status()).body(ApiUtils.error(e.status(), e.getMessage()));
    }

    @ExceptionHandler({
            InvalidMemberException.class
    })
    public ResponseEntity<?> handleInvalidMemberException(InvalidMemberException e) {
        return ResponseEntity.status(e.status()).body(ApiUtils.error(e.status(), e.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiUtils.error(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiUtils.error(HttpStatus.BAD_REQUEST, e.getFieldError().getDefaultMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiUtils.ApiResult<Void> handleEnumBindingError(HttpMessageNotReadableException ex) {
        if (ex.getCause() instanceof InvalidFormatException e) {
            String field = e.getPath().stream()
                    .map(Reference::getFieldName)
                    .collect(Collectors.joining("."));

            String message = String.format("'%s'은(는) 필드 '%s'에 허용되지 않는 값입니다.", e.getValue(), field);
            return ApiUtils.error(HttpStatus.BAD_REQUEST, message);
        }

        return ApiUtils.error(HttpStatus.BAD_REQUEST, "요청 데이터를 읽을 수 없습니다.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다."));
    }
}
