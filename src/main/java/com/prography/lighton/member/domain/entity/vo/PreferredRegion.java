package com.prography.lighton.member.domain.entity.vo;

import java.util.Objects;

import com.prography.lighton.location.domain.entity.Region;
import com.prography.lighton.location.domain.entity.SubRegion;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;

@Embeddable
public class PreferredRegion {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Region region;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private SubRegion subRegion;

    protected PreferredRegion() {}

    private PreferredRegion(Region region, SubRegion subRegion) {
        this.region = region;
        this.subRegion = subRegion;
    }

    public static PreferredRegion of(Region region, SubRegion subRegion) {
        return new PreferredRegion(region, subRegion);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PreferredRegion that))
            return false;
		return Objects.equals(region, that.region) && Objects.equals(subRegion, that.subRegion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(region, subRegion);
    }

    @Override
    public String toString() {
        return "PreferredRegion{" +
                "region=" + region +
                ", subRegion=" + subRegion +
                '}';
    }
}
