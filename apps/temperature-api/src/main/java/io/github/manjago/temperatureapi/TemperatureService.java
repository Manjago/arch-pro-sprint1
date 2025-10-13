package io.github.manjago.temperatureapi;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

final class TemperatureService {

    private TemperatureService() {
    }

    static Map<String, Object> getTemperatureData(String location, String sensorId) {
        final double randomValue = ThreadLocalRandom.current().nextDouble(-50.0, 40.0);
        final double roundedValue = Math.round(randomValue * 10.0) / 10.0;

        return Map.of(
                "value", roundedValue,
                "unit", "celsius",
                "timestamp", Instant.now().toString(), 
                "location", location,
                "status", "ok",
                "sensor_id", sensorId,
                "sensor_type", "thermistor-v2",
                "description", "Just test case"
        );
    }
}
