package com.prography.lighton.announcement.common.domain.entity;

import static com.prography.lighton.common.fixture.AnnouncementTestFixture.CONTENT;
import static com.prography.lighton.common.fixture.AnnouncementTestFixture.IMAGE_URL1;
import static com.prography.lighton.common.fixture.AnnouncementTestFixture.IMAGE_URL2;
import static com.prography.lighton.common.fixture.AnnouncementTestFixture.IMAGE_URL3;
import static com.prography.lighton.common.fixture.AnnouncementTestFixture.TITLE;
import static com.prography.lighton.common.fixture.AnnouncementTestFixture.createAnnouncement;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.prography.lighton.announcement.common.domain.exception.InvalidAnnouncementException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AnnouncementTest {

    @Test
    @DisplayName("공지사항을 정상적으로 생성할 수 있다")
    void should_create_announcement() {
        // Given

        // When
        Announcement announcement = createAnnouncement();

        // Then
        assertThat(announcement.getTitle()).isEqualTo(TITLE);
        assertThat(announcement.getContent()).isEqualTo(CONTENT);
        assertThat(announcement.getImages()).hasSize(3);
    }

    @Test
    @DisplayName("공지사항의 제목이 비어있는 경우 예외가 발생한다")
    void should_throw_exception_when_title_is_empty() {
        // Given

        // When & Then
        assertThrows(InvalidAnnouncementException.class,
                () -> Announcement.of("", CONTENT, List.of(IMAGE_URL1, IMAGE_URL2, IMAGE_URL3))
        );
    }

    @Test
    @DisplayName("공지사항의 내용이 비어있는 경우 예외가 발생한다")
    void should_throw_exception_when_content_is_empty() {
        // Given

        // When & Then
        assertThrows(InvalidAnnouncementException.class,
                () -> Announcement.of(TITLE, "", List.of(IMAGE_URL1, IMAGE_URL2, IMAGE_URL3))
        );
    }


    @Test
    @DisplayName("공지사항의 이미지는 최대 3개까지만 허용된다")
    void should_limit_images_to_three() {
        // Given

        // When & Then
        assertThrows(InvalidAnnouncementException.class,
                () -> Announcement.of(
                        TITLE,
                        CONTENT,
                        List.of(IMAGE_URL1, IMAGE_URL2, IMAGE_URL3, "https://example.com/image4.jpg")
                )
        );
    }

    @Test
    @DisplayName("공지사항을 수정할 수 있다")
    void should_update_announcement() {
        // Given
        Announcement announcement = createAnnouncement();
        String newTitle = "새로운 제목";
        String newContent = "새로운 내용";
        List<String> newImages = List.of("https://example.com/new_image1.jpg", "https://example.com/new_image2.jpg");

        // When
        announcement.update(newTitle, newContent, newImages);

        // Then
        assertThat(announcement.getTitle()).isEqualTo(newTitle);
        assertThat(announcement.getContent()).isEqualTo(newContent);
        assertThat(announcement.getImages()).containsExactlyElementsOf(newImages);
    }
}