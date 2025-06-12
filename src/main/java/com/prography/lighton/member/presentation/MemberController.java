package com.prography.lighton.member.presentation;

import com.prography.lighton.auth.security.util.SecurityUtils;
import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import com.prography.lighton.member.application.CompleteMemberProfileUseCase;
import com.prography.lighton.member.application.ManagePreferredGenreUseCase;
import com.prography.lighton.member.application.RegisterMemberUseCase;
import com.prography.lighton.member.presentation.dto.request.CompleteMemberProfileRequestDTO;
import com.prography.lighton.member.presentation.dto.request.EditMemberGenreRequestDTO;
import com.prography.lighton.member.presentation.dto.request.RegisterMemberRequestDTO;
import com.prography.lighton.member.presentation.dto.response.CheckDuplicateEmailResponseDTO;
import com.prography.lighton.member.presentation.dto.response.CompleteMemberProfileResponseDTO;
import com.prography.lighton.member.presentation.dto.response.GetPreferredGenreResponseDTO;
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
            @RequestBody @Valid EditMemberGenreRequestDTO request) {
        managePreferredGenreUseCase.editMemberGenre(SecurityUtils.getCurrentMemberId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiUtils.success());
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
}
