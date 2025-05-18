package com.prography.lighton.performance.domain.entity.vo;

import com.prography.lighton.region.domain.entity.SubRegion;
import jakarta.persistence.*;

@Embeddable
public class Location {

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false)
    private SubRegion region;
}
