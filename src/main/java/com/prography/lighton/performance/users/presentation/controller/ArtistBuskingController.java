package com.prography.lighton.performance.users.presentation.controller;

import com.prography.lighton.common.annotation.LoginMember;
import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.users.application.service.BuskingService;
import com.prography.lighton.performance.users.presentation.dto.request.RegisterBuskingMultiPart;
import com.prography.lighton.performance.users.presentation.dto.request.SaveBuskingRequest;
import com.prography.lighton.performance.users.presentation.dto.request.UpdateArtistBuskingMultiPart;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/artists/performances/buskings")
public class ArtistBuskingController {

    private final BuskingService buskingService;

    @PostMapping
    public ResponseEntity<ApiResult<String>> registerPerformance(
            @LoginMember Member member,
            @Valid @RequestPart("data")
            SaveBuskingRequest data,

            @NotNull @RequestPart("posterImage")
            MultipartFile posterImage,

            @NotNull @RequestPart("proof")
            MultipartFile proof) {
        RegisterBuskingMultiPart request = new RegisterBuskingMultiPart(data, posterImage, proof);
        buskingService.registerBuskingByArtist(member, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiUtils.success());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResult<String>> updatePerformance(
            @LoginMember Member member,
            @PathVariable Long id,

            @Valid @RequestPart("data")
            SaveBuskingRequest data,

            @NotNull @RequestPart(value = "posterImage", required = false)
            MultipartFile posterImage,

            @NotNull @RequestPart(value = "proof", required = false)
            MultipartFile proof) {
        UpdateArtistBuskingMultiPart request = new UpdateArtistBuskingMultiPart(data, posterImage, proof);
        buskingService.updateBuskingByArtist(member, id, request);
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
