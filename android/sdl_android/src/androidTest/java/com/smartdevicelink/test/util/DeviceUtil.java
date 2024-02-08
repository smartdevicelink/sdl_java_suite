package com.smartdevicelink.test.util;

import android.os.Build;
import android.util.Log;

public class DeviceUtil {
    public static boolean isEmulator() {
        Log.i("Julian", "isEmulator:  " + Build.FINGERPRINT);
        Log.i("Julian", "isEmulator:  " + Build.MODEL);
        Log.i("Julian", "isEmulator:  " + Build.MANUFACTURER);
        Log.i("Julian", "isEmulator: " + Build.BRAND);
        Log.i("Julian", "isEmulator: " + Build.PRODUCT);




        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || (Build.BRAND.startsWith("Android") && Build.DEVICE.startsWith("generic"))
                || (Build.PRODUCT != null && Build.PRODUCT.startsWith("sdk_google_phone"))
                || "google_sdk".equals(Build.PRODUCT);
    }
}
