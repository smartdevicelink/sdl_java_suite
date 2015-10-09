package com.smartdevicelink.lifecycle;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public final class SDLBluetoothReceiver extends BroadcastReceiver {

    private static final String TAG = SDLBluetoothReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, intent.getAction());
        if(intent.getAction().compareTo(BluetoothDevice.ACTION_ACL_CONNECTED) == 0){
            SdlManager.getInstance().connect();
        }
    }
}
