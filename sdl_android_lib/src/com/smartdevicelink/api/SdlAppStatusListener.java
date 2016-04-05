package com.smartdevicelink.api;

public interface SdlAppStatusListener {

    void onStatusChange(String appId, SdlApplication.Status status);

}
