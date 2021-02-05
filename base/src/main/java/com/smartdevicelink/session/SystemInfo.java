package com.smartdevicelink.session;

import androidx.annotation.Nullable;

import com.smartdevicelink.proxy.rpc.VehicleType;

public class SystemInfo {
    private VehicleType vehicleType;
    @Nullable
    private String systemSoftwareVersion;
    @Nullable
    private String systemHardwareVersion;

    public SystemInfo(VehicleType vehicleType, @Nullable String systemSoftwareVersion, @Nullable String systemHardwareVersion) {
        this.vehicleType = vehicleType;
        this.systemSoftwareVersion = systemSoftwareVersion;
        this.systemHardwareVersion = systemHardwareVersion;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    @Nullable
    public String getSystemSoftwareVersion() {
        return systemSoftwareVersion;
    }

    @Nullable
    public String getSystemHardwareVersion() {
        return systemHardwareVersion;
    }
}


