package com.prography.lighton.member.presentation;

import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;

@RestController
@RequestMapping("/api/users")
public class MemberController {

	@PostMapping
	public ApiResult<?> signUp() {
		return ApiUtils.success();
	}

	@PostMapping("/login")
	public ApiResult<?> login() {
		return ApiUtils.success();
	}

	@GetMapping("/duplicate-check")
	public ApiResult<?> duplicateCheck(@RequestParam String email) {
		return ApiUtils.success();
	}

	@PostMapping("/info")
	public ApiResult<?> updateUserInfo() {
		return ApiUtils.success();
	}
}
