package com.prography.lighton.member.domain.entity.vo;

import com.prography.lighton.region.domain.entity.Region;
import com.prography.lighton.region.domain.entity.SubRegion;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PreferredRegion {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Region region;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private SubRegion subRegion;

    public static PreferredRegion of(Region region, SubRegion subRegion) {
        return new PreferredRegion(region, subRegion);
    }
}
