package com.prography.lighton.performance.users.presentation.controller;

import com.prography.lighton.common.annotation.LoginMember;
import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import com.prography.lighton.member.domain.entity.Member;
import com.prography.lighton.performance.users.application.service.BuskingService;
import com.prography.lighton.performance.users.presentation.dto.BuskingRegisterRequest;
import com.prography.lighton.performance.users.presentation.dto.BuskingUpdateRequest;
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
@RequestMapping("/api/members/buskings")
public class BuskingController {

    private final BuskingService buskingService;

    @PostMapping
    public ResponseEntity<ApiResult<String>> registerPerformance(
            @LoginMember Member member,
            @Valid @RequestBody BuskingRegisterRequest request) {
        buskingService.registerBusking(member, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiUtils.success());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResult<String>> updatePerformance(@LoginMember Member member,
                                                               @PathVariable Long id,
                                                               @Valid @RequestBody BuskingUpdateRequest request) {
        buskingService.updateBusking(member, id, request);
        return ResponseEntity.ok()
                .body(ApiUtils.success());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult<String>> cancelPerformance(@LoginMember Member member, @PathVariable Long id) {
        buskingService.cancelBusking(member, id);
        return ResponseEntity.ok()
                .body(ApiUtils.success());
    }
}
