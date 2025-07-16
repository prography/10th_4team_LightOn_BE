package com.prography.lighton.performance.users.presentation.controller;

import com.prography.lighton.common.annotation.LoginMember;
import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.users.application.service.BuskingService;
import com.prography.lighton.performance.users.presentation.dto.request.SaveUserBuskingRequest;
import com.prography.lighton.performance.users.presentation.dto.request.UserBuskingUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members/performances/buskings")
public class UserBuskingController {

    private final BuskingService buskingService;

    @PostMapping
    public ResponseEntity<ApiResult<String>> registerBusking(
            @LoginMember Member member,
            @Valid @RequestBody SaveUserBuskingRequest request) {
        buskingService.registerBuskingByUser(member, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiUtils.success());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResult<String>> updateBusking(@LoginMember Member member,
                                                           @PathVariable Long id,
                                                           @Valid @RequestBody UserBuskingUpdateRequest request) {
        buskingService.updateBuskingByUser(member, id, request);
        return ResponseEntity.ok()
                .body(ApiUtils.success());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult<String>> cancelBusking(@LoginMember Member member, @PathVariable Long id) {
        buskingService.cancelBusking(member, id);
        return ResponseEntity.ok()
                .body(ApiUtils.success());
    }
}
