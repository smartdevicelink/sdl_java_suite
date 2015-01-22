package com.smartdevicelink.transport;

import static com.c4.android.transport.TransportConstants.PACKET_TO_SEND_EXTRA_NAME;

import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Parcelable;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.c4.android.datatypes.RegisteredApp;
import com.c4.android.datatypes.TransportEnums.TransportType;
import com.c4.android.transport.IPacketStateMachine;
import com.c4.android.transport.RouterService;
import com.c4.android.transport.TransportConstants;
import com.c4.android.utl.HandyDandy;
import com.smartdevicelink.protocol.SdlPacket;

public abstract class SdlRouterService extends RouterService{

	public static final String START_ROUTER_SERVICE_ACTION 					= "sdl.router"+ TransportConstants.START_ROUTER_SERVICE_ACTION_SUFFIX;
	public static final String REGISTER_NEWER_SERVER_INSTANCE_ACTION		= "com.sdl.android.newservice";
	public static final String START_SERVICE_ACTION							= "sdl.router.startservice";
	public static final String REGISTER_WITH_ROUTER_ACTION 					= "com.sdl.android.register"; 
	
	public static final String REQUEST_EXTRA_SESSION_FOR_APPID_ACTION 	    = "com.sdl.android.session.addition"; 
	public static final String EXTRA_SESSION_APPID_EXTRA 	    			= "appid"; 
	//public static final String EXTRA_SESSION_APPID_EXTRA 	    = "com.sdl.android.session.addition"; 
	
	protected static final int ROUTER_SERVICE_VERSION_NUMBER = 4;
	
	private static final String TAG = "Sdl Router Service";
	private static final UUID SerialPortServiceClass_UUID = new UUID(0x936DA01F9ABD4D9DL, 0x80C702AF85C822A8L);
	private static final String SDP_NAME = "SyncProxy";
	
	
	/* ***********************************************************************************************************************************************************************
	 * *****************************************************************  PROTOCOL SPECIFIC SETUP  ***************************************************************************
	 *************************************************************************************************************************************************************************/
	
	@Override
	public UUID getSupportedBluetoothUUID() {
		return SerialPortServiceClass_UUID;
	}
	
	@Override
	public String getSDPRecordName() {
		return SDP_NAME;
	}

	@Override
	public String getSharedPrefLocation() {
		return "test.bluetoothprefs";
	}

	@Override
	public int getRouterServiceVersion(){
		return ROUTER_SERVICE_VERSION_NUMBER;
	}
	
	@Override
	public String getNewServiceCheckString() {
		return REGISTER_NEWER_SERVER_INSTANCE_ACTION;
	}
	
	@Override
	public String getRegisterWithRouterAction() {
		return REGISTER_WITH_ROUTER_ACTION;
	}

	@Override
	public String getStartServiceIntentAction() {
		return START_SERVICE_ACTION;
	}

	@Override
	public IPacketStateMachine getProtocolPsm() {
		return new SdlPsm();
	}
	
	
	/* ***********************************************************************************************************************************************************************
	 * *****************************************************************  CUSTOM ADDITIONS  ************************************************************************************
	 *************************************************************************************************************************************************************************/
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
								app.getExtraIds().add((long)-1); //Adding an extra session
							}
						}
						
					}
				}
			}
			
		}
	};
	
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
	
	/* ***********************************************************************************************************************************************************************
	 * *****************************************************************  EVENT TRIGGERS  ************************************************************************************
	 *************************************************************************************************************************************************************************/
	
	
	@Override
	public void onPacketRead(Parcelable incomming, int bytesRead) {
		Log.i(TAG, "******** Read packet with header: " +((SdlPacket)incomming).toString());
		super.onPacketRead(incomming, bytesRead);
	}
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		SESSION_LOCK = new Object();
		synchronized(SESSION_LOCK){
			sessionMap = new SparseArray<Long>();
		}
		IntentFilter filter = new IntentFilter(REQUEST_EXTRA_SESSION_FOR_APPID_ACTION);
		registerReceiver(sdlCustomReceiver,filter);
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(sdlCustomReceiver);
	}


	SparseArray<Long> sessionMap;
	private Object SESSION_LOCK;
	//private static final int SESSION_ID_BYTE = 3;
	@Override
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
	
	
	
	// Remove block comment when looking to debug what's being written to the transport
	@Override
	public boolean writeBytesToTransport(byte[] byteArray,int offset, int count) {
		debugPacket(byteArray);
		
		return super.writeBytesToTransport(byteArray,offset,count);
	}

	
	private void debugPacket(byte[] bytes){
		//DEBUG
		
		if(bytes[0] != 0x00){
			Log.d(TAG, "Writing packet with header: " + HandyDandy.bytesToHex(bytes, 12)); //just want the header
		}else{
			
			//Log.d(TAG, "Writing packet with binary header: " + HandyDandy.bytesToHex(bytes, 12)); //just want the header
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	}
	
	private Long getAppIDForSession(int sessionId){
		synchronized(SESSION_LOCK){
		//Log.d(TAG, "Looking for session: " + sessionId);
		Long appId = sessionMap.get(sessionId);
		if(appId==null){
			int pos;
			for (RegisteredApp app : registeredApps.values()) {
				pos = app.containsExtraId(-1); 
				if(pos != -1){
					app.setExtraId(pos,sessionId);
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

	@Override
	public void onTransportConnected(TransportType type) {
		//SiphonServer.init();
		super.onTransportConnected(type);
		Toast.makeText(getBaseContext(), "SDL "+ type.name()+ " Transport Connected", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onTransportDisconnected(TransportType type) {
		super.onTransportDisconnected(type);
		synchronized(SESSION_LOCK){
			sessionMap.clear();
			if(registeredApps==null){
				return;
			}
			for (RegisteredApp app : registeredApps.values()) {
				app.clearExtraIds();
				app.getExtraIds().add((long)-1); //Since we should be expecting at least one session. God i hope this works.
			}
		}
		Toast.makeText(getBaseContext(), "SDL "+ type.name()+ " Transport disconnected", Toast.LENGTH_SHORT).show();
	}
	
	
	
}
