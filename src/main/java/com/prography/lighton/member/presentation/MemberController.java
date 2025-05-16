package com.prography.lighton.member.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import com.prography.lighton.member.presentation.dto.request.SignUpMemberRequestDTO;
import com.prography.lighton.member.presentation.dto.response.SignUpMemberResponseDTO;

@RestController
@RequestMapping("/api/users")
public class MemberController {

	@PostMapping
	public ApiResult<SignUpMemberResponseDTO> register(SignUpMemberRequestDTO request) {
		return ApiUtils.success(null);
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
