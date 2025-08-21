package com.prography.lighton.announcement.users.presentation;

import com.prography.lighton.announcement.users.application.GetAnnouncementService;
import com.prography.lighton.announcement.users.presentation.dto.response.GetAnnouncementDetailResponseDTO;
import com.prography.lighton.announcement.users.presentation.dto.response.GetAnnouncementListResponseDTO;
import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members/announcements")
@RequiredArgsConstructor
public class UserAnnouncementController {

    private final GetAnnouncementService getAnnouncementService;

    @GetMapping("/{announcementId}")
    public ResponseEntity<ApiResult<GetAnnouncementDetailResponseDTO>> getAnnouncementDetail(
            @PathVariable Long announcementId
    ) {
        return ResponseEntity.ok(ApiUtils.success(
                getAnnouncementService.getAnnouncementDetail(announcementId)));
    }

    @GetMapping
    public ResponseEntity<ApiResult<GetAnnouncementListResponseDTO>> getAnnouncementList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(ApiUtils.success(
                getAnnouncementService.getAnnouncementList(page, size)
        ));
    }

}
