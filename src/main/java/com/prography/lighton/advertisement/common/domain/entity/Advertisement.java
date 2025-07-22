package com.prography.lighton.advertisement.common.domain.entity;

import com.prography.lighton.advertisement.common.domain.entity.enums.Position;
import com.prography.lighton.advertisement.common.domain.exception.InvalidDisplayOrderException;
import com.prography.lighton.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@SQLDelete(sql = "UPDATE advertisement SET status = false WHERE id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SQLRestriction("status = true")
public class Advertisement extends BaseEntity {

    private static final int MIN_DISPLAY_ORDER = 1;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Position position;

    @Column(nullable = false)
    private int displayOrder;

    private String title;

    private String content;

    private String linkUrl;

    public static Advertisement of(String imageUrl, Position position, int displayOrder, String linkUrl, String title,
                                   String content) {
        validateDisplayOrderIsGreaterThanZero(displayOrder);
        return new Advertisement(imageUrl, position, displayOrder, linkUrl, title, content);
    }

    private static void validateDisplayOrderIsGreaterThanZero(int displayOrder) {
        if (displayOrder < MIN_DISPLAY_ORDER) {
            throw new InvalidDisplayOrderException();
        }
    }
}
