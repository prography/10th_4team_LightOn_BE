package com.prography.lighton.performance.users.presentation.util;

import com.prography.lighton.performance.common.domain.entity.Performance;

public class PerformanceRegionFormatter {

    private static final String BLANK = " ";

    public static String getAddress(Performance performance) {
        return performance.getLocation().getRegion().getRegion().getName()
                + BLANK + performance.getLocation().getRegion().getSubRegion().getName();
    }
}
