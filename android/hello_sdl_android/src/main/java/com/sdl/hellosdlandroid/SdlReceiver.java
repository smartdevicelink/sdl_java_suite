package com.sdl.hellosdlandroid;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.smartdevicelink.transport.SdlBroadcastReceiver;
import com.smartdevicelink.transport.SdlRouterService;
import com.smartdevicelink.transport.TransportConstants;
import com.smartdevicelink.util.DebugTool;

public class SdlReceiver extends SdlBroadcastReceiver {
    private static final String TAG = "SdlBroadcastReceiver";

    @Override
    public void onSdlEnabled(Context context, Intent intent) {
        DebugTool.logInfo(TAG, "SDL Enabled");
        intent.setClass(context, SdlService.class);

        // Starting with Android S SdlService needs to be started from a foreground context.
        // We will check the intent for a pendingIntent parcelable extra
        // This pendingIntent allows us to start the SdlService from the context of the active router service which is in the foreground
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (intent.getParcelableExtra(TransportConstants.PENDING_INTENT_EXTRA) != null) {
                PendingIntent pendingIntent = (PendingIntent) intent.getParcelableExtra(TransportConstants.PENDING_INTENT_EXTRA);
                try {
                    pendingIntent.send(context, 0, intent);
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                }
            }
        } else {
            // SdlService needs to be foregrounded in Android O and above
            // This will prevent apps in the background from crashing when they try to start SdlService
            // Because Android O doesn't allow background apps to start background services
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent);
            } else {
                context.startService(intent);
            }
        }
    }

    @Override
    public Class<? extends SdlRouterService> defineLocalSdlRouterClass() {
        return com.sdl.hellosdlandroid.SdlRouterService.class;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent); // Required if overriding this method
    }

    @Override
    public String getSdlServiceName() {
        return "SdlService";
    }
}