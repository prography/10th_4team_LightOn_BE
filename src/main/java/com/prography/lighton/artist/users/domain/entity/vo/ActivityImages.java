package com.prography.lighton.artist.users.domain.entity.vo;

import com.prography.lighton.common.domain.DomainValidator;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ActivityImages {
    @Column(nullable = false)
    private String imageUrl1;

    private String imageUrl2;

    private String imageUrl3;

    private String imageUrl4;

    private String imageUrl5;

    public ActivityImages(String imageUrl1, String imageUrl2, String imageUrl3, String imageUrl4, String imageUrl5) {
        this.imageUrl1 = DomainValidator.requireNonBlank(imageUrl1);
        this.imageUrl2 = imageUrl2;
        this.imageUrl3 = imageUrl3;
        this.imageUrl4 = imageUrl4;
        this.imageUrl5 = imageUrl5;
    }

    public static ActivityImages of(List<String> urls) {
        String[] images = new String[5];
        for (int i = 0; i < 5; i++) {
            images[i] = (i < urls.size()) ? urls.get(i) : null;
        }
        return new ActivityImages(images[0], images[1], images[2], images[3], images[4]);
    }

    public List<String> toList() {
        List<String> result = new ArrayList<>(5);

        for (String url : new String[]{imageUrl1, imageUrl2, imageUrl3, imageUrl4, imageUrl5}) {
            if (url != null && !url.isBlank()) {
                result.add(url);
            }
        }

        return result;
    }


}
