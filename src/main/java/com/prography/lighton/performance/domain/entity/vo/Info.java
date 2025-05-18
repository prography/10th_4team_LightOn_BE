package com.prography.lighton.performance.domain.entity.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Info {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String place;

    private String notice;

    @Column(nullable = false)
    private String posterUrl;

    public static Info of(String title, String description, String place, String notice, String posterUrl) {
        return new Info(title, description, place, notice, posterUrl);
    }
}
