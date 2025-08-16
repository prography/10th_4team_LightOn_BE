package com.prography.lighton.member.users.presentation;

import com.prography.lighton.common.annotation.LoginMember;
import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.member.users.application.query.CheckEmailExistService;
import com.prography.lighton.member.users.application.query.GetMemberInfoService;
import com.prography.lighton.member.users.application.query.GetPreferredArtistsService;
import com.prography.lighton.member.users.application.query.GetPreferredGenreService;
import com.prography.lighton.member.users.presentation.dto.response.CheckDuplicateEmailResponse;
import com.prography.lighton.member.users.presentation.dto.response.GetMemberInfoResponse;
import com.prography.lighton.member.users.presentation.dto.response.GetMyPreferredArtistsResponse;
import com.prography.lighton.member.users.presentation.dto.response.GetPreferredGenreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class UserMemberQueryController {

    private final CheckEmailExistService checkEmailExistsService;
    private final GetMemberInfoService getMemberInfoService;
    private final GetPreferredArtistsService getPreferredArtistsService;
    private final GetPreferredGenreService getPreferredGenreService;

    @GetMapping("/duplicate-check")
    public ResponseEntity<ApiResult<CheckDuplicateEmailResponse>> duplicateCheck(@RequestParam String email) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiUtils.success(checkEmailExistsService.handle(email)));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResult<GetMemberInfoResponse>> getMyInfo(@LoginMember Member member) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiUtils.success(getMemberInfoService.handle(member)));
    }

    @GetMapping("/preferred-artist")
    public ResponseEntity<ApiResult<GetMyPreferredArtistsResponse>> getMyPreferredArtists(
            @LoginMember Member member) {
        // 현재 구현은 내가 신청했던 공연의 아티스트 조회 - 추후 아티스트 페이지 개발 시 변경 예정
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiUtils.success(getPreferredArtistsService.handle(member)));
    }

    @GetMapping("/genres")
    public ResponseEntity<ApiResult<GetPreferredGenreResponse>> getMemberGenres(@LoginMember Member member) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiUtils.success(getPreferredGenreService.handle(member)));
    }

}
