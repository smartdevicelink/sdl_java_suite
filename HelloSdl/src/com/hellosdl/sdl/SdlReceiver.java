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
	public void onSdlEnabled(Context context) {
		Log.e(TAG, "SDL Enabled");
		Intent startIntent = new Intent(context, SdlService.class);
		startIntent.putExtra(FORCE_TRANSPORT_CONNECTED, true);
		context.startService(startIntent);
	}

	@Override
	public void onSdlDisabled(Context context) {
		Intent startIntent = new Intent(context, SdlService.class);
		//context.stopService(startIntent); //TODO maybe startservice with a flag instead?
		
	}
	


}
