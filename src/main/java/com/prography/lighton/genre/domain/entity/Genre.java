package com.prography.lighton.genre.domain.entity;

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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SQLDelete(sql = "UPDATE genre SET status = false WHERE id = ?")
@SQLRestriction("status = true")
public class Genre extends BaseEntity {

    private String name;

    private String imageUrl;

    public static Genre of(String name, String imageUrl) {
        return new Genre(name, imageUrl);
    }
}
