package com.prography.lighton.member.users.presentation;

import com.prography.lighton.common.annotation.LoginMember;
import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.member.users.application.UserMemberCommandService;
import com.prography.lighton.member.users.application.UserMemberQueryService;
import com.prography.lighton.member.users.presentation.dto.request.CompleteMemberProfileRequest;
import com.prography.lighton.member.users.presentation.dto.request.EditMemberGenreRequest;
import com.prography.lighton.member.users.presentation.dto.request.RegisterMemberRequest;
import com.prography.lighton.member.users.presentation.dto.response.CheckDuplicateEmailResponse;
import com.prography.lighton.member.users.presentation.dto.response.CompleteMemberProfileResponse;
import com.prography.lighton.member.users.presentation.dto.response.GetPreferredGenreResponse;
import com.prography.lighton.member.users.presentation.dto.response.RegisterMemberResponse;
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
public class UserMemberController {

    private final UserMemberCommandService userMemberCommandService;
    private final UserMemberQueryService userMemberQueryService;

    @PostMapping
    public ResponseEntity<ApiResult<RegisterMemberResponse>> register(
            @RequestBody @Valid RegisterMemberRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiUtils.success(userMemberCommandService.registerMember(request)));
    }

    @PostMapping("/{temporaryMemberId}/info")
    public ResponseEntity<ApiResult<CompleteMemberProfileResponse>> completeMemberProfile(
            @PathVariable Long temporaryMemberId,
            @RequestBody @Valid CompleteMemberProfileRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiUtils.success(userMemberCommandService.completeMemberProfile(temporaryMemberId, request)));
    }

    @PostMapping("/genres")
    public ResponseEntity<ApiResult<?>> editMemberGenre(
            @RequestBody @Valid EditMemberGenreRequest request,
            @LoginMember Member member) {
        userMemberCommandService.editMemberGenre(member, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiUtils.success());
    }

    @GetMapping("/genres")
    public ResponseEntity<ApiResult<GetPreferredGenreResponse>> getMemberGenres(@LoginMember Member member) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiUtils.success(userMemberQueryService.getPreferredGenre(member)));
    }

    @GetMapping("/duplicate-check")
    public ResponseEntity<ApiResult<CheckDuplicateEmailResponse>> duplicateCheck(@RequestParam String email) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiUtils.success(userMemberQueryService.checkEmailExists(email)));
    }
}
