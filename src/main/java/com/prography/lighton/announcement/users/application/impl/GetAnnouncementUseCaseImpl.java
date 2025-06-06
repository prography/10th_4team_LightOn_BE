package com.prography.lighton.announcement.users.application.impl;

import static org.springframework.data.domain.PageRequest.of;

import com.prography.lighton.announcement.common.domain.entity.Announcement;
import com.prography.lighton.announcement.users.application.GetAnnouncementUseCase;
import com.prography.lighton.announcement.users.infrastructure.UserAnnouncementRepository;
import com.prography.lighton.announcement.users.presentation.dto.response.GetAnnouncementDetailResponseDTO;
import com.prography.lighton.announcement.users.presentation.dto.response.GetAnnouncementListResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetAnnouncementUseCaseImpl implements GetAnnouncementUseCase {

    private final UserAnnouncementRepository userAnnouncementRepository;

    @Override
    public GetAnnouncementDetailResponseDTO getAnnouncementDetail(Long announcementId) {
        Announcement announcement = userAnnouncementRepository.getById(announcementId);

        return GetAnnouncementDetailResponseDTO.of(
                announcement.getId(),
                announcement.getTitle(),
                announcement.getContent(),
                announcement.getImages());
    }

    @Override
    public GetAnnouncementListResponseDTO getAnnouncementList(int page, int size) {
        Page<Announcement> announcements = userAnnouncementRepository.findAllByOrderByCreatedAtDesc(
                of(page, size));

        return GetAnnouncementListResponseDTO.of(announcements);
    }
}
