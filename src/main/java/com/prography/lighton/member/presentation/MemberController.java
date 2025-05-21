package com.prography.lighton.member.presentation;

import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import com.prography.lighton.member.application.member.CompleteMemberProfileUseCase;
import com.prography.lighton.member.application.member.LoginMemberUseCase;
import com.prography.lighton.member.application.member.RegisterMemberUseCase;
import com.prography.lighton.member.presentation.dto.request.CompleteMemberProfileRequestDTO;
import com.prography.lighton.member.presentation.dto.request.LoginMemberRequestDTO;
import com.prography.lighton.member.presentation.dto.request.RegisterMemberRequestDTO;
import com.prography.lighton.member.presentation.dto.response.CompleteMemberProfileResponseDTO;
import com.prography.lighton.member.presentation.dto.response.LoginMemberResponseDTO;
import com.prography.lighton.member.presentation.dto.response.RegisterMemberResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final RegisterMemberUseCase registerMemberUseCase;
    private final CompleteMemberProfileUseCase completeMemberProfileUseCase;
    private final LoginMemberUseCase loginMemberUseCase;

    @PostMapping
    public ApiResult<RegisterMemberResponseDTO> register(@RequestBody @Valid RegisterMemberRequestDTO request) {
        return ApiUtils.success(registerMemberUseCase.registerMember(request));
    }

    @PostMapping("/{temporaryMemberId}/info")
    public ApiResult<CompleteMemberProfileResponseDTO> completeMemberProfile(@PathVariable Long temporaryMemberId,
                                                                             @RequestBody @Valid CompleteMemberProfileRequestDTO request) {
        return ApiUtils.success(completeMemberProfileUseCase.completeMemberProfile(temporaryMemberId, request));
    }

    @GetMapping("/duplicate-check")
    public ApiResult<?> duplicateCheck(@RequestParam String email) {
        return ApiUtils.success();
    }

    // TODO: 추후 토큰 헤더에 담아서 응답하도록 변경
    @PostMapping("/login")
    public ApiResult<LoginMemberResponseDTO> login(@RequestBody @Valid LoginMemberRequestDTO request) {
        return ApiUtils.success(loginMemberUseCase.login(request));
    }
}
