package com.prography.lighton.artist.domain.entity.vo;

import com.prography.lighton.common.domain.DomainValidator;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class History {

    @Column(nullable = false)
    private String bio;

    @Embedded
    private ActivityImages activityImages;

    public History(String bio, ActivityImages activityImages) {
        this.bio = DomainValidator.requireNonBlank(bio);
        this.activityImages = activityImages;
    }

    public static History of(String bio, List<String> imageUrls) {
        return new History(bio, ActivityImages.of(imageUrls));
    }
}
