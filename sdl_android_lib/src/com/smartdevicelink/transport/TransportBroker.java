package com.smartdevicelink.transport;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
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

import com.smartdevicelink.transport.enums.TransportType;


public class TransportBroker {
	
	private static final String TAG = "SdlTransportBroker";
	private final int MAX_BINDER_SIZE = 1000000/4; //~1MB/4 We do this as a safety measure. IPC only allows 1MB for everything. We should never fill more than 25% of the buffer so here we make sure we stay under that
	private final String WHERE_TO_REPLY_PREFIX	 = "com.sdl.android.";
	private static String appId = null,whereToReply = null;
	private Context currentContext = null;
	
	private Object INIT_LOCK = new Object();
	
	private TransportType queuedOnTransportConnect = null;
	
	Messenger routerServiceMessenger = null;
	final Messenger clientMessenger = new Messenger(new ClientHandler());

	boolean isBound = false, registeredWithRouterService = false;
	private String routerPackage = null, routerClassName = null;
	private ComponentName routerService = null;
	
	
	private ServiceConnection routerConnection;
	
	private void initRouterConnection(){
		routerConnection= new ServiceConnection() {

			public void onServiceConnected(ComponentName className, IBinder service) {
				Log.d(TAG, "Bound to service " + className.toString());
				routerServiceMessenger = new Messenger(service);
				isBound = true;
				//So we just established our connection
				//Register with router service
				sendRegistrationMessage();    
			}

			public void onServiceDisconnected(ComponentName className) {
				Log.d(TAG, "UN-Bound to service");
				routerServiceMessenger = null;
				registeredWithRouterService = false;
				isBound = false;
				onHardwareDisconnected(null);
			}
		};
	}

    private synchronized boolean sendMessageToRouterService(Message message){
    	if(message == null){
    		Log.w(TAG, "Attempted to send null message");
    		return false;
    	}
    	Log.i(TAG, "Attempting to send message type - " + message.what);
    	if(isBound && routerServiceMessenger !=null){
    		if(registeredWithRouterService 
    				|| message.what == TransportConstants.ROUTER_REGISTER_CLIENT){ //We can send a message if we are registered or are attempting to register
    			try {
					routerServiceMessenger.send(message);
					return true;
				} catch (RemoteException e) {
					e.printStackTrace();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					return sendMessageToRouterService(message);
					
				}
    		}else{
    			Log.e(TAG, "Unable to send message to router service. Not registered.");
    			return false;
    		}
    	}else{
    		Log.e(TAG, "Unable to send message to router service. Not bound.");
    		return false;
    	}
    }
    
    BroadcastReceiver routerDiscoveryReceiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent!=null){
				if(intent.hasExtra(TransportConstants.BIND_LOCATION_PACKAGE_NAME_EXTRA)
						&& intent.hasExtra(TransportConstants.BIND_LOCATION_CLASS_NAME_EXTRA)){
					//We now know the location of the router service that is currently up and running
					routerPackage = intent.getStringExtra(TransportConstants.BIND_LOCATION_PACKAGE_NAME_EXTRA);
					routerClassName = intent.getStringExtra(TransportConstants.BIND_LOCATION_CLASS_NAME_EXTRA);
					if(routerConnection==null){
						initRouterConnection();
					}
					sendBindingIntent(); //TODO check if we actually binded
				}	
			}			
		}
    	
    };
    
    /**
     * Handler of incoming messages from service.
     */
    class ClientHandler extends Handler {
        ClassLoader loader = getClass().getClassLoader();
    	@Override
        public void handleMessage(Message msg) {
        	
        	Bundle bundle = msg.getData();
        	if(bundle!=null){
        		bundle.setClassLoader(loader);
        	}
        	
        	Log.d(TAG, "Bundle: "  + bundle.toString());
            /* DO NOT MOVE
             * This needs to be first to make sure we already know if we are attempting to enter legacy mode or not
             */
        	if(bundle !=null 
        			&& bundle.containsKey(TransportConstants.ENABLE_LEGACY_MODE_EXTRA)){
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
        				}else if(SdlBroadcastReceiver.isTransportConnected(getContext())){
        					onHardwareConnected(null); //FIXME to include type
        				}
            		}else{ //We were denied registration to the router service, let's see why
            			registeredWithRouterService = false; 
            			Log.w(TAG, "Registration denied from router service. Reason - " + msg.arg1);
            		}
            	
            		break;
            	case TransportConstants.ROUTER_UNREGISTER_CLIENT_RESPONSE:
            		if(msg.arg1==TransportConstants.UNREGISTRATION_RESPONSE_SUCESS){
            			//TODO We've been unregistered. Now what?
            			
            			
            		}else{ //We were denied our unregister request to the router service, let's see why
            			Log.w(TAG, "Unregister request denied from router service. Reason - " + msg.arg1);
            			//Do we care?
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
            		}else{
            			Log.w(TAG, "Flase positive packet reception");
            		}
            		break;
            	case TransportConstants.HARDWARE_CONNECTION_EVENT:
        			if(bundle.containsKey(TransportConstants.HARDWARE_DISCONNECTED)){
        				//We should shut down, so call 
        				Log.d(TAG, "Hardware disconnected");
        				onHardwareDisconnected(TransportType.valueOf(bundle.getString(TransportConstants.HARDWARE_DISCONNECTED)));
        				break;
        			}
        			
        			if(bundle.containsKey(TransportConstants.HARDWARE_CONNECTED)){
        				onHardwareConnected(TransportType.valueOf(bundle.getString(TransportConstants.HARDWARE_CONNECTED)));
        				break;
        			}
            		break;
            	default:
                    super.handleMessage(msg);
            }   
            
        }
    }
		
		
		
		/***************************************************************************************************************************************
		***********************************************  Life Cycle  **************************************************************
		****************************************************************************************************************************************/	
			
		
		@SuppressLint("SimpleDateFormat")
		public TransportBroker(Context context, String appId, ComponentName service){
			synchronized(INIT_LOCK){
				initRouterConnection();
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
				currentContext.registerReceiver(routerDiscoveryReceiver, new IntentFilter(whereToReply)); //TODO this could all move since we don't always need it
				this.routerService = service;
			}
		}

		/**
		 * This beings the initial connection with the router service.
		 */
		public boolean start(){
			Log.d(TAG, "Starting up transport broker for " + whereToReply);
			synchronized(INIT_LOCK){
				if(currentContext==null){
					throw new IllegalStateException("This instance can't be started since it's local reference of context is null. Ensure when suppling a context to the TransportBroker that it is valid");
				}
				if(routerConnection==null){
					initRouterConnection();
				}
				//Log.d(TAG, "Registering our reply receiver: " + whereToReply);
				return registerWithRouterService();
			}
		}
		
		public void resetSession(){
			Log.d(TAG, "RESETING transport broker for " + whereToReply);
			synchronized(INIT_LOCK){
				unregisterWithRouterService();
				routerServiceMessenger = null; //TODO make sure theres nothing else we need
				queuedOnTransportConnect = null;
				unBindFromRouterService();
			}
		}
		/**
		 * This method will end our communication with the router service. 
		 */
		public void stop(){
			Log.d(TAG, "STOPPING transport broker for " + whereToReply);
			synchronized(INIT_LOCK){
				unregisterWithRouterService();
				unBindFromRouterService();
				routerServiceMessenger = null;
				queuedOnTransportConnect = null;
				try{
					if(currentContext!=null){
						currentContext.unregisterReceiver(routerDiscoveryReceiver); //Where we get packets from the Bluetooth Service	
					}
				}catch(IllegalArgumentException e){
					Log.w(TAG, "Receiver was never registered. Not a big deal.");
				}
				currentContext = null;
				
			}
		}
		
		private void unBindFromRouterService(){
			try{
				if(getContext()!=null && routerConnection!=null){
					getContext().unbindService(routerConnection);
				}else{
					Log.w(TAG, "Unable to unbind from router service, context was null");
				}
				
			}catch(IllegalArgumentException e){
				//This is ok
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
				unBindFromRouterService();
				routerServiceMessenger = null;
				routerConnection = null;
				queuedOnTransportConnect = null;
			}
		}
		
		public boolean onHardwareConnected(TransportType type){
			synchronized(INIT_LOCK){
				if(routerServiceMessenger==null){
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
		            this.routerClassName = service.service.getClassName();
		            this.routerPackage = service.service.getPackageName();
		    		return true;
		        }
		    }			
			return false;
		}
		
		
		public boolean sendPacketToRouterService(byte[] bytes, int offset, int count){ //We use ints because that is all that is supported by the outputstream class
			Log.d(TAG,whereToReply + "Sending packet to router service");
			
			if(routerServiceMessenger==null){
				Log.d(TAG,whereToReply + " tried to send packet, but no where to send");
				return false;
			}
			if(bytes == null 
					|| offset<0 
					|| count<0 
					|| count>(bytes.length-offset)){
				Log.w(TAG,whereToReply + "incorrect params supplied");
				return false;
			}
			if(bytes.length<MAX_BINDER_SIZE){//Determine if this is under the packet length.
				Message message = Message.obtain(); //Do we need to always obtain new? or can we just swap bundles?
				message.what = TransportConstants.ROUTER_SEND_PACKET;
				Bundle bundle = new Bundle();
				bundle.putByteArray(TransportConstants.BYTES_TO_SEND_EXTRA_NAME, bytes); //Do we just change this to the args and objs
				bundle.putInt(TransportConstants.BYTES_TO_SEND_EXTRA_OFFSET, offset);
				bundle.putInt(TransportConstants.BYTES_TO_SEND_EXTRA_COUNT, count);
				bundle.putInt(TransportConstants.BYTES_TO_SEND_FLAGS, TransportConstants.BYTES_TO_SEND_FLAG_NONE);
				message.setData(bundle);
				
				sendMessageToRouterService(message);
				return true;
			}else{ //Message is too big for IPC transaction 
				//Log.w(TAG, "Message too big for single IPC transaction. Breaking apart. Size - " +  bytes.length);
				ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
				int bytesRead = 0; 
				boolean firstPacket = true;
				while(stream.available()>0){
					Message message = Message.obtain(); //Do we need to always obtain new? or can we just swap bundles?
					message.what = TransportConstants.ROUTER_SEND_PACKET;
					Bundle bundle = new Bundle();
					byte[] buffer;
					
					if(stream.available()>=MAX_BINDER_SIZE){
						buffer = new byte[MAX_BINDER_SIZE];
						bytesRead = stream.read(buffer, 0, MAX_BINDER_SIZE);
					}else{
						buffer = new byte[stream.available()];
						bytesRead = stream.read(buffer, 0, stream.available());
					}
					
					bundle.putByteArray(TransportConstants.BYTES_TO_SEND_EXTRA_NAME, buffer); //Do we just change this to the args and objs
					bundle.putInt(TransportConstants.BYTES_TO_SEND_EXTRA_OFFSET, 0);
					bundle.putInt(TransportConstants.BYTES_TO_SEND_EXTRA_COUNT, bytesRead);
					//Determine which flag should be sent for this division of the packet
					if(firstPacket){
						bundle.putInt(TransportConstants.BYTES_TO_SEND_FLAGS, TransportConstants.BYTES_TO_SEND_FLAG_LARGE_PACKET_START);
						firstPacket = false;
					}else if(stream.available()<=0){
						bundle.putInt(TransportConstants.BYTES_TO_SEND_FLAGS, TransportConstants.BYTES_TO_SEND_FLAG_LARGE_PACKET_END);
					}else{
						bundle.putInt(TransportConstants.BYTES_TO_SEND_FLAGS, TransportConstants.BYTES_TO_SEND_FLAG_LARGE_PACKET_CONT);
					}
					
					bundle.putLong(TransportConstants.APP_ID_EXTRA, Long.valueOf(appId));
					message.setData(bundle);
					sendMessageToRouterService(message);
					
					
				}
				try {
					stream.close();
					return true;
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}
			
		}
		
		/**
		 * This registers this service with the router service
		 */
		private boolean registerWithRouterService(){ 
			if(getContext()==null){
				Log.e(TAG, "Context set to null, failing out");
				return false;
			}
			 
			if(routerServiceMessenger!=null){
				Log.w(TAG, "Already registered with router service");
				return false;
			}
			
			Intent intent = null;
			if(isRouterServiceRunning(getContext()) ){
				//Attempt to bind
				if(!sendBindingIntent()){
					Log.e(TAG, "Something went wrong while trying to bind with the router service.");
					return false;
				}
				return true;
				
			}
			else{ //There's no router service running, let's start one
				
				//Log.d(TAG,whereToReply + " registering with  router Service");
				intent = new Intent(SdlRouterService.REGISTER_WITH_ROUTER_ACTION);
				Log.w(TAG, "No instance of the Sdl router service to register with");
				//Log.d(TAG,whereToReply + " starting up and registering with  router Service");
				intent = new Intent(SdlRouterService.START_ROUTER_SERVICE_ACTION);
				//Add a reply to get back what
				intent.putExtra(TransportConstants.SEND_PACKET_TO_APP_LOCATION_EXTRA_NAME, whereToReply);
				intent.putExtra(TransportConstants.PACKAGE_NAME_STRING, getContext().getPackageName());
				intent.putExtra(TransportConstants.APP_ID_EXTRA, Long.valueOf(appId));
				currentContext.sendBroadcast(intent);
				return true;
			}		
		}
		
		private boolean sendBindingIntent(){
			if(this.routerPackage !=null && this.routerClassName !=null){
				Log.d(TAG, "Sending bind request to " + this.routerPackage + " - " + this.routerClassName);
				Intent bindingIntent = new Intent();
				bindingIntent.setClassName(this.routerPackage, this.routerClassName);//This sets an explicit intent
				bindingIntent.putExtra(TransportConstants.ROUTER_BIND_REQUEST_TYPE_EXTRA, TransportConstants.BIND_REQUEST_TYPE_CLIENT);
				return getContext().bindService(bindingIntent, routerConnection, Context.BIND_ABOVE_CLIENT);
			}else{
				return false;
			}
		}
		
		private void sendRegistrationMessage(){
			Message msg = Message.obtain();
			msg.what = TransportConstants.ROUTER_REGISTER_CLIENT;
			msg.replyTo = this.clientMessenger;
			Bundle bundle = new Bundle();
			bundle.putLong(TransportConstants.APP_ID_EXTRA, Long.valueOf(appId));
			msg.setData(bundle);
			sendMessageToRouterService(msg);
		}
		
		private void unregisterWithRouterService(){
			Log.i(TAG, "Attempting to unregister with Sdl Router Service");
			if(isBound && routerServiceMessenger!=null){
				Message msg = Message.obtain();
				msg.what = TransportConstants.ROUTER_UNREGISTER_CLIENT;
				msg.replyTo = this.clientMessenger; //Including this in case this app isn't actually registered with the router service
				Bundle bundle = new Bundle();
				bundle.putLong(TransportConstants.APP_ID_EXTRA, Long.valueOf(appId));
				msg.setData(bundle);
				this.sendMessageToRouterService(msg);
			}else{
				Log.w(TAG, "Unable to unregister, not bound to router service");
			}
			
			routerServiceMessenger = null;
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
		
		/**
		 * Use this method to let the router service know that you are requesting a new session from the head unit. 
		 */
		public void requestNewSession(){
			Message msg = Message.obtain();
			msg.what = TransportConstants.ROUTER_REQUEST_NEW_SESSION;
			msg.replyTo = this.clientMessenger; //Including this in case this app isn't actually registered with the router service
			Bundle bundle = new Bundle();
			bundle.putLong(TransportConstants.APP_ID_EXTRA, Long.valueOf(appId));
			msg.setData(bundle);
			this.sendMessageToRouterService(msg);
		}
		
		public void removeSession(long sessionId){
			Message msg = Message.obtain();
			msg.what = TransportConstants.ROUTER_REMOVE_SESSION;
			msg.replyTo = this.clientMessenger; //Including this in case this app isn't actually registered with the router service
			Bundle bundle = new Bundle();
			bundle.putLong(TransportConstants.APP_ID_EXTRA, Long.valueOf(appId));
			bundle.putLong(TransportConstants.SESSION_ID_EXTRA, sessionId);
			msg.setData(bundle);
			this.sendMessageToRouterService(msg);
		}
}
