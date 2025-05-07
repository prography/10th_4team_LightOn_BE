package com.prography.lighton.common.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;

@RestController
public class BasicController {

	@GetMapping("/health")
	public ApiResult<String> healthCheck() {
		return ApiUtils.success("ok");
	}
}
