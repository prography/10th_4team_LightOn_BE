package com.prography.lighton.common.geo;

public class GeoUtils {

    private static final double EARTH_RADIUS_KM = 6371.0;

    public static BoundingBox getBoundingBox(double lat, double lon, double radiusMeters) {
        double radiusKm = radiusMeters / 1000.0;
        double deltaLat = Math.toDegrees(radiusKm / EARTH_RADIUS_KM);
        double deltaLng = Math.toDegrees(radiusKm / (EARTH_RADIUS_KM * Math.cos(Math.toRadians(lat))));

        return new BoundingBox(
                lat - deltaLat, lat + deltaLat,
                lon - deltaLng, lon + deltaLng
        );
    }
}
