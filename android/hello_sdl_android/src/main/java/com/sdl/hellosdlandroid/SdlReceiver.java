package com.sdl.hellosdlandroid;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbManager;
import android.os.Build;

import com.smartdevicelink.transport.SdlBroadcastReceiver;
import com.smartdevicelink.transport.SdlRouterService;
import com.smartdevicelink.transport.TransportConstants;
import com.smartdevicelink.util.AndroidTools;
import com.smartdevicelink.util.DebugTool;

public class SdlReceiver extends SdlBroadcastReceiver {
    private static final String TAG = "SdlBroadcastReceiver";

    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    private PendingIntent pendingIntent;
    private Context storedContext;
    private Intent storedIntent;

    @Override
    public void onSdlEnabled(Context context, Intent intent) {
        DebugTool.logInfo(TAG, "SDL Enabled");
        intent.setClass(context, SdlService.class);

        // Starting with Android S SdlService needs to be started from a foreground context.
        // We will check the intent for a pendingIntent parcelable extra
        // This pendingIntent allows us to start the SdlService from the context of the active router service which is in the foreground
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent pendingIntent = (PendingIntent) intent.getParcelableExtra(TransportConstants.PENDING_INTENT_EXTRA);
            if (pendingIntent != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                    if (!AndroidTools.hasForegroundServiceTypePermission(context)) {
                        requestUsbAccessory(context);
                        storedIntent = intent;
                        storedContext = context;
                        this.pendingIntent = pendingIntent;
                        DebugTool.logInfo(TAG, "Permission missing for ForegroundServiceType connected device." + context);
                        return;
                    }
                }
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
        return SdlService.class.getSimpleName();
    }

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action) && storedContext != null && storedIntent != null && pendingIntent != null) {
                if (AndroidTools.hasForegroundServiceTypePermission(storedContext)) {
                    try {
                        pendingIntent.send(storedContext, 0, storedIntent);
                    } catch (PendingIntent.CanceledException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    /**
     * Request permission from USB Accessory if USB accessory is not null.
     * If the user has not granted the BLUETOOTH_CONNECT permission,
     * we can request the USB Accessory permission to satisfy the requirements for
     * FOREGROUND_SERVICE_CONNECTED_DEVICE and can start our service and allow
     * it to enter the foreground. FOREGROUND_SERVICE_CONNECTED_DEVICE is a requirement
     * in Android 14
     */
    private void requestUsbAccessory(Context context) {
        UsbManager manager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        if (manager.getAccessoryList() == null) {
            return;
        }
        PendingIntent mPermissionIntent = PendingIntent.getBroadcast(context, 0, new Intent(ACTION_USB_PERMISSION), PendingIntent.FLAG_IMMUTABLE);
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            context.registerReceiver(mUsbReceiver, filter, Context.RECEIVER_EXPORTED);
        }
        for (final UsbAccessory usbAccessory : manager.getAccessoryList()) {
            manager.requestPermission(usbAccessory, mPermissionIntent);
        }
    }
}