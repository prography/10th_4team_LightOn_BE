package com.prography.lighton.common.domain.vo;

import com.prography.lighton.region.domain.entity.Region;
import com.prography.lighton.region.domain.entity.SubRegion;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class RegionInfo {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Region region;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private SubRegion subRegion;

    public static RegionInfo of(Region region, SubRegion subRegion) {
        return new RegionInfo(region, subRegion);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RegionInfo that)) {
            return false;
        }
        return Objects.equals(region.getId(), that.region.getId()) &&
                Objects.equals(subRegion.getCode(), that.subRegion.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(region.getId(), subRegion.getCode());
    }

}
