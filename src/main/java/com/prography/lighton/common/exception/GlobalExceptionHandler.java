package com.prography.lighton.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.prography.lighton.common.exception.base.NotFoundException;
import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import com.prography.lighton.region.exception.NoSuchRegionException;
import com.prography.lighton.member.exception.InvalidMemberException;
import com.prography.lighton.member.exception.NoSuchMemberException;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@ExceptionHandler(NotFoundException.class)
	public ApiResult<?> handleNotFoundException(NotFoundException e) {
		return ApiUtils.error(e.status(), e.getMessage());
	}

	@ExceptionHandler({
			InvalidMemberException.class
	})
	public ApiResult<?> handleInvalidMemberException(InvalidMemberException e) {
		return ApiUtils.error(e.status(), e.getMessage());
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ApiResult<?> handleIllegalArgumentException(IllegalArgumentException e) {
		return ApiUtils.error(HttpStatus.BAD_REQUEST, e.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ApiResult<?> handleException(Exception e) {
		logger.error(e.getMessage(), e);
		return ApiUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.");
	}
}
