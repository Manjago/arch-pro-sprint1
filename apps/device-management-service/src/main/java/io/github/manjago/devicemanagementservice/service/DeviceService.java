package io.github.manjago.devicemanagementservice.service;

import io.github.manjago.devicemanagementservice.dto.DeviceResponse;
import io.github.manjago.devicemanagementservice.exception.DeviceNotFoundException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DeviceService {
    private static final DeviceService INSTANCE = new DeviceService();
    private final Map<String, DeviceResponse> devices = new ConcurrentHashMap<>();

    private DeviceService() {}

    public static DeviceService getInstance() {
        return INSTANCE;
    }

    public void save(DeviceResponse device) {
        devices.put(device.getId(), device);
    }

    public DeviceResponse findById(String id) {
        final DeviceResponse device = devices.get(id);
        if (device == null) {
            throw new DeviceNotFoundException(id);
        }
        return device;
    }
}