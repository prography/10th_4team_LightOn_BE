package com.prography.lighton.member.users.presentation;

import com.prography.lighton.common.annotation.LoginMember;
import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.member.users.application.command.CompleteProfileService;
import com.prography.lighton.member.users.application.command.EditPreferredGenreService;
import com.prography.lighton.member.users.application.command.InactivateMemberService;
import com.prography.lighton.member.users.application.command.RegisterMemberService;
import com.prography.lighton.member.users.application.query.UserMemberQueryService;
import com.prography.lighton.member.users.presentation.dto.request.CompleteMemberProfileRequest;
import com.prography.lighton.member.users.presentation.dto.request.EditMemberGenreRequest;
import com.prography.lighton.member.users.presentation.dto.request.RegisterMemberRequest;
import com.prography.lighton.member.users.presentation.dto.response.CheckDuplicateEmailResponse;
import com.prography.lighton.member.users.presentation.dto.response.CompleteMemberProfileResponse;
import com.prography.lighton.member.users.presentation.dto.response.GetMemberInfoResponse;
import com.prography.lighton.member.users.presentation.dto.response.GetMyPreferredArtistsResponse;
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

    private final CompleteProfileService completeProfileService;
    private final RegisterMemberService registerMemberService;
    private final EditPreferredGenreService editPreferredGenreService;
    private final InactivateMemberService inactivateMemberService;

    private final UserMemberQueryService userMemberQueryService;

    @PostMapping
    public ResponseEntity<ApiResult<RegisterMemberResponse>> register(
            @RequestBody @Valid RegisterMemberRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiUtils.success(registerMemberService.handle(request)));
    }

    @PostMapping("/{temporaryMemberId}/info")
    public ResponseEntity<ApiResult<CompleteMemberProfileResponse>> completeMemberProfile(
            @PathVariable Long temporaryMemberId,
            @RequestBody @Valid CompleteMemberProfileRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiUtils.success(completeProfileService.handle(temporaryMemberId, request)));
    }

    @PostMapping("/genres")
    public ResponseEntity<ApiResult<?>> editMemberGenre(
            @RequestBody @Valid EditMemberGenreRequest request,
            @LoginMember Member member) {
        editPreferredGenreService.handle(member, request);
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

    @GetMapping("/preferred-artist")
    public ResponseEntity<ApiResult<GetMyPreferredArtistsResponse>> getMyPreferredArtists(
            @LoginMember Member member) {
        // 현재 구현은 내가 신청했던 공연의 아티스트 조회 - 추후 아티스트 페이지 개발 시 변경 예정
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiUtils.success(userMemberQueryService.getMyPreferredArtists(member)));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResult<GetMemberInfoResponse>> getMyInfo(@LoginMember Member member) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiUtils.success(userMemberQueryService.getMyInfo(member)));
    }
}
