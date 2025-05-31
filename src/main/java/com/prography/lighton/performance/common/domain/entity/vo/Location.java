package com.prography.lighton.performance.common.domain.entity.vo;

import com.prography.lighton.common.domain.vo.RegionInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Location {

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Embedded
    private RegionInfo region;

    public static Location of(Double latitude, Double longitude, RegionInfo region) {
        return new Location(latitude, longitude, region);
    }
}
