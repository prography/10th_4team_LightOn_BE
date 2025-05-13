package com.prography.lighton.member.domain.entity.vo;

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
}
