package com.sdl.hellosdlandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.smartdevicelink.transport.SdlBroadcastReceiver;
import com.smartdevicelink.transport.SdlRouterService;
import com.smartdevicelink.transport.TransportConstants;

public class SdlReceiver  extends SdlBroadcastReceiver {
	private static final String TAG = "SdlBroadcastReciever";
	public static final String RECONNECT_LANG_CHANGE = "RECONNECT_LANG_CHANGE";

	@Override
	public void onSdlEnabled(Context context, Intent intent) {
		Log.d(TAG, "SDL Enabled");
		intent.setClass(context, SdlService.class);

		// SdlService needs to be foregrounded in Android O and above
		// This will prevent apps in the background from crashing when they try to start SdlService
		// Because Android O doesn't allow background apps to start background services
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			context.startForegroundService(intent);
		} else {
			context.startService(intent);
		}
	}

	@Override
	public Class<? extends SdlRouterService> defineLocalSdlRouterClass() {
		return com.sdl.hellosdlandroid.SdlRouterService.class;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent); // Required if overriding this method

		if (intent != null) {
			String action = intent.getAction();
			if (action != null){
				if(action.equalsIgnoreCase(TransportConstants.START_ROUTER_SERVICE_ACTION)) {
					if (intent.getBooleanExtra(RECONNECT_LANG_CHANGE, false)) {
						onSdlEnabled(context, intent);
					}
				}
			}
		}
	}
}