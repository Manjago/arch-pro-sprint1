package io.github.manjago.devicemanagementservice;

import com.google.gson.annotations.SerializedName;

public class DeviceRequest {
    @SerializedName("display_name")
    private String displayName;

    @SerializedName("serial_number")
    private String serialNumber;

    private String vendor;

    @SerializedName("house_id")
    private String houseId;

    @SerializedName("device_type_name")
    private String deviceTypeName;

    public String getDisplayName() {
        return displayName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getVendor() {
        return vendor;
    }

    public String getHouseId() {
        return houseId;
    }

    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    @Override
    public String toString() {
        return "DeviceRequest{" + "displayName='" + displayName + '\'' + ", serialNumber='" + serialNumber + '\'' +
                ", vendor='" + vendor + '\'' + ", houseId='" + houseId + '\'' + ", deviceTypeName='" + deviceTypeName + '\'' + '}';
    }
}