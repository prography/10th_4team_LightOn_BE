package com.prography.lighton.announcement.users.application;

import static com.prography.lighton.common.fixture.AnnouncementTestFixture.ANNOUNCEMENT_ID;
import static com.prography.lighton.common.fixture.AnnouncementTestFixture.TITLE;
import static com.prography.lighton.common.fixture.AnnouncementTestFixture.createAnnouncement;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.data.domain.PageRequest.of;

import com.prography.lighton.announcement.common.domain.entity.Announcement;
import com.prography.lighton.announcement.users.infrastructure.UserAnnouncementRepository;
import com.prography.lighton.announcement.users.presentation.dto.response.GetAnnouncementDetailResponseDTO;
import com.prography.lighton.announcement.users.presentation.dto.response.GetAnnouncementListResponseDTO;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@ExtendWith(MockitoExtension.class)
class GetAnnouncementServiceTest {

    @Mock
    UserAnnouncementRepository userAnnouncementRepository;

    @Test
    @DisplayName("공지사항을 조회할 수 있다")
    void should_get_announcement() {
        // Given
        GetAnnouncementService service = new GetAnnouncementService(userAnnouncementRepository);
        Announcement announcement = createAnnouncement();
        when(userAnnouncementRepository.getById(anyLong())).thenReturn(announcement);

        // When
        GetAnnouncementDetailResponseDTO res = service.getAnnouncementDetail(ANNOUNCEMENT_ID);

        // Then
        verify(userAnnouncementRepository).getById(anyLong());
        assertEquals(ANNOUNCEMENT_ID, res.id());
        assertEquals(TITLE, res.title());
    }

    @Test
    @DisplayName("공지사항 목록을 조회할 수 있다")
    void should_get_announcement_list() {
        // Given
        GetAnnouncementService service = new GetAnnouncementService(userAnnouncementRepository);
        when(userAnnouncementRepository.findAllByOrderByCreatedAtDesc(of(0, 10)))
                .thenReturn(new PageImpl<>(List.of(createAnnouncement(1L), createAnnouncement(2L))));

        // When
        GetAnnouncementListResponseDTO res = service.getAnnouncementList(0, 10);

        // Then
        verify(userAnnouncementRepository).findAllByOrderByCreatedAtDesc(of(0, 10));
        assertEquals(2, res.announcements().getTotalElements());
    }

    @Test
    @DisplayName("공지사항 목록을 조회할 때 빈 목록을 반환할 수 있다")
    void should_return_empty_announcement_list() {
        // Given
        GetAnnouncementService service = new GetAnnouncementService(userAnnouncementRepository);
        when(userAnnouncementRepository.findAllByOrderByCreatedAtDesc(of(0, 10)))
                .thenReturn(Page.empty());

        // When
        GetAnnouncementListResponseDTO res = service.getAnnouncementList(0, 10);

        // Then
        verify(userAnnouncementRepository).findAllByOrderByCreatedAtDesc(of(0, 10));
        assertEquals(0, res.announcements().getTotalElements());
    }

}