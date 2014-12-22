package com.smartdevicelink.transport;

import com.c4.android.transport.C4BroadcastReceiver;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public abstract class SdlBroadcastReceiver extends C4BroadcastReceiver {
	
	private static final String TAG = "BCast Receiver";
	protected static final String SDL_ROUTER_SERVICE_CLASS_NAME 				= "sdlrouterservice";
	public static final String FORCE_TRANSPORT_CONNECTED						= "force_connect"; //This is legacy, do not refactor this. Or I will punch you.


	@Override
	public Class defineLocalRouterServiceClass() {
		return defineLocalSdlRouterClass();
	}
	
	@Override
	public int getRouterServiceVersion(){
		return SdlRouterService.ROUTER_SERVICE_VERSION_NUMBER;	
	}
	
	@Override
	public String getRouterServiceName() {
		return SDL_ROUTER_SERVICE_CLASS_NAME;
	}
	
	
	
	@Override
	public String getNewServiceCheckString() {
		return SdlRouterService.REGISTER_NEWER_SERVER_INSTANCE_ACTION;
	}

	@Override
	public void onProtocolEnabled(Context context){
		onSdlEnabled(context);
	}
	
	
	@Override
	public void onProtocolDisabled(Context context) {
		// TODO Auto-generated method stub
		
	}
	
	

	/**
	 * We need to define this for local copy of the Sdl Bluetooth Service class.
	 * It will be the main point of connection for Sdl Connected apps
	 * @return Return the local copy of SdlRouterService.class
	 * {@inheritDoc}
	 */
	public abstract Class defineLocalSdlRouterClass();

	
	
	/**
	 * 
	 * The developer will need to define exactly what should happen when Sdl is enabled.
	 * This method will only get called when a Sdl  session is initiated.
	 * <p> The most useful code here would be to start the activity or service that handles most of the Livio 
	 * Connect code.
	 * @param context this is the context that was passed to this receiver when it was called.
	 * {@inheritDoc}
	 */
	public abstract void onSdlEnabled(Context context);
	
	public abstract void onSdlDisabled(Context context);


}
