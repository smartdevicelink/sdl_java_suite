package com.hellosdl.sdl;



import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class SdlReceiver extends com.smartdevicelink.transport.SdlBroadcastReceiver {
	private static final String TAG = "BCast Receiver";

	@Override
	public Class defineLocalSdlRouterClass() {
		return SdlRouterService.class;
	}

	@Override
	public void onSdlEnabled(Context context, Intent intent) {
		Log.e(TAG, "SDL Enabled");
		intent.setClass(context, SdlService.class);
		context.startService(intent);
	}



}
