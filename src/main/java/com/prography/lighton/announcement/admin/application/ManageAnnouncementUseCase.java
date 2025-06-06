package com.prography.lighton.announcement.admin.application;

import com.prography.lighton.announcement.admin.presentation.dto.request.ManageAnnouncementRequestDTO;

public interface ManageAnnouncementUseCase {

    void registerAnnouncement(ManageAnnouncementRequestDTO request);

    void updateAnnouncement(Long announcementId, ManageAnnouncementRequestDTO request);

    void deleteAnnouncement(Long announcementId);
}
