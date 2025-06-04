package com.prography.lighton.announcement.admin.presentation;

import com.prography.lighton.announcement.admin.presentation.dto.request.ManageAnnouncementRequestDTO;
import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/announcements/")
@RequiredArgsConstructor
public class AdminAnnouncementController {

    @PostMapping()
    public ResponseEntity<ApiResult<?>> registerAnnouncement(
            @RequestBody @Valid ManageAnnouncementRequestDTO request) {
        return ResponseEntity.ok(ApiUtils.success());
    }

    @PutMapping("/{announcementId}")
    public ResponseEntity<ApiResult<?>> registerAnnouncement(
            @RequestBody @Valid ManageAnnouncementRequestDTO request,
            @PathVariable Long announcementId) {
        return ResponseEntity.ok(ApiUtils.success());
    }

    @DeleteMapping("/{announcementId}")
    public ResponseEntity<ApiResult<?>> deleteAnnouncement(
            @PathVariable Long announcementId) {
        return ResponseEntity.ok(ApiUtils.success());
    }

}
