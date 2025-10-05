package io.github.manjago.temperatureapi;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.util.Map;

class TemperatureByIdHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange t) throws IOException {
        final String sensorId = ServerExchangeUtils.extractLastSegment(t.getRequestURI().getPath());

        if (sensorId != null) {
            final String location = LocationSensorUtils.locationBySensorId(sensorId);
            final Map<String, Object> responseData = TemperatureService.getTemperatureData(location, sensorId);

            ServerExchangeUtils.sendJsonResponse(t, 200, responseData);

        } else {
            ServerExchangeUtils.sendProblemJsonResponse(
                    t,
                    400,
                    "Bad Request",
                    "Required query parameter 'sensorId' is missing."
            );
        }
    }

}
