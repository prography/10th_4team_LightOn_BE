package com.prography.lighton.announcement.common.domain.entity;

import com.prography.lighton.common.domain.BaseEntity;
import jakarta.persistence.Entity;
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

    private String title;

    private String content;

    private String imageUrl;

    public static Announcement of(String title, String content, String imageUrl) {
        return new Announcement(title, content, imageUrl);
    }
}
