package com.prography.lighton.common.domain.vo;

import com.prography.lighton.region.domain.entity.Region;
import com.prography.lighton.region.domain.entity.SubRegion;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RegionInfo {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Region region;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private SubRegion subRegion;

    public static RegionInfo of(Region region, SubRegion subRegion) {
        return new RegionInfo(region, subRegion);
    }
}
