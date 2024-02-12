package com.smartdevicelink;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Added so we pass IntegrationValidator when running unit test.
 */
public class TestSdlReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
    }
}
