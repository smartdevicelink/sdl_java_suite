package com.sdl.hellosdlandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.smartdevicelink.transport.SdlBroadcastReceiver;
import com.smartdevicelink.transport.SdlRouterService;
import com.smartdevicelink.util.DebugTool;

public class SdlReceiver  extends SdlBroadcastReceiver {
	private static final String TAG = "SdlBroadcastReciever";

	@Override
	public void onSdlEnabled(Context context, Intent intent) {
		DebugTool.logInfo(TAG, "SDL Enabled");
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
	}
}