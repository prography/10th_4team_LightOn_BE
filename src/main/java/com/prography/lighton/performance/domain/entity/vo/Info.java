package com.prography.lighton.performance.domain.entity.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Info {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String place;

    @Column(nullable = false)
    private String notice;

    @Column(nullable = false)
    private String ThumbnailImageUrl;
}
