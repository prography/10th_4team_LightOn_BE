package com.prography.lighton.advertisement.admin.presentation;

import com.prography.lighton.advertisement.admin.application.AdminAdvertisementCommandService;
import com.prography.lighton.advertisement.admin.presentation.dto.request.SaveAdvertisementRequestDTO;
import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin/advertisements")
@RequiredArgsConstructor
public class AdminAdvertisementController {

    private final AdminAdvertisementCommandService adminAdvertisementCommandService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResult<?>> saveAdvertisement(
            @RequestPart @Valid SaveAdvertisementRequestDTO request,
            @RequestPart @Valid @NotNull MultipartFile image
    ) {
        adminAdvertisementCommandService.saveAdvertisement(request, image);
        return ResponseEntity.ok(ApiUtils.success());
    }

    @DeleteMapping("/{advertisementId}")
    public ResponseEntity<ApiResult<?>> deleteAdvertisement(
            @PathVariable Long advertisementId
    ) {
        adminAdvertisementCommandService.deleteAdvertisement(advertisementId);
        return ResponseEntity.ok(ApiUtils.success());
    }
}
