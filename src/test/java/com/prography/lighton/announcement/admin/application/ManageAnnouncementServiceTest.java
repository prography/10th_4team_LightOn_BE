package com.prography.lighton.announcement.admin.application;

import static com.prography.lighton.common.fixture.AnnouncementTestFixture.ANNOUNCEMENT_ID;
import static com.prography.lighton.common.fixture.AnnouncementTestFixture.CONTENT;
import static com.prography.lighton.common.fixture.AnnouncementTestFixture.IMAGE_URL1;
import static com.prography.lighton.common.fixture.AnnouncementTestFixture.IMAGE_URL2;
import static com.prography.lighton.common.fixture.AnnouncementTestFixture.IMAGE_URL3;
import static com.prography.lighton.common.fixture.AnnouncementTestFixture.TITLE;
import static com.prography.lighton.common.fixture.AnnouncementTestFixture.createAnnouncement;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.prography.lighton.announcement.admin.infrastructure.AdminAnnouncementRepository;
import com.prography.lighton.announcement.admin.presentation.dto.request.ManageAnnouncementRequestDTO;
import com.prography.lighton.announcement.common.domain.entity.Announcement;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ManageAnnouncementServiceTest {

    @Mock
    AdminAnnouncementRepository adminAnnouncementRepository;


    @Test
    @DisplayName("공지사항을 등록할 수 있다")
    void should_register_announcement() {
        // Given
        ManageAnnouncementService service = new ManageAnnouncementService(adminAnnouncementRepository);
        ManageAnnouncementRequestDTO request = createRequest();
        // When
        service.registerAnnouncement(request);

        // Then
        verify(adminAnnouncementRepository).save(any(Announcement.class));
    }

    @Test
    @DisplayName("공지사항을 수정할 수 있다")
    void should_update_announcement() {
        // Given
        ManageAnnouncementService service = new ManageAnnouncementService(adminAnnouncementRepository);
        Announcement announcement = createAnnouncement();

        String updatedTitle = "수정된 공지사항 제목";
        String updatedContent = "수정된 공지사항 내용";
        List<String> updatedImages = List.of(
                "https://example.com/updated1.jpg",
                "https://example.com/updated2.jpg"

        );
        ManageAnnouncementRequestDTO request = new ManageAnnouncementRequestDTO(updatedTitle,
                updatedContent,
                updatedImages
        );

        when(adminAnnouncementRepository.getById(anyLong())).thenReturn(announcement);

        // When
        service.updateAnnouncement(ANNOUNCEMENT_ID, request);

        // Then
        verify(adminAnnouncementRepository).getById(ANNOUNCEMENT_ID);
        assertThat(announcement.getTitle()).isEqualTo(updatedTitle);
        assertThat(announcement.getContent()).isEqualTo(updatedContent);
        assertThat(announcement.getImages()).containsExactlyElementsOf(updatedImages);
    }

    @Test
    @DisplayName("공지사항을 삭제할 수 있다")
    void should_delete_announcement() {
        // Given
        ManageAnnouncementService service = new ManageAnnouncementService(adminAnnouncementRepository);
        Announcement announcement = createAnnouncement();
        when(adminAnnouncementRepository.getById(anyLong())).thenReturn(announcement);

        // When
        service.deleteAnnouncement(ANNOUNCEMENT_ID);

        // Then
        verify(adminAnnouncementRepository).getById(ANNOUNCEMENT_ID);
        verify(adminAnnouncementRepository).delete(announcement);
    }


    private static ManageAnnouncementRequestDTO createRequest() {
        return new ManageAnnouncementRequestDTO(
                TITLE,
                CONTENT,
                List.of(IMAGE_URL1, IMAGE_URL2, IMAGE_URL3)
        );
    }
}