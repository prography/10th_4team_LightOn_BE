package com.prography.lighton.announcement.common.domain.entity;

import com.prography.lighton.announcement.common.domain.exception.InvalidAnnouncementException;
import com.prography.lighton.common.domain.BaseEntity;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@SQLDelete(sql = "UPDATE announcement SET status = false WHERE id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SQLRestriction("status = true")
public class Announcement extends BaseEntity {

    public static final int MAX_IMAGE_COUNT = 3;

    private String title;

    private String content;

    @ElementCollection
    @CollectionTable(name = "announcement_images", joinColumns = @JoinColumn(name = "announcement_id"))
    @Column(name = "image_url")
    private List<String> images = new ArrayList<>();

    public static Announcement of(String title, String content, List<String> images) {
        validateImages(images);
        return new Announcement(title, content, images);
    }

    public static void validateImages(List<String> images) {
        if (images.size() > MAX_IMAGE_COUNT) {
            throw new InvalidAnnouncementException("공지사항 이미지는 최대 " + MAX_IMAGE_COUNT + "개까지 등록할 수 있습니다.");
        }
    }

    public void update(String title, String content, List<String> images) {
        this.title = title;
        this.content = content;
        this.images = images;
    }
}
