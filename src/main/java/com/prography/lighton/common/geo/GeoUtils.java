package com.prography.lighton.common.geo;

public class GeoUtils {

    private static final double EARTH_RADIUS_KM = 6371.0;

    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_KM * c * 1000; // meter
    }

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