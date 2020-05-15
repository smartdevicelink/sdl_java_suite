package com.sdl.hellosdlandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.smartdevicelink.transport.BaseBroadcastReceiver;
import com.smartdevicelink.transport.BaseRouterService;

public class SdlReceiver  extends BaseBroadcastReceiver {
	private static final String TAG = "SdlBroadcastReciever";

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
	public Class<? extends BaseRouterService> defineLocalSdlRouterClass() {
		return com.sdl.hellosdlandroid.BaseRouterService.class;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent); // Required if overriding this method
	}
}