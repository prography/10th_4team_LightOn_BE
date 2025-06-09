package com.prography.lighton.member.presentation;

import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import com.prography.lighton.member.application.CompleteMemberProfileUseCase;
import com.prography.lighton.member.application.LoginMemberUseCase;
import com.prography.lighton.member.application.RegisterMemberUseCase;
import com.prography.lighton.member.presentation.dto.request.CompleteMemberProfileRequestDTO;
import com.prography.lighton.member.presentation.dto.request.EditMemberGenreRequestDTO;
import com.prography.lighton.member.presentation.dto.request.LoginMemberRequestDTO;
import com.prography.lighton.member.presentation.dto.request.RegisterMemberRequestDTO;
import com.prography.lighton.member.presentation.dto.response.CheckDuplicateEmailResponseDTO;
import com.prography.lighton.member.presentation.dto.response.CompleteMemberProfileResponseDTO;
import com.prography.lighton.member.presentation.dto.response.LoginMemberResponseDTO;
import com.prography.lighton.member.presentation.dto.response.RegisterMemberResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResult<RegisterMemberResponseDTO>> register(
            @RequestBody @Valid RegisterMemberRequestDTO request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiUtils.success(registerMemberUseCase.registerMember(request)));
    }

    @PostMapping("/{temporaryMemberId}/info")
    public ResponseEntity<ApiResult<CompleteMemberProfileResponseDTO>> completeMemberProfile(
            @PathVariable Long temporaryMemberId,
            @RequestBody @Valid CompleteMemberProfileRequestDTO request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiUtils.success(completeMemberProfileUseCase.completeMemberProfile(temporaryMemberId, request)));
    }

    @PostMapping("/{memberId}/genres")
    public ResponseEntity<ApiResult<?>> editMemberGenre(
            @PathVariable Long memberId,
            @RequestBody @Valid EditMemberGenreRequestDTO request) {
        completeMemberProfileUseCase.editMemberGenre(memberId, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiUtils.success());
    }

    @GetMapping("/{memberId}/genres")
    public ResponseEntity<ApiResult<GetPreferredGenreResponseDTO>> getMemberGenres(@PathVariable Long memberId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiUtils.success(managePreferredGenreUseCase.getPreferredGenre(memberId)));
    }

    @GetMapping("/duplicate-check")
    public ResponseEntity<ApiResult<CheckDuplicateEmailResponseDTO>> duplicateCheck(@RequestParam String email) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiUtils.success(registerMemberUseCase.checkEmailExists(email)));
    }

    // TODO: 추후 토큰 헤더에 담아서 응답하도록 변경
    @PostMapping("/login")
    public ResponseEntity<ApiResult<LoginMemberResponseDTO>> login(@RequestBody @Valid LoginMemberRequestDTO request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiUtils.success(loginMemberUseCase.login(request)));
    }
}
