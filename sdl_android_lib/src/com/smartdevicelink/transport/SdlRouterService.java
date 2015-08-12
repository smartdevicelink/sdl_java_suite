package com.smartdevicelink.transport;

import static com.smartdevicelink.transport.TransportConstants.CONNECTED_DEVICE_STRING_EXTRA_NAME;
import static com.smartdevicelink.transport.TransportConstants.FORMED_PACKET_EXTRA_NAME;
import static com.smartdevicelink.transport.TransportConstants.HARDWARE_DISCONNECTED;
import static com.smartdevicelink.transport.TransportConstants.SEND_PACKET_TO_APP_LOCATION_EXTRA_NAME;
import static com.smartdevicelink.transport.TransportConstants.WAKE_UP_BLUETOOTH_SERVICE_INTENT;

import java.util.HashMap;
import java.util.Locale;
import java.util.Set;
import java.util.Vector;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.protocol.enums.FrameType;
import com.smartdevicelink.transport.enums.TransportType;
import com.smartdevicelink.util.BitConverter;

/**
 * <b>This class should not be modified by anyone outside of the approved contributors of the SmartDeviceLink project.</b>
 * This service is a central point of communication between hardware and the registered clients. It will multiplex a single transport
 * to provide a connection for a theoretical infinite amount of SDL sessions. 
 * @author Joey Grover
 *
 */
public abstract class SdlRouterService extends Service{
	
	private static final String TAG = "Sdl Router Service";
	/**
	 * <b> NOTE: DO NOT MODIFY THIS UNLESS YOU KNOW WHAT YOU'RE DOING.</b>
	 */
	protected static final int ROUTER_SERVICE_VERSION_NUMBER = 1;	
	
	public static final String START_ROUTER_SERVICE_ACTION 					= "sdl.router"+ TransportConstants.START_ROUTER_SERVICE_ACTION_SUFFIX;
	public static final String REGISTER_NEWER_SERVER_INSTANCE_ACTION		= "com.sdl.android.newservice";
	public static final String START_SERVICE_ACTION							= "sdl.router.startservice";
	public static final String REGISTER_WITH_ROUTER_ACTION 					= "com.sdl.android.register"; 
	
	/** Message types sent from the BluetoothReadService Handler */
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
	
    
	private static MultiplexBluetoothTransport mSerialService = null;

	private static boolean connectAsClient = false;
	private static boolean closing = false;
	private static Context currentContext = null;
    
    private Handler versionCheckTimeOutHandler;
    private Runnable versionCheckRunable;
    private LocalRouterService localCompareTo = null;
    private final static int VERSION_TIMEOUT_RUNNABLE = 750; 
	
	
    private Intent lastReceivedStartIntent = null;
	public static HashMap<Long,RegisteredApp> registeredApps;
	SparseArray<Long> sessionMap;
	private Object SESSION_LOCK;
	
	private static Messenger altTransportMessager = null; //THERE CAN BE ONLY ONE!!!

	private String  connectedDeviceName = "";			//The name of the connected Device
	private boolean startSequenceComplete = false;	
	
	
	
	/* **************************************************************************************************************************************
	****************************************************************************************************************************************
	***********************************************  Broadcast Receivers START  **************************************************************
	****************************************************************************************************************************************
	****************************************************************************************************************************************/	
	
	/** create our receiver from the router service */
    BroadcastReceiver mainServiceReceiver = new BroadcastReceiver() 
	{
		@Override
		public void onReceive(Context context, Intent intent) 
		{				
			//Let's grab where to reply to this intent at. We will keep it temp right now because we may have to deny registration
			String action =intent.getStringExtra(SEND_PACKET_TO_APP_LOCATION_EXTRA_NAME);
			sendBroadcast(prepareRegistrationIntent(action));	
		}
	};
	
	private Intent prepareRegistrationIntent(String action){
		Intent registrationIntent = new Intent();
		registrationIntent.setAction(action);
		registrationIntent.putExtra(TransportConstants.BIND_LOCATION_PACKAGE_NAME_EXTRA, this.getPackageName());
		registrationIntent.putExtra(TransportConstants.BIND_LOCATION_CLASS_NAME_EXTRA, this.getClass().getName());
		return registrationIntent;
	}
	
	private void onAppRegistered(RegisteredApp app){
		//Log.enableDebug(receivedIntent.getBooleanExtra(LOG_BASIC_DEBUG_BOOLEAN_EXTRA, false));
		//Log.enableBluetoothTraceLogging(receivedIntent.getBooleanExtra(LOG_TRACE_BT_DEBUG_BOOLEAN_EXTRA, false));
		//Ok this is where we should do some authenticating...maybe. 
		//Should we ask for all relevant data in this packet?

		if(startSequenceComplete &&
				!connectAsClient && (mSerialService ==null 
									|| mSerialService.getState() == MultiplexBluetoothTransport.STATE_NONE)){
			Log.e(TAG, "Serial service not initliazed while registering app");
			//Maybe we should try to do a connect here instead
			Log.d(TAG, "Serial service being restarted");
			if(mSerialService ==null){
				Log.e(TAG, "Local copy of BT Server is null");
				mSerialService = MultiplexBluetoothTransport.getBluetoothSerialServerInstance();
				if(mSerialService==null){
					Log.e(TAG, "Local copy of BT Server is still null and so is global");
					mSerialService = MultiplexBluetoothTransport.getBluetoothSerialServerInstance(mHandlerBT);

				}
			}
			mSerialService.start();

		}

		Log.i(TAG, app.appId + " has just been registered with SDL Router Service");
	}
	
	
	 /**
	  * this is to make sure the AceeptThread is still running
	  */
		BroadcastReceiver registerAnInstanceOfSerialServer = new BroadcastReceiver() 
				{
					@Override
					public void onReceive(Context context, Intent intent) 
					{
						//Let's make sure we are on the same version.
						if(intent.hasExtra(SdlBroadcastReceiver.LOCAL_BT_SERVER_VERSION_NUMBER_EXTRA) 
								&& intent.getIntExtra(SdlBroadcastReceiver.LOCAL_BT_SERVER_VERSION_NUMBER_EXTRA, 0)> ROUTER_SERVICE_VERSION_NUMBER
								&& intent.hasExtra(SdlBroadcastReceiver.INTENT_FOR_OTHER_BT_SERVER_INSTANCE_EXTRA)){
							//So the version requesting to be started is actually newer than this version, so we need to 
							//close our bluetooth connections, launch the other service, and close ourself
							int versionOfIntent = intent.getIntExtra(SdlBroadcastReceiver.LOCAL_BT_SERVER_VERSION_NUMBER_EXTRA, 0);
							
							if(localCompareTo == null || (versionOfIntent>localCompareTo.version)){
								Intent savedIntent = (Intent)intent.getParcelableExtra(SdlBroadcastReceiver.INTENT_FOR_OTHER_BT_SERVER_INSTANCE_EXTRA);
								localCompareTo = new LocalRouterService(savedIntent,versionOfIntent);
							}
							return;
							
						}
					}
			};
	
	/**
	 * If the user disconnects the bluetooth device we will want to stop SDL and our current
	 * connection through RFCOMM
	 */
	BroadcastReceiver mListenForDisconnect = new BroadcastReceiver() 
			{
				@Override
				public void onReceive(Context context, Intent intent) 
				{
					String action = intent.getAction();
			    	if(action!=null){Log.d(TAG, "Disconnect received. Action: " + intent.getAction());}
			    	else{Log.d(TAG, "Disconnect received.");}
					if(intent.getAction()!=null && intent.getAction().equalsIgnoreCase("android.bluetooth.adapter.action.STATE_CHANGED") 
							&&(	(BluetoothAdapter.getDefaultAdapter().getState() == BluetoothAdapter.STATE_TURNING_ON) 
							|| (BluetoothAdapter.getDefaultAdapter().getState() == BluetoothAdapter.STATE_ON))){
						return;
					}
					if(action!=null
							&& (intent.getAction().equalsIgnoreCase("android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED") 
									||intent.getAction().equalsIgnoreCase("android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED") ) ){
						//Log.d(TAG, "None of our business");
						return;
					}

					//TODO make sure it's ok to comment out closeBluetoothSerialServer();
					connectAsClient=false;
					
					if(action!=null && intent.getAction().equalsIgnoreCase("android.bluetooth.adapter.action.STATE_CHANGED") 
							&&(	(BluetoothAdapter.getDefaultAdapter().getState() == BluetoothAdapter.STATE_TURNING_OFF) 
							|| (BluetoothAdapter.getDefaultAdapter().getState() == BluetoothAdapter.STATE_OFF))){
							Log.d(TAG, "Bluetooth is shutting off, SDL Router Service is closing.");
							//Since BT is shutting off...there's no reason for us to be on now. 
							//Let's take a break...I'm sleepy
							shouldServiceKeepRunning(intent);
						}
					else{//So we just got d/c'ed from the bluetooth...alright...Let the client know
						if(legacyModeEnabled){
							Log.d(TAG, "Legacy mode enabled and bluetooth d/c'ed, restarting router service bluetooth.");
							enableLegacyMode(false);
							onTransportDisconnected(TransportType.BLUETOOTH); //TODO check that this is ok
							initBluetoothSerialService();
						}
					}
			}
		};
		
/* **************************************************************************************************************************************
***********************************************  Broadcast Receivers End  **************************************************************
****************************************************************************************************************************************/

		/* **************************************************************************************************************************************
		*********************************************** Handlers for bound clients **************************************************************
		****************************************************************************************************************************************/

		
	    /**
	     * Target we publish for clients to send messages to RouterHandler.
	     */
	    final Messenger routerMessenger = new Messenger(new RouterHandler());
	    
		 /**
	     * Handler of incoming messages from clients.
	     */
	    class RouterHandler extends Handler {
	        @Override
	        public void handleMessage(Message msg) {
	        	Bundle receivedBundle = msg.getData();
	        	Bundle returnBundle;
	        	
	            switch (msg.what) {
	            case TransportConstants.ROUTER_REQUEST_BT_CLIENT_CONNECT:              	
	            	if(receivedBundle.getBoolean(TransportConstants.CONNECT_AS_CLIENT_BOOLEAN_EXTRA, false)
	        				&& !connectAsClient){		//We check this flag to make sure we don't try to connect over and over again. On D/C we should set to false
	        				//Log.d(TAG,"Attempting to connect as bt client");
	        				BluetoothDevice device = receivedBundle.getParcelable(BluetoothDevice.EXTRA_DEVICE);
	        				connectAsClient = true;
	        				if(device==null || !bluetoothConnect(device)){
	        					Log.e(TAG, "Unable to connect to bluetooth device");
	        					connectAsClient = false;
	        				}
	        			}
	            	//**************** We don't break here so we can let the app register as well
	                case TransportConstants.ROUTER_REGISTER_CLIENT: //msg.arg1 is appId
	                	Message message = Message.obtain();
	                	message.what = TransportConstants.ROUTER_REGISTER_CLIENT_RESPONSE;
	                	long appId = receivedBundle.getLong(TransportConstants.APP_ID_EXTRA, -1);
	                	if(appId<0 || msg.replyTo == null){
	                		Log.w(TAG, "Unable to requster app as no id or messenger was included");
	                		if(msg.replyTo!=null){
	                			msg.arg1 = TransportConstants.REGISTRATION_RESPONSE_DENIED_APP_ID_NOT_INCLUDED;
	                			try {
									msg.replyTo.send(message);
								} catch (RemoteException e) {
									e.printStackTrace();
								}
	                		}
	                		break;
	                	}
	                	
	                	RegisteredApp app = new RegisteredApp(appId,msg.replyTo);
	                	registeredApps.put(app.getAppId(), app);
	            		onAppRegistered(app);
	            		
	            		//TODO reply to this messanger.
	            		returnBundle = new Bundle();
	            		
	            		if(MultiplexBluetoothTransport.currentlyConnectedDevice!=null){
	            			returnBundle.putString(CONNECTED_DEVICE_STRING_EXTRA_NAME, MultiplexBluetoothTransport.currentlyConnectedDevice);
	            		}
	            		if(!returnBundle.isEmpty()){
	            			message.setData(returnBundle);
	            		}
	            		int result = app.sendMessage(message);
	            		if(result == RegisteredApp.SEND_MESSAGE_ERROR_MESSENGER_DEAD_OBJECT){
	            			registeredApps.remove(appId);
	            		}

	                    break;
	                case TransportConstants.ROUTER_UNREGISTER_CLIENT:
	                	long appIdToUnregister = receivedBundle.getLong(TransportConstants.APP_ID_EXTRA, -1);
	                	
	                	RegisteredApp unregisteredApp = registeredApps.remove(appIdToUnregister);//TODO check if this works
	                	Message response = Message.obtain();
	                	response.what = TransportConstants.ROUTER_UNREGISTER_CLIENT_RESPONSE;
	                	if(unregisteredApp == null){
	                		response.arg1 = TransportConstants.UNREGISTRATION_RESPONSE_FAILED_APP_ID_NOT_FOUND;
	                	}else{
	                		response.arg1 = TransportConstants.UNREGISTRATION_RESPONSE_SUCESS;
	                	}
	                	try {
	                		msg.replyTo.send(response); //We do this because we aren't guaranteed to find the correct registeredApp to send the message through
	                	} catch (RemoteException e) {
	                		e.printStackTrace();
	                		
	                	}catch(NullPointerException e2){
	                		Log.e(TAG, "No reply address included, can't send a reply");
	                	}
	                	
	                    break;
	                case TransportConstants.ROUTER_SEND_PACKET:
	                	Log.d(TAG, "Received packet to send");
	    				writeBytesToTransport(receivedBundle);
	                    break;
	                case TransportConstants.ROUTER_REQUEST_NEW_SESSION:
	                	long appIdRequesting = receivedBundle.getLong(TransportConstants.APP_ID_EXTRA, -1);
	                	Message extraSessionResponse = Message.obtain();
	                	extraSessionResponse.what = TransportConstants.ROUTER_REQUEST_NEW_SESSION_RESPONSE;
	                	if(appIdRequesting>0){
							synchronized(SESSION_LOCK){
								if(registeredApps!=null){
									RegisteredApp appRequesting = registeredApps.get(appIdRequesting);
									if(appRequesting!=null){
										appRequesting.getSessionIds().add((long)-1); //Adding an extra session
										extraSessionResponse.arg1 = TransportConstants.ROUTER_REQUEST_NEW_SESSION_RESPONSE_SUCESS;
									}else{
										extraSessionResponse.arg1 = TransportConstants.ROUTER_REQUEST_NEW_SESSION_RESPONSE_FAILED_APP_NOT_FOUND;
									}
								}
							}		
						}else{
							extraSessionResponse.arg1 = TransportConstants.ROUTER_REQUEST_NEW_SESSION_RESPONSE_FAILED_APP_ID_NOT_INCL;
						}
	                	try {
	                		msg.replyTo.send(extraSessionResponse); //We do this because we aren't guaranteed to find the correct registeredApp to send the message through
	                	} catch (RemoteException e) {
	                		e.printStackTrace();
	                	}catch(NullPointerException e2){
	                		Log.e(TAG, "No reply address included, can't send a reply");
	                	}
	                	break;
	                default:
	                    super.handleMessage(msg);
	            }
	        }
	    }

	    
	    /**
	     * Target we publish for alternative transport (USB) clients to send messages to RouterHandler.
	     */
	    final Messenger altTransportMessenger = new Messenger(new AltTransportHandler());
	    
		 /**
	     * Handler of incoming messages from an alternative transport (USB).
	     */
	    class AltTransportHandler extends Handler {
	    	ClassLoader loader = getClass().getClassLoader();
	        @Override
	        public void handleMessage(Message msg) {
	        	Bundle receivedBundle = msg.getData();
	        	switch(msg.what){
	        	case TransportConstants.HARDWARE_CONNECTION_EVENT:
        			if(receivedBundle.containsKey(TransportConstants.HARDWARE_DISCONNECTED)){
        				//We should shut down, so call 
        				if(altTransportMessager != null 
        						&& altTransportMessager.equals(msg.replyTo)){
        					//The same transport that was connected to the router service is now telling us it's disconnected. Let's inform clients and clear our saved messenger
        					altTransportMessager = null;
        					storeConnectedStatus(false);
        					onTransportDisconnected(TransportType.valueOf(receivedBundle.getString(TransportConstants.HARDWARE_DISCONNECTED)));
        					shouldServiceKeepRunning(null); //this will close the service if bluetooth is not available
        				}
        			}
        			
        			if(receivedBundle.containsKey(TransportConstants.HARDWARE_CONNECTED)){
    					Message retMsg =  Message.obtain();
    					retMsg.what = TransportConstants.ROUTER_REGISTER_ALT_TRANSPORT_RESPONSE;
        				if(altTransportMessager == null){ //Ok no other transport is connected, this is good
        					Log.d(TAG, "Alt transport connected.");
        					if(msg.replyTo == null){
        						break;
        					}
        					altTransportMessager = msg.replyTo;
        					storeConnectedStatus(true);
        					//Let the alt transport know they are good to go
        					retMsg.arg1 = TransportConstants.ROUTER_REGISTER_ALT_TRANSPORT_RESPONSE_SUCESS;
        					onTransportConnected(TransportType.valueOf(receivedBundle.getString(TransportConstants.HARDWARE_CONNECTED)));
        				}else{ //There seems to be some other transport connected
        					//TODO error
        					retMsg.arg1 = TransportConstants.ROUTER_REGISTER_ALT_TRANSPORT_ALREADY_CONNECTED;
        				}
        				if(msg.replyTo!=null){
        					try {retMsg.replyTo.send(retMsg);} catch (RemoteException e) {e.printStackTrace();}
        				}
        			}
            		break;
	        	case TransportConstants.ROUTER_RECEIVED_PACKET:
	        		if(receivedBundle!=null){
	        			receivedBundle.setClassLoader(loader);//We do this because loading a custom parceable object isn't possible without it
	            	}else{
	            		Log.e(TAG, "Bundle was null while sending packet to router service from alt transport");
	            	}
            		if(receivedBundle.containsKey(TransportConstants.FORMED_PACKET_EXTRA_NAME)){
            			SdlPacket packet = receivedBundle.getParcelable(TransportConstants.FORMED_PACKET_EXTRA_NAME);
    					if(packet!=null){
    						onPacketRead(packet);
    					}else{
    						Log.w(TAG, "Received null packet from alt transport service");
    					}
            		}else{
            			Log.w(TAG, "Flase positive packet reception");
            		}
            		break; 
	        	default:
	        		super.handleMessage(msg);
	        	}
	        	
	        }
	    };
		
/* **************************************************************************************************************************************
***********************************************  Life Cycle **************************************************************
****************************************************************************************************************************************/

	@Override
	public IBinder onBind(Intent intent) {
		//Check intent to send back the correct binder (client binding vs alt transport)
		if(intent!=null){
			int requestType = intent.getIntExtra(TransportConstants.ROUTER_BIND_REQUEST_TYPE_EXTRA, TransportConstants.BIND_REQUEST_TYPE_CLIENT);
			switch(requestType){
				case TransportConstants.BIND_REQUEST_TYPE_ALT_TRANSPORT:
					return this.altTransportMessenger.getBinder();
				case TransportConstants.BIND_REQUEST_TYPE_CLIENT:
					return this.routerMessenger.getBinder();
			}
		}
		return null;
	}

	
	
	@Override
	public boolean onUnbind(Intent intent) {
		// TODO If we are supposed to be shutting down, we need to try again.
		Log.d(TAG, "Unbind being called.");
		return super.onUnbind(intent);
	}

	
	private void notifyClients(Message message){
		if(message==null){
			Log.w(TAG, "Can't notify clients, message was null");
			return;
		}
		Log.d(TAG, "Notifying "+ registeredApps.size()+ " clients");
		int result;
		for (RegisteredApp app : registeredApps.values()) {
			result = app.sendMessage(message);
			if(result == RegisteredApp.SEND_MESSAGE_ERROR_MESSENGER_DEAD_OBJECT){
				registeredApps.remove(app.getAppId());
			}
		}
	
	}

	@Override
	public void onCreate() {
		super.onCreate();
		registeredApps = new HashMap<Long,RegisteredApp>();
		closing = false;
		currentContext = getBaseContext();
		registerReceiver(registerAnInstanceOfSerialServer, new IntentFilter(REGISTER_NEWER_SERVER_INSTANCE_ACTION));
		
		Log.i(TAG, "Livio Bluetooth Service has been created");
		newestServiceCheck(currentContext);
		
		SESSION_LOCK = new Object();
		synchronized(SESSION_LOCK){
			sessionMap = new SparseArray<Long>();
		}
	}
	
	private void startUpSequence(){
		IntentFilter stateChangeFilter = new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED");
		stateChangeFilter.addAction("android.bluetooth.device.action.CLASS_CHANGED");
		IntentFilter disconnectFilter1 = new IntentFilter("android.bluetooth.device.action.ACL_DISCONNECTED");
		IntentFilter disconnectFilter2 = new IntentFilter("android.bluetooth.device.action.ACL_DISCONNECT_REQUESTED");

		registerReceiver(mListenForDisconnect,stateChangeFilter );
		registerReceiver(mListenForDisconnect,disconnectFilter1 );
		registerReceiver(mListenForDisconnect,disconnectFilter2 );
		
		IntentFilter filter = new IntentFilter();
		filter.addAction(REGISTER_WITH_ROUTER_ACTION);
		registerReceiver(mainServiceReceiver,filter);
				
		if(!connectAsClient){initBluetoothSerialService();}
		startSequenceComplete= true;
	}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if(registeredApps == null){
			registeredApps = new HashMap<Long,RegisteredApp>();
		}
		if(intent != null ){
			if(intent.hasExtra(SEND_PACKET_TO_APP_LOCATION_EXTRA_NAME)){
				Log.i(TAG, "Received an intent with request to register service: "); //Reply as usual
				sendBroadcast(prepareRegistrationIntent(intent.getStringExtra(SEND_PACKET_TO_APP_LOCATION_EXTRA_NAME)));
			}
			if(intent.hasExtra(TransportConstants.PING_ROUTER_SERVICE_EXTRA)){
				//Make sure we are listening on RFCOMM
				Log.i(TAG, "Received ping, making sure we are listening to bluetooth rfcomm");
				initBluetoothSerialService();
			}
		}
		shouldServiceKeepRunning(intent);
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy(){
		if(versionCheckTimeOutHandler!=null){versionCheckTimeOutHandler.removeCallbacks(versionCheckRunable);}
		Log.v(TAG, "Sdl Router Service Destroyed");
	    closing = true;
		currentContext = null;
		//No need for this Broadcast Receiver anymore
		unregisterAllReceivers();
		closeBluetoothSerialServer();
		registeredApps = null;
		startSequenceComplete=false;
		super.onDestroy();
		try{
			android.os.Process.killProcess(android.os.Process.myPid());
		}catch(Exception e){}
	}
	
	private void unregisterAllReceivers(){
		try{
			unregisterReceiver(registerAnInstanceOfSerialServer);		///This should be first. It will always be registered, these others may not be and cause an exception.
			unregisterReceiver(mListenForDisconnect);
			unregisterReceiver(mainServiceReceiver);
		}catch(Exception e){}
	}
	
	private void notifyAltTransportOfClose(int reason){
		if(this.altTransportMessenger!=null){
			Message msg = Message.obtain();
			msg.what = TransportConstants.ROUTER_SHUTTING_DOWN_NOTIFICATION;
			msg.arg1 = reason;
			try {
				altTransportMessenger.send(msg);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	/* **************************************************************************************************************************************
	***********************************************  Helper Methods **************************************************************
	****************************************************************************************************************************************/
	
	public  String getConnectedDeviceName(){ //FIXME we need to implement something better than this, but for now it will work....
		return connectedDeviceName;
	}
	
	/**
	 * Checks to make sure bluetooth adapter is available and on
	 * @return
	 */
	private boolean bluetoothAvailable(){
		boolean retVal = (!(BluetoothAdapter.getDefaultAdapter()==null) && BluetoothAdapter.getDefaultAdapter().isEnabled());
		Log.d(TAG, "Bluetooth Available? - " + retVal);
		return retVal;
	}

	/**
	 * 
	 * 1. If the app has SDL shut off, 												shut down
	 * 2. if The app has an Alt Transport address or was started by one, 			stay open
	 * 3. If Bluetooth is off/NA	 												shut down
	 * 4. Anything else					
	 */
	public boolean shouldServiceKeepRunning(Intent intent){
		Log.d(TAG, "Determining if this service should remain open");
		
		if(altTransportMessager!=null 
				|| (intent!=null && intent.hasExtra(TransportConstants.ALT_TRANSPORT_ADDRESS_EXTRA))){ //FIXME how to handle 'service starts'
			//We have been started by an alt transport, we must remain open. "My life for Auir...."
			Log.d(TAG, "Alt Transport connected, remaining open");
			return true;
			
		}
		else if(!bluetoothAvailable()){//If bluetooth isn't on...there's nothing to see here
			//Bluetooth is off, we should shut down
			Log.d(TAG, "Bluetooth not available, shutting down service");
			closeSelf();
			return false;
		}else{
			Log.d(TAG, "Service to remain open");
			return true;
		}
	}
	/**
	 * This method is needed so that apps that choose not to implement this as a service as defined by Android, but rather
	 * just a simple class we have to know how to shut down.
	 */
	public void closeSelf(){
		closing = true;
		storeConnectedStatus(false);
		if(getBaseContext()!=null){
			stopSelf();
		}else{
			onDestroy();
		}
	}
	private synchronized void initBluetoothSerialService(){
		Log.i(TAG, "Iniitializing Bluetooth Serial Class");
		//init serial service
		if(mSerialService ==null){
			Log.d(TAG, "Local copy of BT Server is null");
			mSerialService = MultiplexBluetoothTransport.getBluetoothSerialServerInstance();
			if(mSerialService==null){
				Log.d(TAG, "Local copy of BT Server is still null and so is global");
				mSerialService = MultiplexBluetoothTransport.getBluetoothSerialServerInstance(mHandlerBT);
			}
		}
		if (mSerialService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mSerialService.getState() == MultiplexBluetoothTransport.STATE_NONE || mSerialService.getState() == MultiplexBluetoothTransport.STATE_ERROR) {
              // Start the Bluetooth services
            	mSerialService.start();
            }

		}
	}
	
	public void onTransportConnected(final TransportType type){
		//TODO remove
		Toast.makeText(getBaseContext(), "SDL "+ type.name()+ " Transport Connected", Toast.LENGTH_SHORT).show();

		Intent startService = new Intent();  
		startService.setAction(START_SERVICE_ACTION);
		startService.putExtra(TransportConstants.START_ROUTER_SERVICE_SDL_ENABLED_EXTRA, true);
    	sendBroadcast(startService);    	
		//HARDWARE_CONNECTED
	}
	
	public void onTransportDisconnected(TransportType type){
		if(altTransportMessager!=null){  //If we still have an alt transport open, then we don't need to tell the clients to close
			return;
		}
		Log.e(TAG, "Notifying client service of hardware disconnect.");

		if(registeredApps== null || registeredApps.isEmpty()){
			Log.w(TAG, "No clients to notify. Sending global notification.");
			Intent unregisterIntent = new Intent();
			unregisterIntent.putExtra(HARDWARE_DISCONNECTED, type.name());
			unregisterIntent.putExtra(TransportConstants.ENABLE_LEGACY_MODE_EXTRA, legacyModeEnabled);
			unregisterIntent.setAction(TransportConstants.START_ROUTER_SERVICE_ACTION_SUFFIX);
			sendBroadcast(unregisterIntent);
			return;
		}
		Message message = Message.obtain();
		message.what = TransportConstants.HARDWARE_CONNECTION_EVENT;
		Bundle bundle = new Bundle();
		bundle.putString(HARDWARE_DISCONNECTED, type.name());
		bundle.putBoolean(TransportConstants.ENABLE_LEGACY_MODE_EXTRA, legacyModeEnabled);
		message.setData(bundle);		//TODO should we add a transport event what message type?
		notifyClients(message);
		//We've notified our clients, less clean up the mess now.
		synchronized(SESSION_LOCK){
			sessionMap.clear();
			if(registeredApps==null){
				return;
			}
			registeredApps.clear();
			//for (RegisteredApp app : registeredApps.values()) {
			//	app.clearSessionIds();
			//	app.getSessionIds().add((long)-1); //Since we should be expecting at least one session.
			//}
		}
		//TODO remove
		Toast.makeText(getBaseContext(), "SDL "+ type.name()+ " Transport disconnected", Toast.LENGTH_SHORT).show();
	}
	
	public void onPacketRead(SdlPacket packet){
        try {
    		//Log.i(TAG, "******** Read packet with header: " +(packet).toString());
    		if(packet.getVersion()== 1 
    				&& packet.getFrameType() == FrameType.Control
    				&& packet.getFrameInfo() == SdlPacket.FRAME_INFO_START_SERVICE_ACK){
    			//We received a v1 packet from the head unit, this means we can't use the router service.
    			//Enable legacy mode
    			enableLegacyMode(true);
    			return;
    			
    		}
        	//Send the received packet to the registered app
        	sendPacketToRegisteredApp(packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 private final Handler mHandlerBT = new Handler() { //TODO make this generic transport handler
	        @Override
	        public void handleMessage(Message msg) {
	            switch (msg.what) {
	            	case MESSAGE_DEVICE_NAME:
	            		connectedDeviceName = msg.getData().getString(MultiplexBluetoothTransport.DEVICE_NAME);
	            		break;
	            	case MESSAGE_STATE_CHANGE:
	            		switch (msg.arg1) {
	            		case MultiplexBluetoothTransport.STATE_CONNECTED:
	            			storeConnectedStatus(true);
	            			onTransportConnected(TransportType.BLUETOOTH); //FIXME actually check
	            			break;
	            		case MultiplexBluetoothTransport.STATE_CONNECTING:
	            			// Currently attempting to connect - update UI?
	            			break;
	            		case MultiplexBluetoothTransport.STATE_LISTEN:
	            			storeConnectedStatus(false);
	            			break;
	            		case MultiplexBluetoothTransport.STATE_NONE:
	            			// We've just lost the connection
	            			storeConnectedStatus(false);
	            			if(!connectAsClient && !closing){
	            				if(!legacyModeEnabled){
	            					initBluetoothSerialService();
	            				}
	            				onTransportDisconnected(TransportType.BLUETOOTH); //FIXME actually check
	            			}
	            			break;
	            		case MultiplexBluetoothTransport.STATE_ERROR:
	            			if(mSerialService!=null){
	            				Log.d(TAG, "Bluetooth serial server error received, setting state to none, and clearing local copy");
	            				mSerialService.setStateManually(MultiplexBluetoothTransport.STATE_NONE);
	            				mSerialService = null;
	            			}
	            			
	            			break;
	            		}
	                break;
	                
	            	case MESSAGE_READ:
	                	onPacketRead((SdlPacket) msg.obj);
	        			break;
	            }
	        }
	    };
		
		public boolean writeBytesToTransport(Bundle bundle){
			if(bundle == null){
				return false;
			}
			if(mSerialService !=null && mSerialService.getState()==MultiplexBluetoothTransport.STATE_CONNECTED){
				byte[] packet = bundle.getByteArray(TransportConstants.BYTES_TO_SEND_EXTRA_NAME); 
				int offset = bundle.getInt(TransportConstants.BYTES_TO_SEND_EXTRA_OFFSET, 0); //If nothing, start at the begining of the array
				int count = bundle.getInt(TransportConstants.BYTES_TO_SEND_EXTRA_COUNT, packet.length);  //In case there isn't anything just send the whole packet.
				if(packet!=null){
					mSerialService.write(packet,offset,count);
					return true;
				}
				return false;
			}else if(sendThroughAltTransport(bundle)){
				return true;
			}
			else{
				Log.e(TAG, "Can't send data, no transport connected");
				return false;
			}	
		}
		
		
		/**
		 * This Method will send the packets through the alt transport that is connected
		 * @param array The byte array of data to be wrote out
		 * @return If it was possible to send the packet off.
		 * <p><b>NOTE: This is not guaranteed. It is a best attempt at sending the packet, it may fail.</b>
		 */
		private boolean sendThroughAltTransport(Bundle bundle){
			if(altTransportMessager!=null){
				Log.d(TAG, "Sending packet through alt transport");
				Message msg = Message.obtain();
				msg.what = TransportConstants.ROUTER_SEND_PACKET;
				msg.setData(bundle);
				return true;
			}		
			return false;		
		}
		
		/**
		 * This will send the received packet to the registered service. It will default to the single registered "foreground" app.
		 * This can be overridden to provide more specific functionality. 
		 * @param packet the packet that is received
		 * @return whether or not the sending was successful 
		 */
		public boolean sendPacketToRegisteredApp(SdlPacket packet) {
			if(registeredApps!=null && (registeredApps.size()>0)){
	    		Long appid = getAppIDForSession(packet.getSessionId()); //Find where this packet should go
	    		if(appid!=null){
	    			RegisteredApp app = registeredApps.get(appid);
	    			if(app==null){Log.e(TAG, "No app found for app id");
	    				return false;
	    			}
	    			Message message = Message.obtain();
	    			//TODO put arg1 and 2
	    			message.what = TransportConstants.ROUTER_RECEIVED_PACKET;
	    			Bundle bundle = new Bundle();
	    			bundle.putParcelable(FORMED_PACKET_EXTRA_NAME, packet);
	    			message.setData(bundle);
	    			int result = app.sendMessage(message);
	    			if(result == RegisteredApp.SEND_MESSAGE_ERROR_MESSENGER_DEAD_OBJECT){
	    				registeredApps.remove(appid); //TODO should we send out info to head unit to let them know?
	    			}
	    			return true;	//We should have sent our packet, so we can return true now
	    		}else{	//If we can't find a session for this packet we just drop the packet
	    			Log.e(TAG, "App Id was NULL!");
	    		}
	    	}
	    	return false;
		}
	    
	    
		private synchronized void closeBluetoothSerialServer(){ //FIXME change to ITransport
			if(mSerialService != null){
				mSerialService.stop();
				mSerialService = null;
			}
		}
		
		 /**
	     * bluetoothQuerryAndConnect()
	     * This function looks through the phones currently paired bluetooth devices
	     * If one of the devices' names contain "sync", or livio it will attempt to connect the RFCOMM
	     * And start SDL
	     * @return a boolean if a connection was attempted
	     */
		public synchronized boolean bluetoothQuerryAndConnect(){
			if( BluetoothAdapter.getDefaultAdapter().isEnabled()){
				Set<BluetoothDevice> pairedBT= BluetoothAdapter.getDefaultAdapter().getBondedDevices();
	        	Log.d(TAG, "Querry Bluetooth paired devices");
	        	if (pairedBT.size() > 0) {
	            	for (BluetoothDevice device : pairedBT) {
	            		if(device.getName().toLowerCase(Locale.US).contains("sync") 
	            				|| device.getName().toLowerCase(Locale.US).contains("livio")){
	            			bluetoothConnect(device);
	            				  return true;
	                	}
	            	}
	       		}
			}
			else{
				Log.e(TAG, "There was an issue with connecting as client");
			}
			return false;
			}
		
		private synchronized boolean bluetoothConnect(BluetoothDevice device){
    		Log.d(TAG,"Connecting to device: " + device.getName().toString());
			if(mSerialService == null || !mSerialService.isConnected())
			{	// Set up the Bluetooth serial object				
				mSerialService = MultiplexBluetoothTransport.getBluetoothSerialServerInstance(mHandlerBT);
			}
			// We've been given a device - let's connect to it
			if(mSerialService.getState()!=MultiplexBluetoothTransport.STATE_CONNECTING){//mSerialService.stop();
				mSerialService.connect(device);
				if(mSerialService.getState() == MultiplexBluetoothTransport.STATE_CONNECTING){
					return true;
				}
			}

			Log.d(TAG, "Bluetooth SPP Connect Attempt Completed");
			return false;
		}

		/**
		 * This method will make sure a copy of the Router Service is up and running so 
		 * the SDL enabled apps will be ready for a connection.
		 */
		public static void wakeUpBluetoothService(Context context){
			context.sendOrderedBroadcast(new Intent(WAKE_UP_BLUETOOTH_SERVICE_INTENT),null);
		}
		
		//**************************************************************************************************************************************
		//********************************************************* PREFERENCES ****************************************************************
		//**************************************************************************************************************************************
		
		private void storeConnectedStatus(boolean isConnected){
			SharedPreferences prefs = getApplicationContext().getSharedPreferences(getApplicationContext().getPackageName()+SdlBroadcastReceiver.TRANSPORT_GLOBAL_PREFS,
                    Context.MODE_WORLD_READABLE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(SdlBroadcastReceiver.IS_TRANSPORT_CONNECTED, isConnected);
            editor.commit();
		}
		
		/**
		 * This method will set the last known bluetooth connection method that worked with this phone.
		 * This helps speed up the process of connecting
		 * @param level The level of bluetooth connecting method that last worked
		 * @param prefLocation Where the preference should be stored
		 */
		public final static void setBluetoothPrefs (int level, String prefLocation) {
			if(currentContext==null){
				return;
			}
			SharedPreferences mBluetoothPrefs = currentContext.getSharedPreferences(prefLocation, Context.MODE_PRIVATE);
	    	// Write the new prefs
	    	SharedPreferences.Editor prefAdd = mBluetoothPrefs.edit();
	    	prefAdd.putInt("level", level);
	    	prefAdd.commit();
		}
		
		public final static int getBluetoothPrefs(String prefLocation)
		{		
			if(currentContext==null){
				return 0;
			}
			SharedPreferences mBluetoothPrefs = currentContext.getSharedPreferences(prefLocation, Context.MODE_PRIVATE);
			return mBluetoothPrefs.getInt("level", 0);
		}
		
		
		
	
	/* ***********************************************************************************************************************************************************************
	 * *****************************************************************  CUSTOM ADDITIONS  ************************************************************************************
	 *************************************************************************************************************************************************************************/

	private LocalRouterService getLocalBluetoothServiceComapre(){
		return this.localCompareTo;
	}
	
	/**
	 * This method is used to check for the newest version of this class to make sure the latest and greatest is up and running.
	 * @param context
	 */
	private void newestServiceCheck(final Context context){
		versionCheckTimeOutHandler = new Handler(); 
		versionCheckRunable = new Runnable() {           
            public void run() {
            	Log.i(TAG, "Starting up Version Checking ");
            	LocalRouterService local = getLocalBluetoothServiceComapre();
            	if(local!=null && ROUTER_SERVICE_VERSION_NUMBER < local.version){
            		Log.d(TAG, "There is a newer version of the Router Service, starting it up");
                	closing = true;
					closeBluetoothSerialServer();
					Intent serviceIntent = local.launchIntent;
					if(getLastReceivedStartIntent()!=null){
						serviceIntent.putExtras(getLastReceivedStartIntent());
					}
					context.startService(local.launchIntent);
					notifyAltTransportOfClose(TransportConstants.ROUTER_SHUTTING_DOWN_REASON_NEWER_SERVICE);
					if(getBaseContext()!=null){
						stopSelf();
					}else{
						onDestroy();
					}
            	}
            	else{			//Let's start up like normal
            		Log.d(TAG, "Starting up bluetooth transport");
                	startUpSequence();
            	}
            }
        };
        versionCheckTimeOutHandler.postDelayed(versionCheckRunable, VERSION_TIMEOUT_RUNNABLE); 
	}
	
	private Intent getLastReceivedStartIntent(){	
		return lastReceivedStartIntent;
	}
	
	private Long getAppIDForSession(int sessionId){
		synchronized(SESSION_LOCK){
		Log.d(TAG, "Looking for session: " + sessionId);
		Long appId = sessionMap.get(sessionId);
		if(appId==null){
			int pos;
			for (RegisteredApp app : registeredApps.values()) {
				pos = app.containsSessionId(-1); 
				if(pos != -1){
					app.setSessionId(pos,sessionId);
					appId = app.getAppId();
					sessionMap.put(sessionId, appId);
					break;
				}
			}
		}
			Log.d(TAG, "Returning App Id: " + appId);
			return appId;
		}
	}
	
	/* ****************************************************************************************************************************************
	// ***********************************************************   LEGACY   ****************************************************************
	//*****************************************************************************************************************************************/
	private boolean legacyModeEnabled = false;
	
	private void enableLegacyMode(boolean enable){
		Log.d(TAG, "Enable legacy mode: " + enable);
		legacyModeEnabled = enable; //We put this at the end to avoid a race condition between the bluetooth d/c and notify of legacy mode enabled

		if(legacyModeEnabled){
			//So we need to let the clients know they need to host their own bluetooth sessions because the currently connected head unit only supports a very old version of SDL/Applink
			//Start by closing our own bluetooth connection. The following calls will handle actually notifying the clients of legacy mode
			closeBluetoothSerialServer();			
			//Now wait until we get a d/c, then the apps should shut their bluetooth down and go back to normal
			
		}//else{}

	}
	
	/* ****************************************************************************************************************************************
	// ***********************************************************   UTILITY   ****************************************************************
	//*****************************************************************************************************************************************/
	
	private void debugPacket(byte[] bytes){
		//DEBUG
		
		if(bytes[0] != 0x00){
			Log.d(TAG, "Writing packet with header: " + BitConverter.bytesToHex(bytes, 12)); //just want the header
		}else{
			
			//Log.d(TAG, "Writing packet with binary header: " + BitConverter.bytesToHex(bytes, 12)); //just want the header
		//int length = bytes.length-12;
		if(bytes.length<=8){
			Log.w(TAG, "No payload to debug or too small");
			return;
		}
		//Check first byte if 0, make to json
		char[] buffer = new char[bytes.length];
		for(int i = 12;i<bytes.length;i++){
			 buffer[i-12] = (char)(bytes[i] & 0xFF);
		}
		try {
			
			JSONObject object = new JSONObject(new String(buffer));
			Log.d(TAG, "JSON: " + object.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
		
	}
	
    

	
	/* ****************************************************************************************************************************************
	// **********************************************************   TINY CLASSES   ************************************************************
	//*****************************************************************************************************************************************/

	/**
	 *This class enables us to compare two router services
	 * from different apps and determine which is the newest
	 * and therefore which one should be the one spun up.
	 * @author Joey Grover
	 *
	 */
	class LocalRouterService{
		Intent launchIntent = null;
		int version = 0;
		
		private LocalRouterService(Intent intent, int version){
			this.launchIntent = intent;
			this.version = version;
		}
		/**
		 * Check if input is newer than this version
		 * @param service
		 * @return
		 */
		public boolean isNewer(LocalRouterService service){
			return (service.version>this.version);
		}
		
	}
	
	
	/**
	 * This class helps keep track of all the different sessions established with the head unit
	 * and to which app they belong to.
	 * @author Joey Grover
	 *
	 */
	class RegisteredApp {
		protected static final int SEND_MESSAGE_SUCCESS 							= 0x00;
		protected static final int SEND_MESSAGE_ERROR_MESSAGE_NULL 					= 0x01;
		protected static final int SEND_MESSAGE_ERROR_MESSENGER_NULL 				= 0x02;
		protected static final int SEND_MESSAGE_ERROR_MESSENGER_GENERIC_EXCEPTION 	= 0x03;
		protected static final int SEND_MESSAGE_ERROR_MESSENGER_DEAD_OBJECT 		= 0x04;

		long appId;
		Messenger messenger;
		Vector<Long> sessionIds;
	
		/**
		 * This is a simple class to hold onto a reference of a registered app.
		 * @param appId
		 * @param messenger
		 */
		public RegisteredApp(long appId, Messenger messenger){			
			this.appId = appId;
			this.messenger = messenger;
			this.sessionIds = new Vector<Long>();
		}
		public long getAppId() {
			return appId;
		}

		/**
		 * This is a convenience variable and may not be used or useful in different protocols
		 * @return
		 */
		public Vector<Long> getSessionIds() {
			return sessionIds;
		}
		
		/**
		 * Returns the position of the desired object if it is contained in the vector. If not it will return -1.
		 * @param id
		 * @return
		 */
		public int containsSessionId(long id){
			return sessionIds.indexOf(id);
		}
		
		/**
		 * @param sessionId
		 */
		public void setSessionId(int position,long sessionId) throws ArrayIndexOutOfBoundsException {
			this.sessionIds.set(position, (long)sessionId); 
		}
		
		public void clearSessionIds(){
			this.sessionIds.clear();
		}
		
		public int sendMessage(Message message){
			if(this.messenger == null){return SEND_MESSAGE_ERROR_MESSENGER_NULL;}
			if(message == null){return SEND_MESSAGE_ERROR_MESSAGE_NULL;}
			try {
				this.messenger.send(message);
				return SEND_MESSAGE_SUCCESS;
			} catch (RemoteException e) {
				e.printStackTrace();
				if(e instanceof DeadObjectException){
					return SEND_MESSAGE_ERROR_MESSENGER_DEAD_OBJECT;
				}else{
					return SEND_MESSAGE_ERROR_MESSENGER_GENERIC_EXCEPTION;
				}
			}
		}
	}
	
	
}
