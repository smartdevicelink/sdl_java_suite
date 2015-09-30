package com.smartdevicelink.lifecycle;

public class SdlManager {
    private static SdlManager mInstance = new SdlManager();

    public static synchronized SdlManager getInstance() {
        return mInstance;
    }

    private SdlManager() {
    }
}
