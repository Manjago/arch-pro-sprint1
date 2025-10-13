package io.github.manjago.temperatureapi;

final class LocationSensorUtils {

    private LocationSensorUtils() {
    }

    static String sensorIdByLocation(String location) {
        return switch (location) {
            case "Living Room" ->
                "1";
            case "Bedroom" ->
                "2";
            case "Kitchen" ->
                "3";
            default ->
                "0";
        };
    }

    static String locationBySensorId(String sensorId) {
        return switch (sensorId) {
            case "1" ->
                "Living Room";
            case "2" ->
                "Bedroom";
            case "3" ->
                "Kitchen";
            default ->
                "Unknown";
        };
    }

}
