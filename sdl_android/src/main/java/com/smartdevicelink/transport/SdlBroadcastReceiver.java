/*
 * Copyright (c) 2018 Livio, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the Livio Inc. nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package com.smartdevicelink.transport;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Parcelable;
import android.util.Log;

import com.smartdevicelink.R;
import com.smartdevicelink.transport.RouterServiceValidator.TrustedListCallback;
import com.smartdevicelink.transport.enums.TransportType;
import com.smartdevicelink.util.AndroidTools;
import com.smartdevicelink.util.SdlAppInfo;
import com.smartdevicelink.util.ServiceFinder;

import java.util.List;
import java.util.Locale;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.smartdevicelink.transport.TransportConstants.FOREGROUND_EXTRA;

public abstract class SdlBroadcastReceiver extends BroadcastReceiver{
	
	private static final String TAG = "Sdl Broadcast Receiver";

	protected static final String SDL_ROUTER_SERVICE_CLASS_NAME 			= "sdlrouterservice";
	
	public static final String LOCAL_ROUTER_SERVICE_EXTRA					= "router_service";
	public static final String LOCAL_ROUTER_SERVICE_DID_START_OWN			= "did_start";
	
	public static final String TRANSPORT_GLOBAL_PREFS 						= "SdlTransportPrefs"; 
	public static final String IS_TRANSPORT_CONNECTED						= "isTransportConnected"; 
		
	public static Vector<ComponentName> runningBluetoothServicePackage = null;

    @SuppressWarnings("rawtypes")
	private static Class localRouterClass;

    private static final Object QUEUED_SERVICE_LOCK = new Object();
    private static ComponentName queuedService = null;
	
	public int getRouterServiceVersion(){
		return SdlRouterService.ROUTER_SERVICE_VERSION_NUMBER;	
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		//Log.i(TAG, "Sdl Receiver Activated");
		final String action = intent.getAction();
		BluetoothDevice device = null;

		if(action.equalsIgnoreCase(Intent.ACTION_PACKAGE_ADDED)
				|| action.equalsIgnoreCase(Intent.ACTION_PACKAGE_REPLACED)){
			//The package manager has sent out a new broadcast. 
			RouterServiceValidator.invalidateList(context);
			return;
		}
		
        if(!(action.equalsIgnoreCase(BluetoothDevice.ACTION_ACL_CONNECTED)
        		|| action.equalsIgnoreCase(USBTransport.ACTION_USB_ACCESSORY_ATTACHED)
        		|| action.equalsIgnoreCase(TransportConstants.START_ROUTER_SERVICE_ACTION))){
        	//We don't want anything else here if the child class called super and has different intent filters
        	//Log.i(TAG, "Unwanted intent from child class");
        	return;
        }
        
        if(action.equalsIgnoreCase(USBTransport.ACTION_USB_ACCESSORY_ATTACHED)){
        	Log.d(TAG, "Usb connected");
        	intent.setAction(null);
			onSdlEnabled(context, intent);
			return;
        }

		if(intent.hasExtra(BluetoothDevice.EXTRA_DEVICE)){	//Grab the bluetooth device if available
			device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
		}

		boolean didStart = false;
		if (localRouterClass == null){
			localRouterClass = defineLocalSdlRouterClass();
			// we need to check this again because for USB apps, the returned class can still be null
			if (localRouterClass != null) {

				// Check if the service declaration in AndroidManifest has the intent-filter action specified correctly
				boolean serviceFilterHasAction = false;
				String className = localRouterClass.getName();
				List<SdlAppInfo> services = AndroidTools.querySdlAppInfo(context, null);
				for (SdlAppInfo sdlAppInfo : services) {
					if(sdlAppInfo != null && sdlAppInfo.getRouterServiceComponentName() != null && className.equals((sdlAppInfo.getRouterServiceComponentName().getClassName()))){
						serviceFilterHasAction = true;
						break;
					}
				}
				if (!serviceFilterHasAction){
					Log.e(TAG, "WARNING: This application has not specified its intent-filter for the SdlRouterService. THIS WILL THROW AN EXCEPTION IN FUTURE RELEASES!!");
				}

				// Check if the service declaration in AndroidManifest has the router service version metadata specified correctly
				ResolveInfo info = context.getPackageManager().resolveService(new Intent(context, localRouterClass), PackageManager.GET_META_DATA);
				if (info != null) {
					if (info.serviceInfo.metaData == null || !info.serviceInfo.metaData.containsKey(context.getString(R.string.sdl_router_service_version_name))) {
						Log.e(TAG, "WARNING: This application has not specified its metadata tags for the SdlRouterService. THIS WILL THROW AN EXCEPTION IN FUTURE RELEASES!!");
					}
				} else {
					Log.e(TAG, "WARNING: This application has not specified its SdlRouterService correctly in the manifest. THIS WILL THROW AN EXCEPTION IN FUTURE RELEASES!!");
				}
			}
		}

		if(localRouterClass != null && localRouterClass.getName().equalsIgnoreCase(com.smartdevicelink.transport.SdlRouterService.class.getName())){
			Log.e(TAG, "You cannot use the default SdlRouterService class, it must be extended in your project. THIS WILL THROW AN EXCEPTION IN FUTURE RELEASES!!");
		}

		//This will only be true if we are being told to reopen our SDL service because SDL is enabled
		if(action.equalsIgnoreCase(TransportConstants.START_ROUTER_SERVICE_ACTION)){ 
			if(intent.hasExtra(TransportConstants.START_ROUTER_SERVICE_SDL_ENABLED_EXTRA)){	
				if(intent.getBooleanExtra(TransportConstants.START_ROUTER_SERVICE_SDL_ENABLED_EXTRA, false)){
					String packageName = intent.getStringExtra(TransportConstants.START_ROUTER_SERVICE_SDL_ENABLED_APP_PACKAGE);
					final ComponentName componentName = intent.getParcelableExtra(TransportConstants.START_ROUTER_SERVICE_SDL_ENABLED_CMP_NAME);
					if(componentName!=null){
						final Intent finalIntent = intent;
						final Context finalContext = context;
						RouterServiceValidator.createTrustedListRequest(context, false, new TrustedListCallback(){
							@Override
							public void onListObtained(boolean successful) {
								//Log.v(TAG, "SDL enabled by router service from " + packageName + " compnent package " + componentName.getPackageName()  + " - " + componentName.getClassName());
								//List obtained. Let's start our service
								queuedService = componentName;
								finalIntent.setAction("com.sdl.noaction"); //Replace what's there so we do go into some unintended loop
								String  transportType = finalIntent.getStringExtra(TransportConstants.START_ROUTER_SERVICE_TRANSPORT_CONNECTED);
								if(transportType!= null ){
									if(TransportType.USB.toString().equals(transportType)){
										finalIntent.putExtra(UsbManager.EXTRA_ACCESSORY, (Parcelable)null);
									}
								}
								onSdlEnabled(finalContext, finalIntent);
							}
							
						});
					}
					
				}else{
					//This was previously not hooked up, so let's leave it commented out
					//onSdlDisabled(context);
				}
				return;
			}else if(intent.getBooleanExtra(TransportConstants.PING_ROUTER_SERVICE_EXTRA, false)){
				//We were told to wake up our router services
				boolean altServiceWake = intent.getBooleanExtra(TransportConstants.BIND_REQUEST_TYPE_ALT_TRANSPORT, false);
				didStart = wakeUpRouterService(context, false,altServiceWake,device );
				
			}
		}


	    Log.d(TAG, "Check for local router");
	    if(localRouterClass!=null){ //If there is a supplied router service lets run some logic regarding starting one
	    	
	    	if(!didStart){Log.d(TAG, "attempting to wake up router service");
	    		didStart = wakeUpRouterService(context, true,false, device);
	    	}

	    	//So even though we started our own version, on some older phones we find that two services are started up so we want to make sure we send our version that we are working with
	    	//We will send it an intent with the version number of the local instance and an intent to start this instance
	    	
	    	Intent serviceIntent =  new Intent(context, localRouterClass);
	    	SdlRouterService.LocalRouterService self = SdlRouterService.getLocalRouterService(serviceIntent, serviceIntent.getComponent());
	    	Intent restart = new Intent(SdlRouterService.REGISTER_NEWER_SERVER_INSTANCE_ACTION);
	    	restart.putExtra(LOCAL_ROUTER_SERVICE_EXTRA, self);
	    	restart.putExtra(LOCAL_ROUTER_SERVICE_DID_START_OWN, didStart);
	    	context.sendBroadcast(restart);
	    }
	}

	private boolean wakeUpRouterService(final Context context, final boolean ping, final boolean altTransportWake, final BluetoothDevice device){
			new ServiceFinder(context, context.getPackageName(), new ServiceFinder.ServiceFinderCallback() {
				@Override
				public void onComplete(Vector<ComponentName> routerServices) {
					runningBluetoothServicePackage = new Vector<ComponentName>();
					runningBluetoothServicePackage.addAll(routerServices);
					if (runningBluetoothServicePackage.isEmpty()) {
						//If there isn't a service running we should try to start one
						//We will try to sort the SDL enabled apps and find the one that's been installed the longest
						Intent serviceIntent;
						List<SdlAppInfo> sdlAppInfoList = AndroidTools.querySdlAppInfo(context, new SdlAppInfo.BestRouterComparator());
						if (sdlAppInfoList != null && !sdlAppInfoList.isEmpty()) {
							serviceIntent = new Intent();
							serviceIntent.setComponent(sdlAppInfoList.get(0).getRouterServiceComponentName());
						} else{
							Log.d(TAG, "No SDL Router Services found");
							Log.d(TAG, "WARNING: This application has not specified its SdlRouterService correctly in the manifest. THIS WILL THROW AN EXCEPTION IN FUTURE RELEASES!!");
							return;
						}
						if (altTransportWake) {
							serviceIntent.setAction(TransportConstants.BIND_REQUEST_TYPE_ALT_TRANSPORT);
						}
						if(device != null){
							serviceIntent.putExtra(BluetoothDevice.EXTRA_DEVICE, device);
						}
						try {
							if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
								context.startService(serviceIntent);
							}else {
								serviceIntent.putExtra(FOREGROUND_EXTRA, true);
								context.startForegroundService(serviceIntent);

							}
							//Make sure to send this out for old apps to close down
							SdlRouterService.LocalRouterService self = SdlRouterService.getLocalRouterService(serviceIntent, serviceIntent.getComponent());
							Intent restart = new Intent(SdlRouterService.REGISTER_NEWER_SERVER_INSTANCE_ACTION);
							restart.putExtra(LOCAL_ROUTER_SERVICE_EXTRA, self);
							restart.putExtra(LOCAL_ROUTER_SERVICE_DID_START_OWN, true);
							context.sendBroadcast(restart);

						} catch (SecurityException e) {
							Log.e(TAG, "Security exception, process is bad");
						}
					} else {
						if (altTransportWake) {
							wakeRouterServiceAltTransport(context);
							return;
						}else{
							for(ComponentName service : runningBluetoothServicePackage){
								pingRouterService(context,service.getPackageName(),service.getClassName());
							}
						}
						return;
					}
				}
			});
			return true;
	}

	private void wakeRouterServiceAltTransport(Context context){
		Intent serviceIntent = new Intent();
		serviceIntent.setAction(TransportConstants.BIND_REQUEST_TYPE_ALT_TRANSPORT);
		for (ComponentName compName : runningBluetoothServicePackage) {
			serviceIntent.setComponent(compName);
			context.startService(serviceIntent);

		}
	}

	/**
	 * Determines if an instance of the Router Service is currently running on the device.<p>
	 * <b>Note:</b> This method no longer works on Android Oreo or newer
	 * @param context A context to access Android system services through.
	 * @param pingService Set this to true if you want to make sure the service is up and listening to bluetooth
	 * @return True if a SDL Router Service is currently running, false otherwise.
	 */
	private static boolean isRouterServiceRunning(Context context, boolean pingService){
		if(context == null){
			Log.e(TAG, "Can't look for router service, context supplied was null");
			return false;
		}
		if (runningBluetoothServicePackage == null) {
			runningBluetoothServicePackage = new Vector<ComponentName>();
		} else {
			runningBluetoothServicePackage.clear();
		}
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		manager.getRunningAppProcesses();
		List<RunningServiceInfo> runningServices = null;
		try {
			runningServices = manager.getRunningServices(Integer.MAX_VALUE);
		} catch (NullPointerException e) {
			Log.e(TAG, "Can't get list of running services");
			return false;
		}
		for (RunningServiceInfo service : runningServices) {
			//We will check to see if it contains this name, should be pretty specific
			//Log.d(TAG, "Found Service: "+ service.service.getClassName());
			if ((service.service.getClassName()).toLowerCase(Locale.US).contains(SDL_ROUTER_SERVICE_CLASS_NAME) && AndroidTools.isServiceExported(context, service.service)) {
				runningBluetoothServicePackage.add(service.service);    //Store which instance is running
				if (pingService) {
					pingRouterService(context, service.service.getPackageName(), service.service.getClassName());
				}
			}
		}
		return runningBluetoothServicePackage.size() > 0;
		
	}

	/**
	 * Attempts to ping a running router service
	 * @param context A context to access Android system services through.
	 * @param packageName Package name for service to ping
	 * @param className Class name for service to ping
	 */
	protected static void pingRouterService(Context context, String packageName, String className){
		if(context == null || packageName == null || className == null){
			return;
		}
		try{
			Intent intent = new Intent();
			intent.setClassName(packageName, className);
			intent.putExtra(TransportConstants.PING_ROUTER_SERVICE_EXTRA, true);
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
				intent.putExtra(FOREGROUND_EXTRA, true);
				context.startForegroundService(intent);
			}else {
				context.startService(intent);
			}
		}catch(SecurityException e){
			Log.e(TAG, "Security exception, process is bad");
			// This service could not be started
		}
	}

	/**
	 * This call will reach out to all SDL related router services to check if they're connected. If a the router service is connected, it will react by pinging all clients. This receiver will then
	 * receive that ping and if the router service is trusted, the onSdlEnabled method will be called. 
	 * @param context
	 */
	public static void queryForConnectedService(final Context context){
		//Leverage existing call. Include ping bit
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
			ServiceFinder finder = new ServiceFinder(context, context.getPackageName(), new ServiceFinder.ServiceFinderCallback() {
				@Override
				public void onComplete(Vector<ComponentName> routerServices) {
					runningBluetoothServicePackage = new Vector<ComponentName>();
					runningBluetoothServicePackage.addAll(routerServices);
					requestTransportStatus(context,null,true,false);
				}
			});

		}else{
			requestTransportStatus(context,null,true,true);
		}
	}
	/**
	 * If a Router Service is running, this method determines if that service is connected to a device over some form of transport.
	 * @param context A context to access Android system services through. If null is passed, this will always return false
	 * @param callback Use this callback to find out if the router service is connected or not. 
	 */
	@Deprecated
	public static void requestTransportStatus(Context context, final SdlRouterStatusProvider.ConnectedStatusCallback callback){
		requestTransportStatus(context,callback,false, true);
	}

	private static void requestTransportStatus(Context context, final SdlRouterStatusProvider.ConnectedStatusCallback callback, final boolean triggerRouterServicePing, final boolean lookForServices){
		if(context == null){
			if(callback!=null){
				callback.onConnectionStatusUpdate(false, null,context);
			}
			return;
		}
		if((!lookForServices || isRouterServiceRunning(context,false)) && !runningBluetoothServicePackage.isEmpty()){	//So there is a service up, let's see if it's connected
			final ConcurrentLinkedQueue<ComponentName> list = new ConcurrentLinkedQueue<ComponentName>(runningBluetoothServicePackage);
			final SdlRouterStatusProvider.ConnectedStatusCallback sdlBrCallback = new SdlRouterStatusProvider.ConnectedStatusCallback() {	

				@Override
				public void onConnectionStatusUpdate(boolean connected, ComponentName service,Context context) {
					if(!connected && !list.isEmpty()){
						SdlRouterStatusProvider provider = new SdlRouterStatusProvider(context,list.poll(), this);
						if(triggerRouterServicePing){provider.setFlags(TransportConstants.ROUTER_STATUS_FLAG_TRIGGER_PING);	}
						provider.checkIsConnected();
					}else{
						if(service!=null){
							Log.d(TAG, service.getPackageName() + " is connected = " + connected);
						}else{
							Log.d(TAG,"No service is connected/running");
						}
						if(callback!=null){
							callback.onConnectionStatusUpdate(connected, service,context);
						}
						list.clear();
					}

				}
			};
			final SdlRouterStatusProvider provider = new SdlRouterStatusProvider(context,list.poll(),sdlBrCallback);
			if(triggerRouterServicePing){
				provider.setFlags(TransportConstants.ROUTER_STATUS_FLAG_TRIGGER_PING);
			}
			//Lets ensure we have a current list of trusted router services
			RouterServiceValidator.createTrustedListRequest(context, false, new TrustedListCallback(){
				@Override
				public void onListObtained(boolean successful) {
					//This will kick off our check of router services
					provider.checkIsConnected();
				}
			});
				
		}else{
			Log.w(TAG, "Router service isn't running, returning false.");
			if(isBluetoothConnected()){
				Log.d(TAG, "Bluetooth is connected. Attempting to start Router Service");
				Intent serviceIntent = new Intent();
				serviceIntent.setAction(TransportConstants.START_ROUTER_SERVICE_ACTION);
				serviceIntent.putExtra(TransportConstants.PING_ROUTER_SERVICE_EXTRA, true);
	    		AndroidTools.sendExplicitBroadcast(context,serviceIntent,null);
			}
			if(callback!=null){
				callback.onConnectionStatusUpdate(false, null,context);
			}
		}
	}

	@SuppressWarnings({"MissingPermission"})
	private static boolean isBluetoothConnected() {
		BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if(bluetoothAdapter != null && bluetoothAdapter.isEnabled()) {
			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
				int  a2dpState  = bluetoothAdapter.getProfileConnectionState(BluetoothProfile.A2DP);
				int headSetState  = bluetoothAdapter.getProfileConnectionState(BluetoothProfile.HEADSET);

				return ((a2dpState == BluetoothAdapter.STATE_CONNECTED || a2dpState == BluetoothAdapter.STATE_CONNECTING)
						&& (headSetState == BluetoothAdapter.STATE_CONNECTED || headSetState == BluetoothAdapter.STATE_CONNECTING));
			}else{
				return true;
			}
		}
		return false;
	}

	public static ComponentName consumeQueuedRouterService(){
		synchronized(QUEUED_SERVICE_LOCK){
			ComponentName retVal = queuedService;
			queuedService = null;
			return retVal;
		}
	}
	
	/**
	 * We need to define this for local copy of the Sdl Router Service class.
	 * It will be the main point of connection for Sdl enabled apps
	 * @return Return the local copy of SdlRouterService.class
	 * {@inheritDoc}
	 */
	public abstract Class<? extends SdlRouterService> defineLocalSdlRouterClass();

	
	
	/**
	 * 
	 * The developer will need to define exactly what should happen when Sdl is enabled.
	 * This method will only get called when a Sdl  session is initiated.
	 * <p> The most useful code here would be to start the activity or service that handles most of the Livio 
	 * Connect code.
	 * @param context this is the context that was passed to this receiver when it was called.
	 * @param intent this is the intent that alerted this broadcast. Make sure to pass all extra it came with to your service/activity
	 * {@inheritDoc}
	 */
	public abstract void onSdlEnabled(Context context, Intent intent);
	
	//public abstract void onSdlDisabled(Context context); //Removing for now until we're able to abstract from developer


}
