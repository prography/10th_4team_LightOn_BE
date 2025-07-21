package com.prography.lighton.advertisement.admin.presentation;

import com.prography.lighton.advertisement.admin.presentation.dto.request.SaveAdvertisementRequestDTO;
import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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
@RequestMapping("/api/admin/advertisements")
@RequiredArgsConstructor
public class AdminAdvertisementController {

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResult<?>> saveAdvertisement(
            @RequestPart SaveAdvertisementRequestDTO request,
            @RequestPart MultipartFile image
    ) {
        return ResponseEntity.ok(ApiUtils.success());
    }

    @DeleteMapping("/{advertisementId}")
    public ResponseEntity<ApiResult<?>> deleteAdvertisement(
            @PathVariable Long advertisementId
    ) {
        return ResponseEntity.ok(ApiUtils.success());
    }

    @PutMapping("/{advertisementId}")
    public ResponseEntity<ApiResult<?>> updateAdvertisement(
            @PathVariable Long advertisementId,
            @RequestPart SaveAdvertisementRequestDTO request,
            @RequestPart MultipartFile image
    ) {
        return ResponseEntity.ok(ApiUtils.success());
    }
}
