package com.smartdevicelink.managers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.smartdevicelink.protocol.enums.SessionType;

public interface ServiceEncryptionListener {
    void onEncryptionServiceUpdated(@NonNull SessionType serviceType, boolean isServiceEncrypted, @Nullable String error);
}