package com.smartdevicelink.transport;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

public abstract class SdlBroadcastReceiver extends BroadcastReceiver{
	
	private static final String TAG = "Sdl Broadcast Receiver";

	protected static final String SDL_ROUTER_SERVICE_CLASS_NAME 			= "sdlrouterservice";

	public static final String FORCE_TRANSPORT_CONNECTED					= "force_connect"; //This is legacy, do not refactor this. 
	
	public static final String LOCAL_ROUTER_SERVICE_EXTRA					= "router_service";
	public static final String LOCAL_ROUTER_SERVICE_DID_START_OWN			= "did_start";
	
	public static final String TRANSPORT_GLOBAL_PREFS 						= "SdlTransportPrefs"; 
	public static final String IS_TRANSPORT_CONNECTED						= "isTransportConnected"; 
		
	public static String runningBluetoothServicePackage = null;

    @SuppressWarnings("rawtypes")
	private static Class localRouterClass;

	
	public int getRouterServiceVersion(){
		return SdlRouterService.ROUTER_SERVICE_VERSION_NUMBER;	
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		//Log.i(TAG, "Sdl Receiver Activated");
		String action = intent.getAction();

		if(action.equalsIgnoreCase(Intent.ACTION_PACKAGE_ADDED)
				|| action.equalsIgnoreCase(Intent.ACTION_PACKAGE_REPLACED)){
			//The package manager has sent out a new broadcast. 
			RouterServiceValidator.invalidateList(context);
			return;
		}

		//This will only be true if we are being told to reopen our SDL service because SDL is enabled
		if(action.contains(TransportConstants.START_ROUTER_SERVICE_ACTION_SUFFIX)){  //TODO make sure this works with only the suffix
			if(intent.hasExtra(TransportConstants.START_ROUTER_SERVICE_SDL_ENABLED_EXTRA)){	
				if(intent.getBooleanExtra(TransportConstants.START_ROUTER_SERVICE_SDL_ENABLED_EXTRA, false)){
					String packageName = intent.getStringExtra(TransportConstants.START_ROUTER_SERVICE_SDL_ENABLED_APP_PACKAGE);
					ComponentName componentName = intent.getParcelableExtra(TransportConstants.START_ROUTER_SERVICE_SDL_ENABLED_CMP_NAME);
					if(componentName!=null){
						Log.v(TAG, "SDL enabled by router service from " + packageName + " compnent package " + componentName.getPackageName()  + " - " + componentName.getClassName());
						RouterServiceValidator vlad = new RouterServiceValidator(context);
						if(vlad.validate()){
							Log.d(TAG, "Router service trusted!");
							onSdlEnabled(context, componentName);
						}
						Log.e(TAG, "RouterService was not trusted. Ignoring intent from : "+ componentName.getClassName());
					}
					
					
				}else{
					//This was previously not hooked up, so let's leave it commented out
					//onSdlDisabled(context);
				}
				return;
			}

		}

	    if (intent.getAction().contains("android.bluetooth.adapter.action.STATE_CHANGED")){
	      
	    	int state = intent.getExtras().getInt("android.bluetooth.adapter.extra.STATE");
	    		if (state == BluetoothAdapter.STATE_OFF || 
	    			state == BluetoothAdapter.STATE_TURNING_OFF ){
	    			//onProtocolDisabled(context);
	    			//Let's let the service that is running manage what to do for this
	    			//If we were to do it here, for every instance of this BR it would send
	    			//an intent to stop service, where it's only one that is needed.
	    			return;
	    		}else if(state == BluetoothAdapter.STATE_TURNING_ON){
	    			//We started bluetooth, we should check for a new valid router list
	    			Log.d(TAG, "Attempting to get list of approved router services");
	    			RouterServiceValidator.createTrustedListRequest(context,true);
	    		}
	    }
	    boolean didStart = false;
	    localRouterClass = defineLocalSdlRouterClass();
	    if(localRouterClass!=null){ //If there is a supplied router service lets run some logic regarding starting one
	    	
	    	if(!isRouterServiceRunning(context, true)){
	    		//If there isn't a service running we should try to start one
	    		Log.i(TAG, "Attempting to start an instance of the Router Service");
	    		//The under class should have implemented this....
	    		
	    		//So let's start up our service since no copy is running
	    		Intent serviceIntent = new Intent(context, localRouterClass);
	    		if(intent.getAction().contains(TransportConstants.START_ROUTER_SERVICE_ACTION_SUFFIX)){ //TODO make sure this works
	    			//Log.i(TAG, "Adding reply address to starting intent of Router Service: "+intent.getStringExtra(SEND_PACKET_TO_APP_LOCATION_EXTRA_NAME));
	    			if(serviceIntent!=null 
	    					&& intent!=null 
	    					&& intent.getExtras()!=null){
	    				serviceIntent.putExtras(intent.getExtras());//Add all the extras
	    			}
	    		}
	    		context.startService(serviceIntent);
	    		didStart = true;
	    	}else{
	    		Log.i(TAG, "An instance of the Router Service is already running");	    	
	    	}

	    	//So even though we started our own version, on some older phones we find that two services are started up so we want to make sure we send our version that we are working with
	    	//We will send it an intent with the version number of the local instance and an intent to start this instance
	    	
	    	Intent serviceIntent =  new Intent(context, localRouterClass);
	    	SdlRouterService.LocalRouterService self = SdlRouterService.getLocalRouterService(serviceIntent);
	    	Intent restart = new Intent(SdlRouterService.REGISTER_NEWER_SERVER_INSTANCE_ACTION);
	    	restart.putExtra(LOCAL_ROUTER_SERVICE_EXTRA, self);
	    	restart.putExtra(LOCAL_ROUTER_SERVICE_DID_START_OWN, didStart);
	    	context.sendBroadcast(restart);
	    }
	}
	
	/**
	 * Determines if an instance of the Router Service is currently running on the device. 
	 * @param context A context to access Android system services through.
	 * @param pingService Set this to true if you want to make sure the service is up and listening to bluetooth
	 * @return True if a Livio Bluetooth Service is currently running, false otherwise.
	 */
	private static boolean isRouterServiceRunning(Context context, boolean pingService){
		if(context == null){
			Log.e(TAG, "Can't look for router service, context supplied was null");
			return false;
		}
		Log.d(TAG, "Looking for Service: "+ SDL_ROUTER_SERVICE_CLASS_NAME);
		ActivityManager manager = (ActivityManager) context.getSystemService("activity");
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	    	//We will check to see if it contains this name, should be pretty specific
	    	//Log.d(TAG, "Found Service: "+ service.service.getClassName());
	    	if ((service.service.getClassName()).toLowerCase().contains(SDL_ROUTER_SERVICE_CLASS_NAME)) {
	    		runningBluetoothServicePackage = service.service.getPackageName();	//Store which instance is running
	            if(pingService){
	            	Intent intent = new Intent();
	            	intent.setClassName(service.service.getPackageName(), service.service.getClassName());
	            	intent.putExtra(TransportConstants.PING_ROUTER_SERVICE_EXTRA, pingService);
	            	context.startService(intent);
	            }
	    		return true;
	        }
	    }
		
		return false;
		
	}

	/**
	 * If a Router Service is running, this method determines if that service is connected to a device over some form of transport.
	 * @param context A context to access Android system services through.
	 * @return True if a transport connection is established, false otherwise.
	 */
	public static boolean isTransportConnected(Context context){
		if(isRouterServiceRunning(context,false)){	//So there is a service up, let's see if it's connected
			Context con;
			try {
				con = context.createPackageContext(runningBluetoothServicePackage, 0);
	            if(con==null ){
	            	Log.w(TAG, "Unable to check for service connection. Returning false. "+runningBluetoothServicePackage);
	            	return false; // =( well that sucks.
	            }
				SharedPreferences pref = con.getSharedPreferences(
	            		con.getPackageName()+TRANSPORT_GLOBAL_PREFS , 4);
	            boolean connected = pref.getBoolean(IS_TRANSPORT_CONNECTED, false);
	           // Log.w(TAG, "Is Connected? Returning " + connected);
				return connected;
			} catch (NameNotFoundException e) {
				e.printStackTrace();
				return false;
			}

		}else{
			Log.w(TAG, "Router service isn't running, returning false.");
		}

		return false;
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
	public abstract void onSdlEnabled(Context context, ComponentName componentName);
	
	//public abstract void onSdlDisabled(Context context); //Removing for now until we're able to abstract from developer


}
