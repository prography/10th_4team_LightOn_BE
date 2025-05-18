package com.prography.lighton.common.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import com.prography.lighton.common.annotation.LoginMember;
import com.prography.lighton.member.domain.entity.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class BasicController {

	@GetMapping("/health")
	public ApiResult<String> healthCheck() {
		return ApiUtils.success("ok");
	}

	@GetMapping("/api/me")
	public ResponseEntity<ApiResult<String>> me(@LoginMember Member member) {
		log.info(String.valueOf(member.getAuthority()));
		log.info("memberId: {}", member.getId());
		return ResponseEntity.ok(ApiUtils.success("ok"));
	}
}
