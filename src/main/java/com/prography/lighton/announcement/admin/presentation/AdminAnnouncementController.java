package com.prography.lighton.announcement.admin.presentation;

import com.prography.lighton.announcement.admin.application.ManageAnnouncementUseCase;
import com.prography.lighton.announcement.admin.presentation.dto.request.ManageAnnouncementRequestDTO;
import com.prography.lighton.common.annotation.AdminOnly;
import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
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
@RequestMapping("/api/admin/announcements")
@RequiredArgsConstructor
public class AdminAnnouncementController {

    private final ManageAnnouncementUseCase manageAnnouncementUseCase;

    @PostMapping()
    @AdminOnly
    public ResponseEntity<ApiResult<?>> registerAnnouncement(
            @RequestBody @Valid ManageAnnouncementRequestDTO request) {
        manageAnnouncementUseCase.registerAnnouncement(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiUtils.success());
    }

    @PutMapping("/{announcementId}")
    @AdminOnly
    public ResponseEntity<ApiResult<?>> registerAnnouncement(
            @RequestBody @Valid ManageAnnouncementRequestDTO request,
            @PathVariable Long announcementId) {
        manageAnnouncementUseCase.updateAnnouncement(announcementId, request);
        return ResponseEntity.ok(ApiUtils.success());
    }

    @DeleteMapping("/{announcementId}")
    @AdminOnly
    public ResponseEntity<ApiResult<?>> deleteAnnouncement(
            @PathVariable Long announcementId) {
        manageAnnouncementUseCase.deleteAnnouncement(announcementId);
        return ResponseEntity.ok(ApiUtils.success());
    }

}
