package com.prography.lighton.artist.domain.entity.vo;

import com.prography.lighton.location.domain.entity.Region;
import com.prography.lighton.location.domain.entity.SubRegion;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

@Embeddable
public class ActivityLocation {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Region region;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private SubRegion subRegion;
}
