package io.github.manjago.devicemanagementservice.handler;

import io.github.manjago.devicemanagementservice.dto.DeviceResponse;
import io.github.manjago.devicemanagementservice.service.DeviceService;
import io.github.manjago.devicemanagementservice.util.JsonUtil;
import spark.Request;
import spark.Response;
import spark.Route;

public class GetDeviceHandler implements Route {
    private final DeviceService deviceService = DeviceService.getInstance();

    @Override
    public Object handle(Request req, Response res) throws Exception {
        final String id = req.params(":id");

        final DeviceResponse device = deviceService.findById(id);

        res.type("application/json");
        res.status(200);

        return JsonUtil.toJson(device);
    }
}