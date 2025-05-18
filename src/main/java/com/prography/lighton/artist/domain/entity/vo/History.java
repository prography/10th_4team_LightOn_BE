package com.prography.lighton.artist.domain.entity.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class History {

    @Column(nullable = false)
    private String bio;

    @Embedded
    private ActivityImages activityImages;

    public static History of(String bio, List<String> imageUrls) {
        return new History(bio, ActivityImages.of(imageUrls));
    }
}
