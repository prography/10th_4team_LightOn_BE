package com.prography.lighton.artist.domain.entity.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ActivityImages {
    @Column(nullable = false)
    private String imageUrl1;

    private String imageUrl2;

    private String imageUrl3;

    private String imageUrl4;

    private String imageUrl5;

    public static ActivityImages of(List<String> urls) {
        String[] images = new String[5];
        for (int i = 0; i < 5; i++) {
            images[i] = (i < urls.size()) ? urls.get(i) : null;
        }
        return new ActivityImages(images[0], images[1], images[2], images[3], images[4]);
    }
}
