package com.prography.lighton.performance.users.presentation.controller;

import com.prography.lighton.common.annotation.LoginMember;
import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.users.application.service.ArtistPerformanceService;
import com.prography.lighton.performance.users.presentation.dto.PerformanceRegisterRequest;
import com.prography.lighton.performance.users.presentation.dto.PerformanceUpdateRequest;
import com.prography.lighton.performance.users.presentation.dto.RegisterPerformanceMultiPart;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/artists/performances/concerts")
public class PerformanceController {

    private final ArtistPerformanceService artistPerformanceService;

    @PostMapping
    public ResponseEntity<ApiResult<String>> registerPerformance(
            @LoginMember Member member,

            @Valid @RequestPart("data")
            PerformanceRegisterRequest data,

            @NotNull @RequestPart("posterImage")
            MultipartFile posterImage,

            @NotNull @RequestPart("proof")
            MultipartFile proof) {
        RegisterPerformanceMultiPart request = new RegisterPerformanceMultiPart(data, posterImage, proof);
        artistPerformanceService.registerPerformance(member, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiUtils.success());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResult<String>> updatePerformance(@LoginMember Member member,
                                                               @PathVariable Long id,
                                                               @Valid @RequestBody PerformanceUpdateRequest request) {
        artistPerformanceService.updatePerformance(member, id, request);
        return ResponseEntity.ok()
                .body(ApiUtils.success());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult<String>> cancelPerformance(@LoginMember Member member, @PathVariable Long id) {
        artistPerformanceService.cancelPerformance(member, id);
        return ResponseEntity.ok()
                .body(ApiUtils.success());
    }
}
