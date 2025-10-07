package io.github.manjago.devicemanagementservice;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class DeviceResponse {
    private final String id;

    @SerializedName("display_name")
    private final String displayName;

    @SerializedName("serial_number")
    private final String serialNumber;

    private final String vendor;
    private final String status;
    private final Map<String, Object> properties;

    @SerializedName("battery_level")
    private final Integer batteryLevel;

    @SerializedName("house_id")
    private final String houseId;

    @SerializedName("device_type")
    private final DeviceType deviceType;

    public DeviceResponse(String id,
            String displayName,
            String serialNumber,
            String vendor,
            String status,
            Map<String, Object> properties,
            Integer batteryLevel,
            String houseId,
            DeviceType deviceType) {
        this.id = id;
        this.displayName = displayName;
        this.serialNumber = serialNumber;
        this.vendor = vendor;
        this.status = status;
        this.properties = properties;
        this.batteryLevel = batteryLevel;
        this.houseId = houseId;
        this.deviceType = deviceType;
    }

    @Override
    public String toString() {
        return "DeviceResponse{" + "id='" + id + '\'' + ", displayName='" + displayName + '\'' + ", serialNumber='" + serialNumber + '\'' + ", vendor='" + vendor + '\'' + ", status='" + status + '\'' + ", properties=" + properties + ", batteryLevel=" + batteryLevel + ", houseId='" + houseId + '\'' + ", deviceType=" + deviceType + '}';
    }

    public static class DeviceType {
        private final int id;
        private final String name;

        public DeviceType(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return "DeviceType{" + "id=" + id + ", name='" + name + '\'' + '}';
        }
    }
}