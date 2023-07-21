package com.sdl.hellosdlandroid;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.smartdevicelink.managers.ManagerUtility;
import com.smartdevicelink.transport.SdlBroadcastReceiver;
import com.smartdevicelink.transport.SdlRouterService;
import com.smartdevicelink.transport.TransportConstants;
import com.smartdevicelink.util.DebugTool;

public class SdlReceiver extends SdlBroadcastReceiver {
    private static final String TAG = "SdlBroadcastReceiver";
    PendingIntent pendingIntent;

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
                    if (!ManagerUtility.LoginUtil.hasForegroundServiceTypePermission(context)) {
                        requestUsbAccessory(context);
                        this.pendingIntent = pendingIntent;
                        DebugTool.logInfo(TAG, "Permission missing for ForegroundServiceType connected device.");
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

    private static final String ACTION_USB_PERMISSION =
            "com.android.example.USB_PERMISSION";
    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "Julian onReceive: in on receve for usb");
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbAccessory accessory = (UsbAccessory) intent.getParcelableExtra(UsbManager.EXTRA_ACCESSORY);
                    if(intent.hasExtra(UsbManager.EXTRA_PERMISSION_GRANTED)) {
                        Log.i(TAG, "Julian onReceive: ");
                    }
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        Log.i(TAG, "Julian onReceive: in on receve for usb");

                        if(accessory != null){
                            Log.i(TAG, "Julian onReceive: Accessory permission granted starting sdlService");
                            if (pendingIntent == null){
                                return;
                            }
                            try {
                                Log.i(TAG, "onReceive: sending intent");
                                pendingIntent.send(context, 0, intent);
                            } catch (PendingIntent.CanceledException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    else {
                        Log.d(TAG, "permission denied for accessory " + accessory);
                    }
                }
            }
        }
    };
    private void requestUsbAccessory(Context context) {
        PendingIntent mPermissionIntent = PendingIntent.getBroadcast(context, 0, new Intent(ACTION_USB_PERMISSION), PendingIntent.FLAG_IMMUTABLE);
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.registerReceiver(mUsbReceiver, filter, Context.RECEIVER_EXPORTED);
        }
        UsbManager manager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        for (final UsbAccessory usbAccessory : manager.getAccessoryList()) {
            manager.requestPermission(usbAccessory, mPermissionIntent);
        }
    }
}