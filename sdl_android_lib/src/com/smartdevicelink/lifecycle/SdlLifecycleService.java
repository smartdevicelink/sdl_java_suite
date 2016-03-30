package com.smartdevicelink.lifecycle;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import java.lang.reflect.Constructor;
import java.util.Stack;

public class SdlLifecycleService extends Service implements ISdlLifecycleService{

    private Stack<SdlActivity> mSdlActivityStack = new Stack<>();

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

    void startSdlActivity(Class<? extends SdlActivity> activity){

        SdlActivity activityInstance;

        try {
            Constructor<? extends SdlActivity> constructor = activity.getConstructor(SdlLifecycleService.class);
            activityInstance = constructor.newInstance(this);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to launch "
                    + activity.getClass().getName() + " as an SdlActivity.", e);
        }

        SdlActivity currentActivity = mSdlActivityStack.peek();

        if(currentActivity != null){
        }

        activityInstance.onCreate();

        mSdlActivityStack.push(activityInstance);

    }
}
