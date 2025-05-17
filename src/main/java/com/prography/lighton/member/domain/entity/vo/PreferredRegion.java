package com.prography.lighton.member.domain.entity.vo;

import java.util.Objects;

import com.prography.lighton.location.domain.entity.Region;
import com.prography.lighton.location.domain.entity.SubRegion;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

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
