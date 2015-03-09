package com.smartdevicelink.transport;

import static com.smartdevicelink.transport.TransportConstants.CONNECTED_DEVICE_STRING_EXTRA_NAME;
import static com.smartdevicelink.transport.TransportConstants.HARDWARE_DISCONNECTED;
import static com.smartdevicelink.transport.TransportConstants.PACKET_TO_SEND_EXTRA_NAME;
import static com.smartdevicelink.transport.TransportConstants.PING_REGISTERED_SERVICE_REPLY_EXTRA;
import static com.smartdevicelink.transport.TransportConstants.SEND_PACKET_ACTION;
import static com.smartdevicelink.transport.TransportConstants.SEND_PACKET_TO_APP_LOCATION_EXTRA_NAME;
import static com.smartdevicelink.transport.TransportConstants.SEND_PACKET_TO_ROUTER_LOCATION_EXTRA_NAME;
import static com.smartdevicelink.transport.TransportConstants.UNREGISTER_EXTRA;
import static com.smartdevicelink.transport.TransportConstants.UNREGISTER_EXTRA_REASON_PING_TIMEOUT;
import static com.smartdevicelink.transport.TransportConstants.WAKE_UP_BLUETOOTH_SERVICE_INTENT;

import java.util.HashMap;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
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
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.protocol.enums.FrameType;
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
	
	protected static final int ROUTER_SERVICE_VERSION_NUMBER = 4;
	
	public static final String START_ROUTER_SERVICE_ACTION 					= "sdl.router"+ TransportConstants.START_ROUTER_SERVICE_ACTION_SUFFIX;
	public static final String REGISTER_NEWER_SERVER_INSTANCE_ACTION		= "com.sdl.android.newservice";
	public static final String START_SERVICE_ACTION							= "sdl.router.startservice";
	public static final String REGISTER_WITH_ROUTER_ACTION 					= "com.sdl.android.register"; 
	public static final String REQUEST_EXTRA_SESSION_FOR_APPID_ACTION 	    = "com.sdl.android.session.addition"; 
	public static final String EXTRA_SESSION_APPID_EXTRA 	    			= "appid"; 
	
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
    private LocalBluetoothService localCompareTo = null;
    private final static int VERSION_TIMEOUT_RUNNABLE = 750; 
	
	
    private Intent lastReceivedStartIntent = null;
	public static HashMap<Long,RegisteredApp> registeredApps; //Trying static, but i dont like it
	SparseArray<Long> sessionMap;
	private Object SESSION_LOCK;
	
	private static String altTransportAddress = null;

	private String  connectedDeviceName = "";			//The name of the connected Device
	private boolean startSequenceComplete = false;
	private boolean alreadyUpdatedForegroundApp = false;
	
	
	
	
	
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
			if(intent.getAction().equals(TransportConstants.REQUEST_BT_CLIENT_CONNECT)
				&& intent.getBooleanExtra(TransportConstants.CONNECT_AS_CLIENT_BOOLEAN_EXTRA, false)
				&& !connectAsClient){		//We check this flag to make sure we don't try to connect over and over again. On D/C we should set to false
				//Log.d(TAG,"Attempting to connect as bt client");
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				connectAsClient = true;
				if(device==null || !bluetoothConnect(device)){
					Log.e(TAG, "Unable to connect to bluetooth device");
					connectAsClient = false;
				}
				
			}
			//Let's see if they wanted to unregister
			if(intent.hasExtra(UNREGISTER_EXTRA)){
				long appId = intent.getLongExtra(UNREGISTER_EXTRA, 0);
				//Log.i(TAG, appId + " has just been unregistered with SDL Router Service");
				registeredApps.remove(appId); //Should remove if it exists and nothing happens if it doesn't. Chill as hell.
				return;
			}
			//Let's grab where to reply to this intent at. We will keep it temp right now because we may have to deny registration
			String tempSendBackAction =intent.getStringExtra(SEND_PACKET_TO_APP_LOCATION_EXTRA_NAME);
			long appId = intent.getLongExtra(TransportConstants.APP_ID_EXTRA, 0);
			//Log.d(TAG, "Attempting to registered: " + appId + " at: " +tempSendBackAction );
			Intent registrationIntent = new Intent();
			registrationIntent.setAction(tempSendBackAction);
			RegisteredApp app = new RegisteredApp(appId,tempSendBackAction);
			registrationIntent = registerApp(app,intent);
			sendBroadcast(registrationIntent);	
		}
	};
	/**
	 * This will register the app with the service, it will be the one main point of contact for hardware/app communication
	 * @param appReceiverName The return address of the intent that was sent
	 * @param intent The intent we received in the register broadcast receiver
	 * @return An intent that will tell the registering app that it was successful
	 */
	private synchronized Intent registerApp(RegisteredApp app, Intent intent){
		Intent registrationIntent = new Intent();
		registrationIntent.setAction(app.getReplyAddress());
		//whereToSendPackets = appReceiverName;
		registeredApps.put(app.getAppId(), app);
		onAppRegistered(app, intent, registrationIntent);
		
		return registrationIntent;
	}
	
	private void onAppRegistered(RegisteredApp app, Intent receivedIntent, Intent beingSent){
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

		beingSent.putExtra(SEND_PACKET_TO_ROUTER_LOCATION_EXTRA_NAME, SEND_PACKET_ACTION);
		if(MultiplexBluetoothTransport.currentlyConnectedDevice!=null){
			beingSent.putExtra(CONNECTED_DEVICE_STRING_EXTRA_NAME, MultiplexBluetoothTransport.currentlyConnectedDevice);
		}

		Log.i(TAG, app.getReplyAddress() + " has just been registered with Livio Bluetooth Service");
	}
	
	
	
	
	/**Receiver for sending out packets*/
    BroadcastReceiver outPacketsReceiver = new BroadcastReceiver() 
	{
		@Override
		public void onReceive(Context context, Intent intent) 
		{
			if(intent.hasExtra(TransportConstants.BYTES_TO_SEND_EXTRA_NAME)){
				byte[] packet = intent.getByteArrayExtra(TransportConstants.BYTES_TO_SEND_EXTRA_NAME); 
				int offset = intent.getIntExtra(TransportConstants.BYTES_TO_SEND_EXTRA_OFFSET, 0); //If nothing, start at the begining of the array
				int count = intent.getIntExtra(TransportConstants.BYTES_TO_SEND_EXTRA_COUNT, packet.length);  //In case there isn't anything just send the whole packet.
				
				send(packet,offset,count);
			}
			
		}
	};
	
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
								localCompareTo = new LocalBluetoothService(savedIntent,versionOfIntent);
							}
							return;
							
						}
					}
			};
	
	/**
	 * If the user disconnects the bluetooth device we will want to stop Livio Connect and our current
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

					closeBluetoothSerialServer();
					connectAsClient=false;
					
					if(action!=null && intent.getAction().equalsIgnoreCase("android.bluetooth.adapter.action.STATE_CHANGED") 
							&&(	(BluetoothAdapter.getDefaultAdapter().getState() == BluetoothAdapter.STATE_TURNING_OFF) 
							|| (BluetoothAdapter.getDefaultAdapter().getState() == BluetoothAdapter.STATE_OFF))){
							Log.d(TAG, "Bluetooth is shutting off, LivioBluetoothService is closing.");
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
	
		/**Receiver for alt transport*/
		BroadcastReceiver altTransportReceiver = new BroadcastReceiver(){ //connection status and readReads in data
			@Override
			public void onReceive(Context context, Intent intent)
			{
				if(intent.hasExtra(TransportConstants.ALT_TRANSPORT_CONNECTION_STATUS_EXTRA)){ //Connection status
					Intent ackIntent = new Intent();
					switch(intent.getIntExtra(TransportConstants.ALT_TRANSPORT_CONNECTION_STATUS_EXTRA, 0)){
						case TransportConstants.ALT_TRANSPORT_CONNECTED:
							altTransportAddress = intent.getStringExtra(TransportConstants.ALT_TRANSPORT_ADDRESS_EXTRA);
							ackIntent.setAction(altTransportAddress);
							//ackIntent.putExtra(name, value);;
							storeConnectedStatus(true);
							sendBroadcast(ackIntent);
							Toast.makeText(getBaseContext(), "Livio Connect Enabled", Toast.LENGTH_SHORT).show();
							break;
						case TransportConstants.ALT_TRANSPORT_DISCONNECTED:
							storeConnectedStatus(false);
							ackIntent.setAction(altTransportAddress);
							sendBroadcast(ackIntent);
							altTransportAddress = null;
							onTransportDisconnected(TransportType.USB); //Make sure the client knows the hardware has disconnected
							shouldServiceKeepRunning(null); //this will close the service if bluetooth is not available
							break;
					}
				}else if(intent.hasExtra(TransportConstants.ALT_TRANSPORT_READ)){ //Come in as char[]
					//Send to the app!
					//Toast.makeText(getBaseContext(), "Got here 1", Toast.LENGTH_SHORT).show();
					//FIXME sendPacketToRegisteredApp(intent.getCharArrayExtra(TransportConstants.ALT_TRANSPORT_READ));
				}
			}
		};
		
		BroadcastReceiver sdlCustomReceiver = new BroadcastReceiver() 
		{
			@Override
			public void onReceive(Context context, Intent intent) 
			{
				if(intent.getAction().equals(REQUEST_EXTRA_SESSION_FOR_APPID_ACTION)){
					if(intent.hasExtra(EXTRA_SESSION_APPID_EXTRA)){
						synchronized(SESSION_LOCK){
							if(registeredApps!=null){
								RegisteredApp app = registeredApps.get(intent.getLongExtra(EXTRA_SESSION_APPID_EXTRA, (long)-1));
								if(app!=null){
									app.getSessionIds().add((long)-1); //Adding an extra session
								}
							}
							
						}
					}
				}
				
			}
		};
		
/* **************************************************************************************************************************************
***********************************************  Broadcast Receivers End  **************************************************************
****************************************************************************************************************************************/

	
		
		
/* **************************************************************************************************************************************
***********************************************  Life Cycle **************************************************************
****************************************************************************************************************************************/

	@Override
	public IBinder onBind(Intent intent) {
		return null; 
	}

	
	//FIXME this is where we need to handle the logic of where to send the packet
	private void notifyClient(Intent intent){
		if(intent==null){
			return;
		}
		Log.d(TAG, "Notifying "+ registeredApps.size()+ " clients");
		//registeredApps;
		for (RegisteredApp app : registeredApps.values()) {
			intent.setAction(app.getReplyAddress());
			sendBroadcast(intent);
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
		IntentFilter filter = new IntentFilter(REQUEST_EXTRA_SESSION_FOR_APPID_ACTION);
		registerReceiver(sdlCustomReceiver,filter);
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
		filter.addAction(TransportConstants.REQUEST_BT_CLIENT_CONNECT);
		registerReceiver(mainServiceReceiver,filter);
		
		registerReceiver(altTransportReceiver, new IntentFilter(TransportConstants.ALT_TRANSPORT_RECEIVER)); //For reading/writing off alt transport

		IntentFilter sendFilter = new IntentFilter();
		sendFilter.addAction(SEND_PACKET_ACTION);
		sendFilter.addAction(TransportConstants.SEND__GLOBAL_PACKET_ACTION);
		
		registerReceiver(outPacketsReceiver,sendFilter); //This is only for sending out packets
		
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
			Log.i(TAG, "Received an intent with request to register service: ");
			 Intent registerIntent = new Intent(REGISTER_WITH_ROUTER_ACTION);
			 registerIntent.putExtras(intent);
			 if(startSequenceComplete){
				 sendBroadcast(registerIntent);
			 }else{
				 long appId = intent.getLongExtra(TransportConstants.APP_ID_EXTRA, 0);
				 RegisteredApp app = new RegisteredApp(appId,intent.getStringExtra(SEND_PACKET_TO_APP_LOCATION_EXTRA_NAME));
				 sendBroadcast(registerApp(app, registerIntent));
			 }
		 }else if(intent.hasExtra(TransportConstants.ALT_TRANSPORT_ADDRESS_EXTRA)){
			 Log.d(TAG, "Service started by alt transport");
			 altTransportAddress = intent.getStringExtra(TransportConstants.ALT_TRANSPORT_ADDRESS_EXTRA);
			 Intent ackIntent = new Intent();
			 ackIntent.setAction(altTransportAddress);
			 //ackIntent.putExtra(name, value);;
			 storeConnectedStatus(true);
			 sendBroadcast(ackIntent);
			 lastReceivedStartIntent = intent;
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
	    }
	
	private void unregisterAllReceivers(){
		try{
			unregisterReceiver(registerAnInstanceOfSerialServer);		///This should be first. It will always be registered, these others may not be and cause an exception.
			unregisterReceiver(sdlCustomReceiver);
			unregisterReceiver(mListenForDisconnect);
			unregisterReceiver(mainServiceReceiver);
			unregisterReceiver(outPacketsReceiver);
			unregisterReceiver(altTransportReceiver);
		}catch(Exception e){}
	}
	
	/* **************************************************************************************************************************************
	***********************************************  Helper Methods **************************************************************
	****************************************************************************************************************************************/

	public boolean hasUpdatedForegroundApp(){
		return alreadyUpdatedForegroundApp;
	}
	public void setHasUpdatedForegroundApp(boolean updated){
		alreadyUpdatedForegroundApp= updated;
	}
	
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
	 * 1. If the app has Livio Connect shut off, 									shut down
	 * 2. if The app has an Alt Transport address or was started by one, 			stay open
	 * 3. If Bluetooth is off/NA	 												shut down
	 * 4. Anything else					
	 */
	public boolean shouldServiceKeepRunning(Intent intent){
		Log.d(TAG, "Determining if this service should remain open");
		
		if(altTransportAddress!=null 
				|| (intent!=null && intent.hasExtra(TransportConstants.ALT_TRANSPORT_ADDRESS_EXTRA))){
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
		storeConnectedStatus(false);
		if(getBaseContext()!=null){
			stopSelf();
		}else{
			onDestroy();
		}
	}
	private synchronized void initBluetoothSerialService(){
		Log.i(TAG, "Iniitializing Bluetooth Serial Class");
		//LivioConnect.getInstance().setProtocolVersion(5); //FIXME get rid of
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
              // Start the Bluetooth chat services
            	mSerialService.start();
            }

		}
	}
	
	public void onTransportConnected(final TransportType type){
		//TODO remove
		Toast.makeText(getBaseContext(), "SDL "+ type.name()+ " Transport Connected", Toast.LENGTH_SHORT).show();

		Intent startService = new Intent();  //FIXME we might need to change this considering how we might allow different apps to use this services (ie, not a single foreground app) 
		startService.setAction(START_SERVICE_ACTION);
		startService.putExtra(TransportConstants.START_ROUTER_SERVICE_SDL_ENABLED_EXTRA, true);
    	sendBroadcast(startService);    	
		//HARDWARE_CONNECTED
	}
	
	public void onTransportDisconnected(TransportType type){
		if(altTransportAddress!=null){  //FIXME why is this here?
			return;
		}
		Log.e(TAG, "Notifying client service of hardware disconnect.");
		
		Intent unregisterIntent = new Intent();
		unregisterIntent.putExtra(HARDWARE_DISCONNECTED, type.name());
		unregisterIntent.putExtra(TransportConstants.ENABLE_LEGACY_MODE_EXTRA, legacyModeEnabled);
		if(registeredApps== null || registeredApps.isEmpty()){
			Log.w(TAG, "No clients to notify. Sending global notification.");
			unregisterIntent.setAction(TransportConstants.START_ROUTER_SERVICE_ACTION_SUFFIX);
			sendBroadcast(unregisterIntent);
			return;
		}
		
		notifyClient(unregisterIntent);
		//We've notified our clients, less clean up the mess now.
		synchronized(SESSION_LOCK){
			sessionMap.clear();
			if(registeredApps==null){
				return;
			}
			for (RegisteredApp app : registeredApps.values()) {
				app.clearSessionIds();
				app.getSessionIds().add((long)-1); //Since we should be expecting at least one session. God i hope this works.
			}
		}
		//TODO remove
		Toast.makeText(getBaseContext(), "SDL "+ type.name()+ " Transport disconnected", Toast.LENGTH_SHORT).show();
	}
	
	//FIXME basically delete this function as it only calls one other method
	public void onPacketRead(SdlPacket packet){

        try {//TODO remove debugging
    		Log.i(TAG, "******** Read packet with header: " +(packet).toString());
    		if(packet.getVersion()== 1 
    				&& packet.getFrameType() == FrameType.Control
    				&& packet.getFrameInfo() == SdlPacket.FRAME_INFO_START_SERVICE_ACK){
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
	            			alreadyUpdatedForegroundApp = false;
	            			onTransportConnected(TransportType.BLUETOOTH); //FIXME actually check
	            			break;
	            		case MultiplexBluetoothTransport.STATE_CONNECTING:
	            			// Currently attempting to connect - update UI?
	            			break;
	            		case MultiplexBluetoothTransport.STATE_LISTEN:
	            			storeConnectedStatus(false);
	            			break;
	            		case MultiplexBluetoothTransport.STATE_NONE:
	            			// We've just lost the connection - update UI?
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

	    public boolean send(byte[] bytes,int offset, int count){ 
	    	if(bytes==null){
	    		return false;
	    	}
	    	return writeBytesToTransport(bytes,offset,count);
	    }
		
		
		public boolean writeBytesToTransport(byte[] byteArray,int offset,int count){
			//TODO remove debug packet
			debugPacket(byteArray);
			if(mSerialService !=null && mSerialService.getState()==MultiplexBluetoothTransport.STATE_CONNECTED){
				mSerialService.write(byteArray,offset,count);
				return true;
			}else if(sendThroughAltTransport(byteArray)){
				return true;
			}
			else{
				Log.e(TAG, "Can't send data, serial service is null");
				return false;
			}
		}
		
		
		
		/**
		 * This Method will send the packets through the alt transport that is connected
		 * @param array
		 * @return If it was possible to send the packet off.
		 * <p><b>NOTE: This is not guaranteed. It is a best attempt at sending the packet, it may fail.</b>
		 */
		private boolean sendThroughAltTransport(byte[] array){
			if(altTransportAddress!=null){
				Log.d(TAG, "Sending packet through alt transport");
				Intent intent = new Intent(altTransportAddress);
				intent.putExtra(TransportConstants.ALT_TRANSPORT_WRITE, array);
				sendBroadcast(intent);
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
		public boolean sendPacketToRegisteredApp(Parcelable packet) {
			if(registeredApps!=null && (registeredApps.size()>0)){
	    		Intent sendingPacketToClientIntent = new Intent();
	    		//sendingPacketToClientIntent.setAction(whereToSendPackets);
	    		sendingPacketToClientIntent.putExtra(PACKET_TO_SEND_EXTRA_NAME, packet); //FIXME this bullshit. do we check here for app id?
	    		
	    		Long appid =getAppIDForSession(((SdlPacket)packet).getSessionId());
	    		if(appid!=null){
	    			sendingPacketToClientIntent.setAction(registeredApps.get(appid).getReplyAddress());
	    			sendBroadcast(sendingPacketToClientIntent);
	    		}else{
	    			Log.e(TAG, "App Id was NULL!");
	    			return false;
	    		}
	    		//notifyClient(sendingPacketToClientIntent);
	     		//Log.d(TAG, "should have sent bytes: " + packet.length);
	    		return true;
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
	     * If one of the devices' names contain "fire", "bcsm", or livio it will attempt to connect the RFCOMM
	     * And start Livio Connect
	     * @return a boolean if a connection was attempted
	     */
		public synchronized boolean bluetoothQuerryAndConnect(){
			if( BluetoothAdapter.getDefaultAdapter().isEnabled()){
				Set<BluetoothDevice> pairedBT= BluetoothAdapter.getDefaultAdapter().getBondedDevices();
	        	Log.d(TAG, "Querry Bluetooth paired devices");
	        	if (pairedBT.size() > 0) {
	            	for (BluetoothDevice device : pairedBT) {
	                	//There will be a list of names available soon from the authentication server
	            		if(device.getName().toLowerCase(Locale.US).contains("bcsm") 
	            				|| device.getName().toLowerCase(Locale.US).contains("fire")
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
			if(!device.getName().equalsIgnoreCase("livio_lvc02a")) {
				if(mSerialService.getState()!=MultiplexBluetoothTransport.STATE_CONNECTING){//mSerialService.stop();
				mSerialService.connect(device);
					if(mSerialService.getState() == MultiplexBluetoothTransport.STATE_CONNECTING){
						return true;
					}
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
            //Legacy call for < v5.01
            prefs = getSharedPreferences(SdlBroadcastReceiver.TRANSPORT_GLOBAL_PREFS,
                    Context.MODE_WORLD_READABLE);
            editor = prefs.edit();
            editor.putBoolean(SdlBroadcastReceiver.IS_TRANSPORT_CONNECTED, isConnected);
            editor.commit();
		}
		
		public final static void setBluetoothPrefs (int Level, String prefLocation) {
			if(currentContext==null){
				return;
			}
			SharedPreferences mBluetoothPrefs = currentContext.getSharedPreferences(prefLocation, Context.MODE_PRIVATE);
	    	// Write the new prefs
	    	SharedPreferences.Editor prefAdd = mBluetoothPrefs.edit();
	    	prefAdd.putInt("level", Level);
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

	
	/**
	 * Use this method to let the router service know that you are requesting an additional service from the head unit. This should not be used for the first service request, 
	 * as every registered app will automatically have one service request pending.
	 * @param context
	 * @param appId
	 */
	public static void requestAdditionalService(Context context, long appId){
		Intent request = new Intent(SdlRouterService.REQUEST_EXTRA_SESSION_FOR_APPID_ACTION);
		request.putExtra(EXTRA_SESSION_APPID_EXTRA, appId);
		context.sendBroadcast(request);
	}
	
	private LocalBluetoothService getLocalBluetoothServiceComapre(){
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
            	LocalBluetoothService local = getLocalBluetoothServiceComapre();
            	if(local!=null && ROUTER_SERVICE_VERSION_NUMBER < local.version){
            		Log.d(TAG, "There is a newer version of the Router Service, starting it up");
                	closing = true;
					closeBluetoothSerialServer();
					Intent serviceIntent = local.launchIntent;
					if(getLastReceivedStartIntent()!=null){
						serviceIntent.putExtras(getLastReceivedStartIntent());
					}
					context.startService(local.launchIntent);
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
		//Log.d(TAG, "Looking for session: " + sessionId);
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
			//Log.d(TAG, "Returning App Id: " + appId);
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
			//Start by closing our own bluetooth connection
			closeBluetoothSerialServer();
			
			//Now let the clients know they need to start their own bluetooth
			//Intent legacyIntent = new Intent();
			//legacyIntent.putExtra(TransportConstants.ENABLE_LEGACY_MODE_EXTRA, enable);
			//notifyClient(legacyIntent);
			
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
	class LocalBluetoothService{
		Intent launchIntent = null;
		int version = 0;
		
		private LocalBluetoothService(Intent intent, int version){
			this.launchIntent = intent;
			this.version = version;
		}
		/**
		 * Check if input is newer than this version
		 * @param service
		 * @return
		 */
		public boolean isNewer(LocalBluetoothService service){
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
		//TODO add some sort of proper in foreground, has focus, etc. flag. (if needed)
		long appId;
		String replyAddress; //possible package name? Probably a good idea. meh for now
		Vector<Long> sessionIds;
		
		/**
		 * This is a simple class to hold onto a reference of a registered app. This is an immutable class. Deal with it.
		 * @param appId
		 * @param replyAddress
		 */
		public RegisteredApp(long appId, String replyAddress){
			this.appId = appId;
			this.replyAddress = replyAddress;
			this.sessionIds = new Vector<Long>();
			this.sessionIds.add((long) -1);
		}

		public long getAppId() {
			return appId;
		}

		public String getReplyAddress() {
			return replyAddress;
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
		 * This is a convince variable and may not be used or useful in different protocols
		 * @param sessionId
		 */
		public void setSessionId(int position,long sessionId) throws ArrayIndexOutOfBoundsException {
			this.sessionIds.set(position, (long)sessionId); 
		}
		
		public void clearSessionIds(){
			this.sessionIds.clear();
		}
		
	}
	
	
}
