package com.smartdevicelink.lifecycle;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public final class SDLBluetoothReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().compareTo(BluetoothDevice.ACTION_ACL_CONNECTED) == 0){
            SdlManager.getInstance().connect();
        }
    }
}
