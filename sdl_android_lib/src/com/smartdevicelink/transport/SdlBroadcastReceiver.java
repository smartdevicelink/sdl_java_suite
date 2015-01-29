package com.smartdevicelink.transport;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

public abstract class SdlBroadcastReceiver extends BroadcastReceiver{
	
	private static final String TAG = "Sdl Broadcast Receiver";
	private static final String SEND_PACKET_TO_APP_LOCATION_EXTRA_NAME 		= "senderintent";

	protected static final String SDL_ROUTER_SERVICE_CLASS_NAME 			= "sdlrouterservice";

	public static final String FORCE_TRANSPORT_CONNECTED					= "force_connect"; //This is legacy, do not refactor this. Or I will punch you.
	public static final String INTENT_FOR_OTHER_BT_SERVER_INSTANCE_EXTRA	= "otherinstanceintent";
	public static final String LOCAL_BT_SERVER_VERSION_NUMBER_EXTRA 		= "versionextra";
	public static final String TRANSPORT_GLOBAL_PREFS 						= "LivioBluetooth"; //This is legacy, do not refactor this. Or I will punch you.
	public static final String IS_TRANSPORT_CONNECTED						= "isBluetoothConnected"; //This is legacy, do not refactor this. Or I will punch you.
		
	public static String runningBluetoothServicePackage = null;

    @SuppressWarnings("rawtypes")
	private static Class localRouterClass;

	
	public int getRouterServiceVersion(){
		return SdlRouterService.ROUTER_SERVICE_VERSION_NUMBER;	
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(TAG, "Sdl Receiver Activated");

		//This will only be true if we are being told to reopen our SDL service because SDL is enabled
		if(intent.getAction().contains(TransportConstants.START_ROUTER_SERVICE_ACTION_SUFFIX)){  //TODO make sure this works with only the suffix
			if(intent.hasExtra(TransportConstants.START_ROUTER_SERVICE_LIVIOCONNECT_ENABLED_EXTRA)){	
			if(intent.getBooleanExtra(TransportConstants.START_ROUTER_SERVICE_LIVIOCONNECT_ENABLED_EXTRA, false)){
					onSdlEnabled(context);
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
	    			state == BluetoothAdapter.STATE_TURNING_OFF )
	    		{
	    		//onProtocolDisabled(context);
	    		//Let's let the service that is running manage what to do for this
	    		//If we were to do it here, for every instance of this BR it would send
	    		//an intent to stop service, where it's only one that is needed.
	    		return;
	    		}
	    }
	    
		//Basically if the service is already running somewhere, let it know what version this app is running
		if(isRouterServiceRunning(context)){
			//So we know an instance is running, but we need to make sure it is the most upto date
			//We will send it an intent with the version number of the local instance and an intent to start this instance
			Log.i(TAG, "An instance of the Router Service is already running");
			localRouterClass = defineLocalSdlRouterClass();
			Intent serviceIntent =  new Intent(context, localRouterClass);
			Intent restart = new Intent(getNewServiceCheckString());
			restart.putExtra(INTENT_FOR_OTHER_BT_SERVER_INSTANCE_EXTRA, serviceIntent);
			restart.putExtra(LOCAL_BT_SERVER_VERSION_NUMBER_EXTRA, getRouterServiceVersion());
			context.sendBroadcast(restart);
			return;
		}
	    
	    
		Log.i(TAG, "Attempting to start an instance of the Router Service");
	    //The under class should have implemented this....
	    localRouterClass = defineLocalSdlRouterClass();
		//So let's start up our service since no copy is running
		Intent serviceIntent = new Intent(context, localRouterClass);
		if(intent.getAction().contains(TransportConstants.START_ROUTER_SERVICE_ACTION_SUFFIX)){ //TODO make sure this works
			Log.i(TAG, "Adding reply address to starting intent of Router Service: "+intent.getStringExtra(SEND_PACKET_TO_APP_LOCATION_EXTRA_NAME));
			//serviceIntent.putExtra(SEND_PACKET_TO_APP_LOCATION_EXTRA_NAME, intent.getStringExtra(SEND_PACKET_TO_APP_LOCATION_EXTRA_NAME));
			if(serviceIntent!=null 
					&& intent!=null 
					&& intent.getExtras()!=null){
				serviceIntent.putExtras(intent.getExtras());//Add all the extras
			}
		}
		context.startService(serviceIntent);
		

	}
	
	/**
	 * Determines if an instance of the Router Service is currently running on the device. 
	 * @param context A context to access Android system services through.
	 * @return True if a Livio Bluetooth Service is currently running, false otherwise.
	 */
	private static boolean isRouterServiceRunning(Context context){
		Log.d(TAG, "Looking for Service: "+ SDL_ROUTER_SERVICE_CLASS_NAME);
		ActivityManager manager = (ActivityManager) context.getSystemService("activity");
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	    	//We will check to see if it contains this name, should be pretty specific
	    	//Log.d(TAG, "Found Service: "+ service.service.getClassName());
	    	if ((service.service.getClassName()).toLowerCase().contains(SDL_ROUTER_SERVICE_CLASS_NAME)) {
	    		runningBluetoothServicePackage = service.service.getPackageName();	//Store which instance is running
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
		if(isRouterServiceRunning(context)){	//So there is a service up, let's see if it's connected
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
	            Log.w(TAG, "Is Connected? Returning " + connected);
				return connected;
			} catch (NameNotFoundException e) {
				e.printStackTrace();
				return false;
			}

		}else{
			Log.w("C4", "Router service isn't running, returning false.");
		}

		return false;
	}

	
	
	public String getNewServiceCheckString() {
		return SdlRouterService.REGISTER_NEWER_SERVER_INSTANCE_ACTION;
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
