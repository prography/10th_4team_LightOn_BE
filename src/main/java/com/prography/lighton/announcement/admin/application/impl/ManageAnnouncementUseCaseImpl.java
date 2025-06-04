package com.prography.lighton.announcement.admin.application.impl;

import com.prography.lighton.announcement.admin.application.ManageAnnouncementUseCase;
import com.prography.lighton.announcement.admin.infrastructure.AdminAnnouncementRepository;
import com.prography.lighton.announcement.admin.presentation.dto.request.ManageAnnouncementRequestDTO;
import com.prography.lighton.announcement.common.domain.entity.Announcement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ManageAnnouncementUseCaseImpl implements ManageAnnouncementUseCase {

    private final AdminAnnouncementRepository adminAnnouncementRepository;

    @Override
    public void registerAnnouncement(ManageAnnouncementRequestDTO request) {
        Announcement announcement = Announcement.of(
                request.title(),
                request.content(),
                request.imageUrl()
        );

        adminAnnouncementRepository.save(announcement);
    }

    @Override
    public void updateAnnouncement(Long announcementId, ManageAnnouncementRequestDTO request) {
        Announcement announcement = adminAnnouncementRepository.getById(announcementId);

        announcement.update(
                request.title(),
                request.content(),
                request.imageUrl()
        );
    }

    @Override
    public void deleteAnnouncement(Long announcementId) {
        Announcement announcement = adminAnnouncementRepository.getById(announcementId);

        adminAnnouncementRepository.delete(announcement);
    }
}
