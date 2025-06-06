package com.prography.lighton.common.geo;

public record BoundingBox(
        double minLatitude,
        double maxLatitude,
        double minLongitude,
        double maxLongitude) {
}
