package com.smartdevicelink.managers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.smartdevicelink.protocol.enums.SessionType;

public interface ServiceEncryptionListener {
    void onEncryptionServiceUpdated(@NonNull SessionType serviceType, boolean isServiceEncrypted, @Nullable String error);
}