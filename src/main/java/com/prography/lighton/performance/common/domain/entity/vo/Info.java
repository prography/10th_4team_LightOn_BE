package com.prography.lighton.performance.common.domain.entity.vo;

import static com.prography.lighton.common.domain.DomainValidator.requireNonBlank;

import com.prography.lighton.common.domain.DomainValidator;
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

    private String posterUrl;

    public static Info of(String title, String description, String place, String notice, String posterUrl) {
        requireNonBlank(title);
        requireNonBlank(description);
        requireNonBlank(place);
        return new Info(title, description, place, notice, posterUrl);
    }
}
