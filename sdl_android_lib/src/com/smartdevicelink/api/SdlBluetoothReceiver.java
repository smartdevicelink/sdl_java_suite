package com.smartdevicelink.api;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SdlBluetoothReceiver extends BroadcastReceiver{

    private static final String TAG = SdlBluetoothReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent == null || intent.getAction() == null) return;
        Log.i(TAG, "Bluetooth Action: " + intent.getAction());
        if(intent.getAction().compareTo(BluetoothDevice.ACTION_ACL_CONNECTED) == 0){
            SdlManager.getInstance().onBluetoothConnected();
        }
    }
}
