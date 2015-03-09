package com.smartdevicelink.transport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Parcelable;
import android.util.Log;


public class TransportBroker {
	
	private static final String TAG = "SdlTransportBroker";

	private final String WHERE_TO_REPLY_PREFIX							 = "com.sdl.android.";
	private static String appId = null,whereToReply = null, sendPacketAddress = null;;
	private Context currentContext = null;
	
	private Object INIT_LOCK = new Object();
	
	private TransportType queuedOnTransportConnect = null;
	
	 BroadcastReceiver packetReceiver = new BroadcastReceiver() 
		{
			@Override
			public void onReceive(Context context, Intent intent) 
			{
				if(intent.hasExtra(TransportConstants.ENABLE_LEGACY_MODE_EXTRA)){
					boolean enableLegacy = intent.getBooleanExtra(TransportConstants.ENABLE_LEGACY_MODE_EXTRA, false);
					Log.d(TAG, "Setting legacy mode: " +enableLegacy );
					enableLegacyMode(enableLegacy);
					//TODO not sure yet i think we just let it DC
					if(isLegacyModeEnabled()){
						//Start bluetooth
						//initBluetoothSerialService();
						//NOTE: This is to avoid the hardware d/c right below. If we ever send more extras it will be important to note that we exit here
						//return;							
					}else{
						//Stop bluetooth
						//closeBluetoothSerialServer();
					}
				}
				//Log.d(TAG, whereToReply + " received an Intent, checking to see what it is");
				if(intent.hasExtra(TransportConstants.HARDWARE_DISCONNECTED)){
					//We should shut down, so call 
					Log.d(TAG, "Being told the hardware disconnected, calling onHfardwareDisconnect()");
					onHardwareDisconnected(TransportType.valueOf(intent.getStringExtra(TransportConstants.HARDWARE_DISCONNECTED)));
				}
				if(intent.hasExtra(TransportConstants.HARDWARE_CONNECTED)){
					onHardwareConnected(TransportType.valueOf(intent.getStringExtra(TransportConstants.HARDWARE_CONNECTED)));
					
				}
				if(intent.hasExtra(TransportConstants.UNREGISTER_EXTRA)){
					//We should shut down, so call 
					//Log.d(TAG, "Being told to unregister, calling onServiceUnregsiteredFromBTServer()");
									
					sendPacketAddress = null;
					onServiceUnregsiteredFromRouterService(intent.getIntExtra(TransportConstants.UNREGISTER_EXTRA, 0));
					
				}
				if(intent.hasExtra(TransportConstants.PING_REGISTERED_SERVICE_EXTRA)){
					//We were pinged! Those BAST.... let's just reply
					if(sendPacketAddress!=null){
						Intent replyIntent = new Intent(sendPacketAddress);
						replyIntent.putExtra(TransportConstants.PING_REGISTERED_SERVICE_REPLY_EXTRA, "PING_REGISTERED_SERVICE_REPLY_EXTRA");
						getContext().sendBroadcast(replyIntent);
					}
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
						//initBluetoothConnection();
					}
					else{
						//Log.d(TAG,"The sendPacketAddress is " +sendPacketAddress );

					}
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
				if(intent.hasExtra(TransportConstants.PACKET_TO_SEND_EXTRA_NAME)){
					//So the intent has a packet with it. PEFRECT! Let's send it through the library
					//1/15/2015 this is now a parceable object being sent
					Parcelable packet = intent.getParcelableExtra(TransportConstants.PACKET_TO_SEND_EXTRA_NAME);
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
				sendPacketAddress = null;
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
				sendPacketAddress = null;
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
				if(sendPacketAddress==null){
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
		    	if ((service.service.getClassName()).toLowerCase(Locale.US).contains(SdlBroadcastReceiver.SDL_ROUTER_SERVICE_CLASS_NAME)) { //TODO fix to look for correct name
		            return true;
		        }
		    }			
			return false;
		}
		
		public void sendPacketToRouterService(char[] packet){
			//Log.d(TAG,whereToReply + " sending packet to Bluetooth Service");

			if(sendPacketAddress==null){
				Log.d(TAG,whereToReply + " tried to send packet, but no where to send");
				return;
			}
			Intent test = new Intent();
			test.setAction(sendPacketAddress);
			test.putExtra(TransportConstants.PACKET_TO_SEND_EXTRA_NAME, packet);
			currentContext.sendBroadcast(test);
		}
		
		
		public void sendPacketToRouterService(byte[] bytes, int offset, int count){
			//Log.d(TAG,whereToReply + " sending packet to Bluetooth Service");

			if(sendPacketAddress==null){
				Log.d(TAG,whereToReply + " tried to send packet, but no where to send");
				return;
			}
			Intent test = new Intent();
			test.setAction(sendPacketAddress);
			test.putExtra(TransportConstants.BYTES_TO_SEND_EXTRA_NAME, bytes);
			test.putExtra(TransportConstants.BYTES_TO_SEND_EXTRA_OFFSET, offset);
			test.putExtra(TransportConstants.BYTES_TO_SEND_EXTRA_COUNT, count);
			
			currentContext.sendBroadcast(test);
		}
		
		/**
		 * This registers this service with the router service
		 */
		private void registerWithRouterService(){ 
			if(getContext()==null){
				Log.e(TAG, "Context set to null, failing out");
				return;
				//setContext(getBaseContext()); //Hey at least try something
			}
			if(sendPacketAddress!=null){
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
			sendPacketAddress = null;
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
		***********************************************  LEGACY  **************************************************************
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
		
		//private static MultiplexBluetoothTransport mSerialService = null;
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
	
}
