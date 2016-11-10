package com.smartdevicelink.transport;

import java.util.Locale;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.smartdevicelink.util.AndroidTools;
import com.smartdevicelink.transport.RouterServiceValidator.TrustedListCallback;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public abstract class SdlBroadcastReceiver extends BroadcastReceiver{
	
	private static final String TAG = "Sdl Broadcast Receiver";

	private static final String BOOT_COMPLETE = "android.intent.action.BOOT_COMPLETED";
	private static final String ACL_CONNECTED = "android.bluetooth.device.action.ACL_CONNECTED";
	private static final String STATE_CHANGED = "android.bluetooth.adapter.action.STATE_CHANGED" ;
	
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
		String action = intent.getAction();
		
		if(action.equalsIgnoreCase(Intent.ACTION_PACKAGE_ADDED)
				|| action.equalsIgnoreCase(Intent.ACTION_PACKAGE_REPLACED)){
			//The package manager has sent out a new broadcast. 
			RouterServiceValidator.invalidateList(context);
			return;
		}
		
        if(!(action.equalsIgnoreCase(BOOT_COMPLETE)
        		|| action.equalsIgnoreCase(ACL_CONNECTED)
        		|| action.equalsIgnoreCase(STATE_CHANGED)
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
        
	    boolean didStart = false;
	    localRouterClass = defineLocalSdlRouterClass();
        
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
								RouterServiceValidator vlad = new RouterServiceValidator(finalContext,componentName);
								if(vlad.validate()){
									//Log.d(TAG, "Router service trusted!");
									queuedService = componentName;
									finalIntent.setAction("com.sdl.noaction"); //Replace what's there so we do go into some unintended loop
									onSdlEnabled(finalContext, finalIntent);
								}else{
									Log.w(TAG, "RouterService was not trusted. Ignoring intent from : "+ componentName.getClassName());
								}
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
				didStart = wakeUpRouterService(context, false,altServiceWake );
				
			}

		}
		
	    if (intent.getAction().contains("android.bluetooth.adapter.action.STATE_CHANGED")){
	    	int state = intent.getIntExtra("android.bluetooth.adapter.extra.STATE",-1);
	    		if (state == BluetoothAdapter.STATE_OFF || 
	    			state == BluetoothAdapter.STATE_TURNING_OFF ){
	    			//onProtocolDisabled(context);
	    			//Let's let the service that is running manage what to do for this
	    			//If we were to do it here, for every instance of this BR it would send
	    			//an intent to stop service, where it's only one that is needed.
	    			return;
	    		}else if(state == BluetoothAdapter.STATE_TURNING_ON){
	    			//We started bluetooth, we should check for a new valid router list
	    			RouterServiceValidator.createTrustedListRequest(context,true);
	    		}
	    }

	    if(localRouterClass!=null){ //If there is a supplied router service lets run some logic regarding starting one
	    	
	    	if(!didStart){
	    		didStart = wakeUpRouterService(context, true,false);
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
	
	private boolean wakeUpRouterService(Context context, boolean ping, boolean altTransportWake){
    	if(!isRouterServiceRunning(context, ping)){  
    		//If there isn't a service running we should try to start one
    		//The under class should have implemented this....
    		
    		//So let's start up our service since no copy is running
    		Intent serviceIntent = new Intent(context, localRouterClass);
    		if(altTransportWake){
    			serviceIntent.setAction(TransportConstants.BIND_REQUEST_TYPE_ALT_TRANSPORT);
    		}
    		context.startService(serviceIntent);
    		return true;
    	}else{
    		if(altTransportWake &&  runningBluetoothServicePackage!=null && runningBluetoothServicePackage.size()>0){
    			Intent serviceIntent = new Intent();
        		serviceIntent.setAction(TransportConstants.BIND_REQUEST_TYPE_ALT_TRANSPORT);
        		//context.startService(serviceIntent);
        		for(ComponentName compName: runningBluetoothServicePackage){
        			serviceIntent.setComponent(compName);
        			context.startService(serviceIntent);

        		}
        		return true;
    		}
    		return false;
    	}
	}

	/**
	 * Determines if an instance of the Router Service is currently running on the device. 
	 * @param context A context to access Android system services through.
	 * @param pingService Set this to true if you want to make sure the service is up and listening to bluetooth
	 * @return True if a SDL Router Service is currently running, false otherwise.
	 */
	private static boolean isRouterServiceRunning(Context context, boolean pingService){
		if(context == null){
			Log.e(TAG, "Can't look for router service, context supplied was null");
			return false;
		}
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		if(runningBluetoothServicePackage==null){
			runningBluetoothServicePackage = new Vector<ComponentName>();
		}else{
			runningBluetoothServicePackage.clear();
		}
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	    	//We will check to see if it contains this name, should be pretty specific
	    	//Log.d(TAG, "Found Service: "+ service.service.getClassName());
	    	if ((service.service.getClassName()).toLowerCase(Locale.US).contains(SDL_ROUTER_SERVICE_CLASS_NAME) && AndroidTools.isServiceExported(context, service.service)) {
	    		
	    		runningBluetoothServicePackage.add(service.service);	//Store which instance is running
	            if(pingService){
	            	Intent intent = new Intent();
	            	intent.setClassName(service.service.getPackageName(), service.service.getClassName());
	            	intent.putExtra(TransportConstants.PING_ROUTER_SERVICE_EXTRA, pingService);
	            	context.startService(intent);
	            }
	        }
	    }
		
		return runningBluetoothServicePackage.size() > 0;
		
	}

	/**
	 * This call will reach out to all SDL related router services to check if they're connected. If a the router service is connected, it will react by pinging all clients. This receiver will then
	 * receive that ping and if the router service is trusted, the onSdlEnabled method will be called. 
	 * @param context
	 */
	public static void queryForConnectedService(Context context){
		//Leverage existing call. Include ping bit
		requestTransportStatus(context,null,true);
	}
	/**
	 * If a Router Service is running, this method determines if that service is connected to a device over some form of transport.
	 * @param context A context to access Android system services through. If null is passed, this will always return false
	 * @param callback Use this callback to find out if the router service is connected or not. 
	 */
	public static void requestTransportStatus(Context context, final SdlRouterStatusProvider.ConnectedStatusCallback callback){
		requestTransportStatus(context,callback,false);
	}

	private static void requestTransportStatus(Context context, final SdlRouterStatusProvider.ConnectedStatusCallback callback, final boolean triggerRouterServicePing){
		if(context == null){
			if(callback!=null){
				callback.onConnectionStatusUpdate(false, null,context);
			}
			return;
		}
		if(isRouterServiceRunning(context,false) && !runningBluetoothServicePackage.isEmpty()){	//So there is a service up, let's see if it's connected
			final ConcurrentLinkedQueue<ComponentName> list = new ConcurrentLinkedQueue<ComponentName>(runningBluetoothServicePackage);
			final SdlRouterStatusProvider.ConnectedStatusCallback sdlBrCallback = new SdlRouterStatusProvider.ConnectedStatusCallback() {	

				@Override
				public void onConnectionStatusUpdate(boolean connected, ComponentName service,Context context) {
					if(!connected && !list.isEmpty()){
						SdlRouterStatusProvider provider = new SdlRouterStatusProvider(context,list.poll(), this);
						if(triggerRouterServicePing){provider.setFlags(TransportConstants.ROUTER_STATUS_FLAG_TRIGGER_PING);	}
						provider.checkIsConnected();
					}else{
						Log.d(TAG, service.getPackageName() + " is connected = " + connected);
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
			if(BluetoothAdapter.getDefaultAdapter()!=null && BluetoothAdapter.getDefaultAdapter().isEnabled()){
				Intent serviceIntent = new Intent();
				serviceIntent.setAction(TransportConstants.START_ROUTER_SERVICE_ACTION);
				serviceIntent.putExtra(TransportConstants.PING_ROUTER_SERVICE_EXTRA, true);
	    		context.sendBroadcast(serviceIntent);
			}
			if(callback!=null){
				callback.onConnectionStatusUpdate(false, null,context);
			}
		}
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
