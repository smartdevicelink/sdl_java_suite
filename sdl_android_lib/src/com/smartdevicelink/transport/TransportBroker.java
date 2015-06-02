package com.smartdevicelink.transport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;


public class TransportBroker {
	
	private static final String TAG = "SdlTransportBroker";

	private final String WHERE_TO_REPLY_PREFIX							 = "com.sdl.android.";
	private static String appId = null,whereToReply = null;// sendPacketAddress = null;
	private Context currentContext = null;
	
	private Object INIT_LOCK = new Object();
	
	private TransportType queuedOnTransportConnect = null;
	
	Messenger routerService = null;
	final Messenger mMessenger = new Messenger(new ClientHandler());

	boolean isBound;
	
	private ServiceConnection routerConnection = new ServiceConnection() {
		
        public void onServiceConnected(ComponentName className, IBinder service) {
        	Log.d(TAG, "Bound to service " + className.toString());
        	routerService = new Messenger(service);
            isBound = true;
            //So we just established our connection
            //TODO register with router service
        }

        public void onServiceDisconnected(ComponentName className) {
        	Log.d(TAG, "UN-Bound to service");
        	routerService = null;
            isBound = false;
        }
    };
    
    private void sendMessageToRouterService(Message message){
    	if(isBound){
    		try {
				routerService.send(message);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
    	}else{
    		Log.e(TAG, "Unable to send message to router service. Not bound.");
    	}
    }
    
    BroadcastReceiver routerDiscoveryReceiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent!=null){
				if(intent.hasExtra(TransportConstants.BIND_LOCATION_PACKAGE_NAME_EXTRA)
						&& intent.hasExtra(TransportConstants.BIND_LOCATION_CLASS_NAME_EXTRA)){
					//We now know the location of the router service that is currently up and running
					
					Intent bindingIntent = new Intent();
					bindingIntent.setClassName(intent.getStringExtra(TransportConstants.BIND_LOCATION_PACKAGE_NAME_EXTRA), intent.getStringExtra(TransportConstants.BIND_LOCATION_CLASS_NAME_EXTRA));//This sets an explicit intent
					context.bindService(bindingIntent, routerConnection, Context.BIND_AUTO_CREATE);
				}
				
			}

			
		}
    	
    };
    /**
     * Handler of incoming messages from service.
     */
    class ClientHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
        	Bundle bundle = msg.getData();
            /* DO NOT MOVE
             * This needs to be first to make sure we already know if we are attempting to enter legacy mode or not
             */
        	if(bundle !=null && bundle.containsKey(TransportConstants.ENABLE_LEGACY_MODE_EXTRA)){
				boolean enableLegacy = bundle.getBoolean(TransportConstants.ENABLE_LEGACY_MODE_EXTRA, false);
				Log.d(TAG, "Setting legacy mode: " +enableLegacy );
				enableLegacyMode(enableLegacy);
			}
            
        	//Find out what message we have and what to do with it
            switch (msg.what) {//FIXME
            	case TransportConstants.ROUTER_REGISTER_CLIENT_RESPONSE:
            		if(msg.arg1==TransportConstants.REGISTRATION_RESPONSE_SUCESS){
            			//TODO yay! we have been registered. Now what?
            			registeredWithRouterService = true;
            			if(bundle!=null && bundle.containsKey(TransportConstants.CONNECTED_DEVICE_STRING_EXTRA_NAME)){
        					//Keep track if we actually get this
        				}
            			if(queuedOnTransportConnect!=null){
        					onHardwareConnected(queuedOnTransportConnect);
        					queuedOnTransportConnect = null;
        				}	
            		}else{
            			registeredWithRouterService = false; 
            			Log.w(TAG, "Registration denied from router service. Reason - " + msg.arg1);
            		}
            	
            		break;
            	case TransportConstants.ROUTER_RECEIVED_PACKET:
					//So the intent has a packet with it. PEFRECT! Let's send it through the library
            		if(bundle.containsKey(TransportConstants.FORMED_PACKET_EXTRA_NAME)){
            			Parcelable packet = bundle.getParcelable(TransportConstants.FORMED_PACKET_EXTRA_NAME);
    					if(packet!=null){
    						onPacketReceived(packet);
    					}else{
    						Log.w(TAG, "Received null packet from router service, not passing along");
    					}
            		}
            		break;
            	case TransportConstants.HARDWARE_CONNECTION_EVENT:
        			if(bundle.containsKey(TransportConstants.HARDWARE_DISCONNECTED)){
        				//We should shut down, so call 
        				onHardwareDisconnected(TransportType.valueOf(bundle.getString(TransportConstants.HARDWARE_DISCONNECTED)));
        			}
        			
        			if(bundle.containsKey(TransportConstants.HARDWARE_CONNECTED)){
        				onHardwareConnected(TransportType.valueOf(bundle.getString(TransportConstants.HARDWARE_CONNECTED)));
        				
        			}
            		break;
            	default:
                    super.handleMessage(msg);
            }   
            
        }
    }
    
	 BroadcastReceiver packetReceiver = new BroadcastReceiver() 
		{
			@Override
			public void onReceive(Context context, Intent intent) 
			{
				//Log.d(TAG, whereToReply + " received an Intent, checking to see what it is");
				if(intent.hasExtra(TransportConstants.ENABLE_LEGACY_MODE_EXTRA)){
					boolean enableLegacy = intent.getBooleanExtra(TransportConstants.ENABLE_LEGACY_MODE_EXTRA, false);
					Log.d(TAG, "Setting legacy mode: " +enableLegacy );
					enableLegacyMode(enableLegacy);
				}
				if(intent.hasExtra(TransportConstants.HARDWARE_DISCONNECTED)){
					//We should shut down, so call 
					//Log.d(TAG, "Being told the hardware disconnected, calling onHardwareDisconnect()");
					onHardwareDisconnected(TransportType.valueOf(intent.getStringExtra(TransportConstants.HARDWARE_DISCONNECTED)));
				}
				if(intent.hasExtra(TransportConstants.HARDWARE_CONNECTED)){
					onHardwareConnected(TransportType.valueOf(intent.getStringExtra(TransportConstants.HARDWARE_CONNECTED)));
					
				}
				if(intent.hasExtra(SdlRouterService.REGISTER_WITH_ROUTER_ACTION)){
					//Log.d(TAG,"Being told to reregister");

					//So the Bluetooth Service is telling us it is free now.
					//Let's try to register again with the service. It better not deny us again! I swear!
					if(sendPacketAddress==null){ 
						//Log.d(TAG,"The sendPacketAddress is null, attempt to register with Bluetooth Service");
						//Want to make sure we are connecting
						//This should be ok to send in the case of the first service creation
						//We should only be returned one successful intent and one refused attempt
						registerWithRouterService();
					}
					//else{Log.d(TAG,"The sendPacketAddress is " +sendPacketAddress );}
					return;
				}
				if(intent.hasExtra(TransportConstants.REGISTRATION_DENIED_EXTRA_NAME)){
					Log.d(TAG,"The bluetooth service denied registration to " + whereToReply);
					return;

				}

				if(intent.hasExtra(TransportConstants.SEND_PACKET_TO_ROUTER_LOCATION_EXTRA_NAME)){
					//This means that we are registered! whoo!
					//Let's grab the address of out packets
					sendPacketAddress = intent.getStringExtra(TransportConstants.SEND_PACKET_TO_ROUTER_LOCATION_EXTRA_NAME);
					//Let's try to grab a local copy of the BluetoothDeviceName
					if(intent.hasExtra(TransportConstants.CONNECTED_DEVICE_STRING_EXTRA_NAME)){
						//Keep track if we actually get this
					}
					//Log.i(TAG, whereToReply + " Registered with the Sdl Bluetooth Service!");
					if(queuedOnTransportConnect!=null){
						onHardwareConnected(queuedOnTransportConnect);
						queuedOnTransportConnect = null;
					}
				}
				if(intent.hasExtra(TransportConstants.FORMED_PACKET_EXTRA_NAME)){
					//So the intent has a packet with it. PEFRECT! Let's send it through the library
					//1/15/2015 this is now a parceable object being sent
					Parcelable packet = intent.getParcelableExtra(TransportConstants.FORMED_PACKET_EXTRA_NAME);
					if(packet!=null){
						onPacketReceived(packet);
					}else{
						Log.w(TAG, "Received null packet from router service, not passing along");
					}

				}
				
			}
		};
		
		
		
		/***************************************************************************************************************************************
		***********************************************  Life Cycle  **************************************************************
		****************************************************************************************************************************************/	
			
		
		public TransportBroker(Context context, String appId){
			synchronized(INIT_LOCK){
				//So the user should have set the AppId, lets define where the intents need to be sent
				SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss"); //So we have a time stamp of the event
				String timeStamp = s.format(new Date(System.currentTimeMillis()));
				if(whereToReply==null){
					if(appId==null){ //FIXME this should really just throw an error
						whereToReply = WHERE_TO_REPLY_PREFIX + "."+ timeStamp; //TODO get appid
					}else{
						whereToReply = WHERE_TO_REPLY_PREFIX + appId +"."+ timeStamp; 
					}
				}
				TransportBroker.appId = appId;
				queuedOnTransportConnect = null;
				currentContext = context;
				//Log.d(TAG, "Registering our reply receiver: " + whereToReply);
				currentContext.registerReceiver(packetReceiver, new IntentFilter(whereToReply));
			}
		}

		/**
		 * This beings the initial connection with the router service.
		 */
		public void start(){
			//Log.d(TAG, "Starting up transport broker for " + whereToReply);
			synchronized(INIT_LOCK){
				if(currentContext==null){
					throw new IllegalStateException("This instance can't be started since it's local reference of context is null. Ensure when suppling a context to the TransportBroker that it is valid");
				}
				//Log.d(TAG, "Registering our reply receiver: " + whereToReply);
				registerWithRouterService();
			}
		}
		
		public void resetSession(){
			//Log.d(TAG, "RESETING transport broker for " + whereToReply);
			synchronized(INIT_LOCK){
				unregisterWithRouterService();
				routerService = null; //TODO make sure theres nothing else we need
				queuedOnTransportConnect = null;
			}
		}
		/**
		 * This method will end our communication with the router service. 
		 */
		public void stop(){
			//Log.d(TAG, "STOPPING transport broker for " + whereToReply);
			synchronized(INIT_LOCK){
				unregisterWithRouterService();
				routerService = null;
				queuedOnTransportConnect = null;
				try{
					if(currentContext!=null){
						currentContext.unregisterReceiver(packetReceiver); //Where we get packets from the Bluetooth Service	
					}
				}catch(IllegalArgumentException e){
					Log.w(TAG, "Receiver was never registered. Not a big deal.");
				}
				currentContext = null;
				
			}
		}
		

		/***************************************************************************************************************************************
		***********************************************  Event Callbacks  **************************************************************
		****************************************************************************************************************************************/	
		
		
		public void onServiceUnregsiteredFromRouterService(int unregisterCode){
			queuedOnTransportConnect = null;
		}
		
		public void onHardwareDisconnected(TransportType type){
			synchronized(INIT_LOCK){
				//sendPacketAddress = null;
				queuedOnTransportConnect = null;
			}
		}
		
		public boolean onHardwareConnected(TransportType type){
			synchronized(INIT_LOCK){
				if(routerService==null){
					queuedOnTransportConnect = type;
					return false;
				}
				return true;
			}
			
		}
		
		public void onPacketReceived(Parcelable packet){
			
		}
		
		
		/**
		 * We want to check to see if the Router service is already up and running
		 * @param context
		 * @return
		 */
		private boolean isRouterServiceRunning(Context context){
			Log.d(TAG,whereToReply + " checking if a bluetooth service is running");
			if(context==null){
				
				return false;
			}
			ActivityManager manager = (ActivityManager) context.getSystemService("activity");
		    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
		    	//We will check to see if it contains this name, should be pretty specific
		    	if ((service.service.getClassName()).toLowerCase(Locale.US).contains(SdlBroadcastReceiver.SDL_ROUTER_SERVICE_CLASS_NAME)) { 
		            return true;
		        }
		    }			
			return false;
		}
		
		
		public void sendPacketToRouterService(byte[] bytes, int offset, int count){
			//Log.d(TAG,whereToReply + " sending packet to Bluetooth Service");

			if(routerService==null){
				Log.d(TAG,whereToReply + " tried to send packet, but no where to send");
				return;
			}
			Message message = Message.obtain();
			message.what = TransportConstants.ROUTER_SEND_PACKET;
			Bundle bundle = new Bundle();
			bundle.putByteArray(TransportConstants.BYTES_TO_SEND_EXTRA_NAME, bytes);
			bundle.putLong(TransportConstants.BYTES_TO_SEND_EXTRA_OFFSET, offset);
			bundle.putLong(TransportConstants.BYTES_TO_SEND_EXTRA_COUNT, count);
			message.setData(bundle);
			
			sendMessageToRouterService(message);
		}
		
		/**
		 * This registers this service with the router service
		 */
		private void registerWithRouterService(){ 
			if(getContext()==null){
				Log.e(TAG, "Context set to null, failing out");
				return;
			}
			//FIXME we need a different boolean to store our registered state
			if(routerService!=null){
				Log.w(TAG, "Already registered with router service");
				return;
			}
			
			Intent intent = null;
			if(!isRouterServiceRunning(getContext()) ){
				Log.w(TAG, "No instance of the Sdl router service to register with");
				//Log.d(TAG,whereToReply + " starting up and registering with  router Service");
				intent = new Intent(SdlRouterService.START_ROUTER_SERVICE_ACTION);
			}
			else{
				//Log.d(TAG,whereToReply + " registering with  router Service");
				intent = new Intent(SdlRouterService.REGISTER_WITH_ROUTER_ACTION);
			}
			
			intent.putExtra(TransportConstants.SEND_PACKET_TO_APP_LOCATION_EXTRA_NAME, whereToReply);
			intent.putExtra(TransportConstants.PACKAGE_NAME_STRING, getContext().getPackageName());
			intent.putExtra(TransportConstants.APP_ID_EXTRA, Long.valueOf(appId));
			
			currentContext.sendBroadcast(intent);
			
		}
		
		private void unregisterWithRouterService(){
			Log.i(TAG, "Attempting to unregister with Sdl Router Service");
			Intent unregisterWithService = new Intent();
			unregisterWithService.setAction(SdlRouterService.REGISTER_WITH_ROUTER_ACTION);
			unregisterWithService.putExtra(TransportConstants.UNREGISTER_EXTRA, Long.valueOf(appId));
			currentContext.sendBroadcast(unregisterWithService);
			//TODO do I need to do this? routerService = null;
		}
		

		
		/**
		 * Since it doesn't always make sense to add another service, use this method to get
		 * the appropriate context that the rest of this class is using.
		 * @return The currently used context for this class
		 */
		private Context getContext(){
			return currentContext;
		}
		
		/***************************************************************************************************************************************
		***********************************************  LEGACY  *******************************************************************************
		****************************************************************************************************************************************/	
		/* 
		 * Due to old implementations of SDL/Applink, old versions can't support multiple sessions per RFCOMM channel. 
		 * This causes a race condition in the router service where only the first app registered will be able to communicate with the
		 * head unit. With this additional code, the router service will:
		 * 1) Acknowledge it's connected to an old system
		 * 2) d/c its bluetooth
		 * 3) Send a message to all clients connected that legacy mode is enabled
		 * 4) Each client spins up their own bluetooth RFCOMM listening channel
		 * 5) Head unit will need to query apps again
		 * 6) HU should then connect to each app by their own RFCOMM channel bypassing the router service
		 * 7) When the phone is D/C from the head unit the router service will reset and tell clients legacy mode is now off
		 */
		
		private static boolean legacyModeEnabled = false;
		private static Object LEGACY_LOCK = new Object();
		
		protected void enableLegacyMode(boolean enable){
			synchronized(LEGACY_LOCK){
				legacyModeEnabled = enable;
			}
		}
		protected static boolean isLegacyModeEnabled(){
			synchronized(LEGACY_LOCK){
				return legacyModeEnabled;
			}
			
		}
		
		/***************************************************************************************************************************************
		****************************************************  LEGACY END ***********************************************************************
		****************************************************************************************************************************************/
}
