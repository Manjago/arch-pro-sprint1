
package io.github.manjago.devicemanagementservice.handler;

import io.github.manjago.devicemanagementservice.dto.DeviceRequest;
import io.github.manjago.devicemanagementservice.dto.DeviceResponse;
import io.github.manjago.devicemanagementservice.exception.ValidationException;
import io.github.manjago.devicemanagementservice.util.JsonUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import com.github.f4b6a3.uuid.UuidCreator;

import java.util.HashMap;

public class PostDevicesHandler implements Route {

    @Override
    public Object handle(Request req, Response res)  {
        DeviceRequest request = JsonUtil.fromJson(req.body(), DeviceRequest.class);

        if (request.getDisplayName() == null || request.getDisplayName().isBlank()) {
            throw new ValidationException("display_name is required");
        }
        if (request.getSerialNumber() == null || request.getSerialNumber().isBlank()) {
            throw new ValidationException("serial_number is required");
        }

        final DeviceResponse response = new DeviceResponse(
                UuidCreator.getTimeOrderedEpoch().toString(), // UUID v7
                request.getDisplayName(),
                request.getSerialNumber(),
                request.getVendor(),
                "registered",
                new HashMap<>(),
                null,
                request.getHouseId(),
                new DeviceResponse.DeviceType(1, request.getDeviceTypeName())
        );

        res.type("application/json");
        res.status(201);

        return JsonUtil.toJson(response);
    }
}