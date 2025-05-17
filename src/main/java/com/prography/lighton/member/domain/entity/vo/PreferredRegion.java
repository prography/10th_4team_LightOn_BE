package com.prography.lighton.member.domain.entity.vo;

import com.prography.lighton.region.domain.entity.Region;
import com.prography.lighton.region.domain.entity.SubRegion;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

@Embeddable
public class PreferredRegion {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Region region;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private SubRegion subRegion;

    protected PreferredRegion() {}

    private PreferredRegion(Region region, SubRegion subRegion) {
        this.region = region;
        this.subRegion = subRegion;
    }

    public static PreferredRegion of(Region region, SubRegion subRegion) {
        return new PreferredRegion(region, subRegion);
    }
}
