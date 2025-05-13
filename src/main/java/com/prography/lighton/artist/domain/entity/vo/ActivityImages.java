package com.prography.lighton.artist.domain.entity.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ActivityImages {
    @Column(nullable = false)
    private String imageUrl1;

    private String imageUrl2;

    private String imageUrl3;

    private String imageUrl4;

    private String imageUrl5;
}
