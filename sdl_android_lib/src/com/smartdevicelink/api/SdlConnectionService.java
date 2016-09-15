package com.smartdevicelink.api;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class SdlConnectionService extends Service {

    private static final String TAG = SdlConnectionService.class.getSimpleName();

    private static final int CONNECTION_TIMEOUT = 60*1000;
    private static final int SERVICE_ID = 1988;

    private final Object MAP_LOCK = new Object();

    private SdlManager mSdlManager;
    private HashMap<String, SdlApplication> mRunningApplications = new HashMap<>();
    private HashMap<String, SdlApplication> mConnectedApplications = new HashMap<>();
    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private Notification mPersistentNotification;

    void startSdlApplication(SdlApplicationConfig config){
        synchronized (MAP_LOCK) {
            if (mRunningApplications.get(config.getAppId()) == null) {
                try {
                    SdlApplication application;
                    if(config.getSdlApplicationClass() == null){
                        application = new SdlApplication();
                    } else {
                        Class<? extends SdlApplication> clazz = config.getSdlApplicationClass();
                        application = clazz.newInstance();
                    }
                    application.initialize(this, config, mConnectionStatusListener);
                    mRunningApplications.put(config.getAppId(), application);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void startSdlApplication(Map<String, SdlApplicationConfig> configRegistry){
        synchronized (MAP_LOCK) {
            for (Map.Entry<String, SdlApplicationConfig> entry : configRegistry.entrySet()) {
                SdlApplicationConfig config = entry.getValue();
                startSdlApplication(config);
            }
        }
    }

    void setPersistentNotification(Notification notification){
        this.mPersistentNotification = notification;
    }

    void setSdlManager(SdlManager manager){
        this.mSdlManager = manager;
    }

    private void startTimer(){
        mainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                synchronized (MAP_LOCK) {
                    for (Map.Entry<String, SdlApplication> entry : mRunningApplications.entrySet()) {
                        entry.getValue().closeConnection(false, true, true);
                    }
                    mRunningApplications.clear();
                    mConnectedApplications.clear();
                    mSdlManager.sdlDisconnected();
                    Log.w(TAG, "SDL connection attempts timed out.");
                }
            }
        }, CONNECTION_TIMEOUT);
    }

    private void cancelTimer(){
        mainHandler.removeCallbacksAndMessages(null);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new SdlConnectionBinder();
    }

    private SdlApplication.ConnectionStatusListener mConnectionStatusListener = new SdlApplication.ConnectionStatusListener() {
        @Override
        public void onStatusChange(String appId, SdlApplication.Status status) {
            Log.i(TAG, "AppID: " + appId + " is " + status.name());
            synchronized (MAP_LOCK){
                switch (status){
                    case CONNECTING:
                        if(mConnectedApplications.size() == 0){
                            cancelTimer();
                            startTimer();
                        }
                        break;
                    case CONNECTED:
                        if(mConnectedApplications.size() == 0) {
                            cancelTimer();
                            startForeground(SERVICE_ID, mPersistentNotification);
                        }
                        SdlApplication connectedApp = mRunningApplications.get(appId);
                        if(connectedApp != null) {
                            mConnectedApplications.put(appId, connectedApp);
                        }
                        break;
                    case DISCONNECTED:
                        mConnectedApplications.remove(appId);
                        mRunningApplications.remove(appId);
                        if(mRunningApplications.size() == 0){
                            Log.i(TAG, "All applications disconnected.");
                            mSdlManager.sdlDisconnected();
                        }
                        break;
                }
            }
        }
    };

    class SdlConnectionBinder extends Binder{
        SdlConnectionService getSdlConnectionService(){
            return SdlConnectionService.this;
        }
    }

}
