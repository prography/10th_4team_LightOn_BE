package com.prography.lighton.member.users.presentation;

import com.prography.lighton.common.annotation.LoginMember;
import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.member.users.application.CompleteMemberProfileUseCase;
import com.prography.lighton.member.users.application.ManagePreferredGenreUseCase;
import com.prography.lighton.member.users.application.RegisterMemberUseCase;
import com.prography.lighton.member.users.presentation.dto.request.CompleteMemberProfileRequestDTO;
import com.prography.lighton.member.users.presentation.dto.request.EditMemberGenreRequestDTO;
import com.prography.lighton.member.users.presentation.dto.request.RegisterMemberRequestDTO;
import com.prography.lighton.member.users.presentation.dto.response.CheckDuplicateEmailResponseDTO;
import com.prography.lighton.member.users.presentation.dto.response.CompleteMemberProfileResponseDTO;
import com.prography.lighton.member.users.presentation.dto.response.GetPreferredGenreResponseDTO;
import com.prography.lighton.member.users.presentation.dto.response.RegisterMemberResponseDTO;
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
    private final ManagePreferredGenreUseCase managePreferredGenreUseCase;

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

    @PostMapping("/genres")
    public ResponseEntity<ApiResult<?>> editMemberGenre(
            @RequestBody @Valid EditMemberGenreRequestDTO request,
            @LoginMember Member member) {
        managePreferredGenreUseCase.editMemberGenre(member, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiUtils.success());
    }

    @GetMapping("/genres")
    public ResponseEntity<ApiResult<GetPreferredGenreResponseDTO>> getMemberGenres(@LoginMember Member member) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiUtils.success(managePreferredGenreUseCase.getPreferredGenre(member)));
    }

    @GetMapping("/duplicate-check")
    public ResponseEntity<ApiResult<CheckDuplicateEmailResponseDTO>> duplicateCheck(@RequestParam String email) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiUtils.success(registerMemberUseCase.checkEmailExists(email)));
    }
}
