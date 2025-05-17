package com.prography.lighton.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import com.prography.lighton.location.exception.NoSuchRegionException;
import com.prography.lighton.member.exception.InvalidMemberException;
import com.prography.lighton.member.exception.NoSuchMemberException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({
			NoSuchMemberException.class,
			NoSuchRegionException.class
	})
	public ApiResult<?> handleNotFoundException(RuntimeException e) {
		return ApiUtils.error(HttpStatus.NOT_FOUND, e.getMessage());
	}

	@ExceptionHandler({
			InvalidMemberException.class
	})
	public ApiResult<?> handleInvalidMemberException(InvalidMemberException e) {
		return ApiUtils.error(HttpStatus.BAD_REQUEST, e.getMessage());
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ApiResult<?> handleIllegalArgumentException(IllegalArgumentException e) {
		return ApiUtils.error(HttpStatus.BAD_REQUEST, e.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ApiResult<?> handleException(Exception e) {
		return ApiUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.");
	}
}
