package com.prography.lighton.common.fixture;

import static org.springframework.test.util.ReflectionTestUtils.setField;

import com.prography.lighton.announcement.common.domain.entity.Announcement;
import java.util.List;

public class AnnouncementTestFixture {

    public static final Long ANNOUNCEMENT_ID = 1L;
    public static final String TITLE = "공지사항 제목";
    public static final String CONTENT = "공지사항 내용";
    public static final String IMAGE_URL1 = "https://example.com/image1.jpg";
    public static final String IMAGE_URL2 = "https://example.com/image2.jpg";
    public static final String IMAGE_URL3 = "https://example.com/image3.jpg";


    public static Announcement createAnnouncement() {
        Announcement announcement = Announcement.of(
                TITLE,
                CONTENT,
                List.of(IMAGE_URL1, IMAGE_URL2, IMAGE_URL3)
        );
        setField(announcement, "id", ANNOUNCEMENT_ID);
        return announcement;
    }

    public static Announcement createAnnouncement(long id) {
        Announcement announcement = Announcement.of(
                TITLE,
                CONTENT,
                List.of(IMAGE_URL1, IMAGE_URL2, IMAGE_URL3)
        );
        setField(announcement, "id", id);
        return announcement;
    }

}
