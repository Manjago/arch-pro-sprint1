package io.github.manjago.temperatureapi;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.util.Map;

class TemperatureByLocationHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange t) throws IOException {
        final Map<String, String> queryParams = ServerExchangeUtils.parseQueryString(t.getRequestURI().getQuery());

        final String location = queryParams.get("location");

        if (location != null) {
            String sensorId = LocationSensorUtils.sensorIdByLocation(location);
            final Map<String, Object> responseData = TemperatureService.getTemperatureData(location, sensorId);

            ServerExchangeUtils.sendJsonResponse(t, 200, responseData);
        } else {
            ServerExchangeUtils.sendProblemJsonResponse(
                    t,
                    400,
                    "Bad Request",
                    "Required query parameter 'location' is missing."
            );
        }
    }

}
