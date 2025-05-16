package com.prography.lighton.member.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import com.prography.lighton.member.application.RegisterMemberUseCase;
import com.prography.lighton.member.presentation.dto.request.RegisterMemberRequestDTO;
import com.prography.lighton.member.presentation.dto.response.RegisterMemberResponseDTO;

@RestController
@RequestMapping("/api/users")
public class MemberController {

	private final RegisterMemberUseCase registerMemberUseCase;

	public MemberController(RegisterMemberUseCase registerMemberUseCase) {
		this.registerMemberUseCase = registerMemberUseCase;
	}

	@PostMapping
	public ApiResult<RegisterMemberResponseDTO> register(@RequestBody RegisterMemberRequestDTO request) {
		return ApiUtils.success(registerMemberUseCase.registerMember(request));
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
