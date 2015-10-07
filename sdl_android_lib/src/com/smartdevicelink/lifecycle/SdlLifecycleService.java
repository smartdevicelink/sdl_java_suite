package com.smartdevicelink.lifecycle;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

public class SdlLifecycleService extends Service implements ISdlLifecycleService{

    private final SdlLifecycleBinder mBinder = new SdlLifecycleBinder() {
        @Override
        public ISdlLifecycleService getSdlPersistentService() {
            return SdlLifecycleService.this;
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    @Override
    public void onSdlConnected() {
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("SDL Connected")
                .setContentText("SDL Connected")
                .build();
        startForeground(1990, notification);
    }

    @Override
    public void onSdlDisconnected() {
        stopForeground(true);
    }
}
