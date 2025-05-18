package com.prography.lighton.artist.domain.entity.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public class History {

    @Column(nullable = false)
    private String bio;

    @Embedded
    private ActivityImages activityImages;
}
