package com.prography.lighton.announcement.users.application;

import com.prography.lighton.announcement.users.presentation.dto.response.GetAnnouncementDetailResponseDTO;
import com.prography.lighton.announcement.users.presentation.dto.response.GetAnnouncementListResponseDTO;

public interface GetAnnouncementUseCase {

    GetAnnouncementDetailResponseDTO getAnnouncementDetail(Long announcementId);

    GetAnnouncementListResponseDTO getAnnouncementList(int page, int size);
}
