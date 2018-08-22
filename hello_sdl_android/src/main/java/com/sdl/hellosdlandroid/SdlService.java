package com.sdl.hellosdlandroid;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.proxy.LockScreenManager;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.SdlProxyALM;
import com.smartdevicelink.proxy.callbacks.OnServiceEnded;
import com.smartdevicelink.proxy.callbacks.OnServiceNACKed;
import com.smartdevicelink.proxy.interfaces.IProxyListenerALM;
import com.smartdevicelink.proxy.rpc.AddCommand;
import com.smartdevicelink.proxy.rpc.AddCommandResponse;
import com.smartdevicelink.proxy.rpc.AddSubMenuResponse;
import com.smartdevicelink.proxy.rpc.AlertManeuverResponse;
import com.smartdevicelink.proxy.rpc.AlertResponse;
import com.smartdevicelink.proxy.rpc.ButtonPressResponse;
import com.smartdevicelink.proxy.rpc.ChangeRegistrationResponse;
import com.smartdevicelink.proxy.rpc.CreateInteractionChoiceSetResponse;
import com.smartdevicelink.proxy.rpc.DeleteCommandResponse;
import com.smartdevicelink.proxy.rpc.DeleteFileResponse;
import com.smartdevicelink.proxy.rpc.DeleteInteractionChoiceSetResponse;
import com.smartdevicelink.proxy.rpc.DeleteSubMenuResponse;
import com.smartdevicelink.proxy.rpc.DiagnosticMessageResponse;
import com.smartdevicelink.proxy.rpc.DialNumberResponse;
import com.smartdevicelink.proxy.rpc.EndAudioPassThruResponse;
import com.smartdevicelink.proxy.rpc.GenericResponse;
import com.smartdevicelink.proxy.rpc.GetDTCsResponse;
import com.smartdevicelink.proxy.rpc.GetInteriorVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.GetSystemCapabilityResponse;
import com.smartdevicelink.proxy.rpc.GetVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.GetWayPointsResponse;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.ListFiles;
import com.smartdevicelink.proxy.rpc.ListFilesResponse;
import com.smartdevicelink.proxy.rpc.MenuParams;
import com.smartdevicelink.proxy.rpc.OnAudioPassThru;
import com.smartdevicelink.proxy.rpc.OnButtonEvent;
import com.smartdevicelink.proxy.rpc.OnButtonPress;
import com.smartdevicelink.proxy.rpc.OnCommand;
import com.smartdevicelink.proxy.rpc.OnDriverDistraction;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.OnHashChange;
import com.smartdevicelink.proxy.rpc.OnInteriorVehicleData;
import com.smartdevicelink.proxy.rpc.OnKeyboardInput;
import com.smartdevicelink.proxy.rpc.OnLanguageChange;
import com.smartdevicelink.proxy.rpc.OnLockScreenStatus;
import com.smartdevicelink.proxy.rpc.OnPermissionsChange;
import com.smartdevicelink.proxy.rpc.OnRCStatus;
import com.smartdevicelink.proxy.rpc.OnStreamRPC;
import com.smartdevicelink.proxy.rpc.OnSystemRequest;
import com.smartdevicelink.proxy.rpc.OnTBTClientState;
import com.smartdevicelink.proxy.rpc.OnTouchEvent;
import com.smartdevicelink.proxy.rpc.OnVehicleData;
import com.smartdevicelink.proxy.rpc.OnWayPointChange;
import com.smartdevicelink.proxy.rpc.PerformAudioPassThruResponse;
import com.smartdevicelink.proxy.rpc.PerformInteractionResponse;
import com.smartdevicelink.proxy.rpc.PutFile;
import com.smartdevicelink.proxy.rpc.PutFileResponse;
import com.smartdevicelink.proxy.rpc.ReadDIDResponse;
import com.smartdevicelink.proxy.rpc.ResetGlobalPropertiesResponse;
import com.smartdevicelink.proxy.rpc.ScrollableMessageResponse;
import com.smartdevicelink.proxy.rpc.SendHapticDataResponse;
import com.smartdevicelink.proxy.rpc.SendLocationResponse;
import com.smartdevicelink.proxy.rpc.SetAppIconResponse;
import com.smartdevicelink.proxy.rpc.SetDisplayLayoutResponse;
import com.smartdevicelink.proxy.rpc.SetGlobalPropertiesResponse;
import com.smartdevicelink.proxy.rpc.SetInteriorVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.SetMediaClockTimerResponse;
import com.smartdevicelink.proxy.rpc.ShowConstantTbtResponse;
import com.smartdevicelink.proxy.rpc.ShowResponse;
import com.smartdevicelink.proxy.rpc.SliderResponse;
import com.smartdevicelink.proxy.rpc.SpeakResponse;
import com.smartdevicelink.proxy.rpc.StreamRPCResponse;
import com.smartdevicelink.proxy.rpc.SubscribeButtonResponse;
import com.smartdevicelink.proxy.rpc.SubscribeVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.SubscribeWayPointsResponse;
import com.smartdevicelink.proxy.rpc.SystemRequestResponse;
import com.smartdevicelink.proxy.rpc.UnsubscribeButtonResponse;
import com.smartdevicelink.proxy.rpc.UnsubscribeVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.UnsubscribeWayPointsResponse;
import com.smartdevicelink.proxy.rpc.UpdateTurnListResponse;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.LockScreenStatus;
import com.smartdevicelink.proxy.rpc.enums.RequestType;
import com.smartdevicelink.proxy.rpc.enums.SdlDisconnectedReason;
import com.smartdevicelink.proxy.rpc.enums.TextAlignment;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;
import com.smartdevicelink.transport.BTTransportConfig;
import com.smartdevicelink.transport.BaseTransportConfig;
import com.smartdevicelink.transport.MultiplexTransportConfig;
import com.smartdevicelink.transport.TCPTransportConfig;
import com.smartdevicelink.transport.TransportConstants;
import com.smartdevicelink.transport.USBTransportConfig;
import com.smartdevicelink.util.CorrelationIdGenerator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SdlService extends Service implements IProxyListenerALM{

	private static final String TAG 					= "SDL Service";

	private static final String APP_NAME 				= "Hello Sdl";
	private static final String APP_ID 					= "8675309";
	
	private static final String ICON_FILENAME 			= "hello_sdl_icon.png";
	private static final String SDL_IMAGE_FILENAME  	= "sdl_full_image.png";
	private int iconCorrelationId;

	private List<String> remoteFiles;
	
	private static final String WELCOME_SHOW 			= "Welcome to HelloSDL";
	private static final String WELCOME_SPEAK 			= "Welcome to Hello S D L";
	
	private static final String TEST_COMMAND_NAME 		= "Test Command";
	private static final int TEST_COMMAND_ID 			= 1;

	private static final int FOREGROUND_SERVICE_ID = 111;

	// TCP/IP transport config
	// The default port is 12345
	// The IP is of the machine that is running SDL Core
	private static final int TCP_PORT = 12345;
	private static final String DEV_MACHINE_IP_ADDRESS = "192.168.1.78";

	// variable to create and call functions of the SyncProxy
	private SdlProxyALM proxy = null;

	private boolean firstNonHmiNone = true;
	@SuppressWarnings("unused")
	private boolean isVehicleDataSubscribed = false;

	private String lockScreenUrlFromCore = null;
	private final LockScreenManager lockScreenManager = new LockScreenManager();

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
        Log.d(TAG, "onCreate");
		super.onCreate();
		remoteFiles = new ArrayList<>();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			enterForeground();
		}
	}

	@SuppressLint("NewApi")
	public void enterForeground() {
		Notification notification = new Notification.Builder(this)
				.setContentTitle(getString(R.string.app_name))
				.setContentText("Connected through SDL")
				.setSmallIcon(R.drawable.ic_sdl)
				.setPriority(Notification.PRIORITY_DEFAULT)
				.build();
		startForeground(FOREGROUND_SERVICE_ID, notification);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//Check if this was started with a flag to force a transport connect
		boolean forced = intent !=null && intent.getBooleanExtra(TransportConstants.FORCE_TRANSPORT_CONNECTED, false);
        startProxy(forced, intent);

		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		disposeSyncProxy();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			stopForeground(true);
		}
		super.onDestroy();
	}

	private void startProxy(boolean forceConnect, Intent intent) {
        Log.i(TAG, "Trying to start proxy");
		if (proxy == null) {
			try {
                Log.i(TAG, "Starting SDL Proxy");
				BaseTransportConfig transport = null;
				if(BuildConfig.TRANSPORT.equals("MBT")){
					int securityLevel;
					if(BuildConfig.SECURITY.equals("HIGH")){
						securityLevel = MultiplexTransportConfig.FLAG_MULTI_SECURITY_HIGH;
					}else if(BuildConfig.SECURITY.equals("MED")){
						securityLevel = MultiplexTransportConfig.FLAG_MULTI_SECURITY_MED;
					}else if(BuildConfig.SECURITY.equals("LOW")){
						securityLevel = MultiplexTransportConfig.FLAG_MULTI_SECURITY_LOW;
					}else{
						securityLevel = MultiplexTransportConfig.FLAG_MULTI_SECURITY_OFF;
					}
					transport = new MultiplexTransportConfig(this, APP_ID, securityLevel);
				}else if(BuildConfig.TRANSPORT.equals("LBT")){
					transport = new BTTransportConfig();
				}else if(BuildConfig.TRANSPORT.equals("TCP")){
					transport = new TCPTransportConfig(TCP_PORT, DEV_MACHINE_IP_ADDRESS, true);
				}else if(BuildConfig.TRANSPORT.equals("USB")) {
					if (intent != null && intent.hasExtra(UsbManager.EXTRA_ACCESSORY)) { //If we want to support USB transport
						if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.HONEYCOMB) {
							Log.e(TAG, "Unable to start proxy. Android OS version is too low");
							return;
						}else {
							//We have a usb transport
							transport = new USBTransportConfig(getBaseContext(), (UsbAccessory) intent.getParcelableExtra(UsbManager.EXTRA_ACCESSORY));
							Log.d(TAG, "USB created.");
						}
					}
				}
				if(transport != null) {
					proxy = new SdlProxyALM(this, APP_NAME, true, APP_ID, transport);
				}
			} catch (SdlException e) {
				e.printStackTrace();
				// error creating proxy, returned proxy = null
				if (proxy == null) {
					stopSelf();
				}
			}
		}else if(forceConnect){
			proxy.forceOnConnected();
		}
	}

	private void disposeSyncProxy() {
		LockScreenActivity.updateLockScreenStatus(LockScreenStatus.OFF);

		if (proxy != null) {
			try {
				proxy.dispose();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				proxy = null;
			}
		}
		this.firstNonHmiNone = true;
		this.isVehicleDataSubscribed = false;
		
	}

	/**
	 * Will show a sample test message on screen as well as speak a sample test message
	 */
	private void showTest(){
		try {
			proxy.show(TEST_COMMAND_NAME, "Command has been selected", TextAlignment.CENTERED, CorrelationIdGenerator.generateId());
			proxy.speak(TEST_COMMAND_NAME, CorrelationIdGenerator.generateId());
		} catch (SdlException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *  Add commands for the app on SDL.
	 */
	private void sendCommands(){
		AddCommand command = new AddCommand();
		MenuParams params = new MenuParams();
		params.setMenuName(TEST_COMMAND_NAME);
		command.setCmdID(TEST_COMMAND_ID);
		command.setMenuParams(params);
		command.setVrCommands(Collections.singletonList(TEST_COMMAND_NAME));
		sendRpcRequest(command);
	}

	/**
	 * Sends an RPC Request to the connected head unit. Automatically adds a correlation id.
	 * @param request the rpc request that is to be sent to the module
	 */
	private void sendRpcRequest(RPCRequest request){
		try {
			proxy.sendRPCRequest(request);
		} catch (SdlException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Sends the app icon through the uploadImage method with correct params
	 */
	private void sendIcon(){
		iconCorrelationId = CorrelationIdGenerator.generateId();
		uploadImage(R.mipmap.ic_launcher, ICON_FILENAME, iconCorrelationId, true);
	}
	
	/**
	 * This method will help upload an image to the head unit
	 * @param resource the R.drawable.__ value of the image you wish to send
	 * @param imageName the filename that will be used to reference this image
	 * @param correlationId the correlation id to be used with this request. Helpful for monitoring putfileresponses
	 * @param isPersistent tell the system if the file should stay or be cleared out after connection.
	 */
	@SuppressWarnings("SameParameterValue")
	private void uploadImage(int resource, String imageName, int correlationId, boolean isPersistent){
		PutFile putFile = new PutFile();
		putFile.setFileType(FileType.GRAPHIC_PNG);
		putFile.setSdlFileName(imageName);
		putFile.setCorrelationID(correlationId);
		putFile.setPersistentFile(isPersistent);
		putFile.setSystemFile(false);
		putFile.setBulkData(contentsOfResource(resource));

		try {
			proxy.sendRPCRequest(putFile);
		} catch (SdlException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Helper method to take resource files and turn them into byte arrays
	 * @param resource Resource file id.
	 * @return Resulting byte array.
	 */
	private byte[] contentsOfResource(int resource) {
		InputStream is = null;
		try {
			is = getResources().openRawResource(resource);
			ByteArrayOutputStream os = new ByteArrayOutputStream(is.available());
			final int bufferSize = 4096;
			final byte[] buffer = new byte[bufferSize];
			int available;
			while ((available = is.read(buffer)) >= 0) {
				os.write(buffer, 0, available);
			}
			return os.toByteArray();
		} catch (IOException e) {
			Log.w(TAG, "Can't read icon file", e);
			return null;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void onProxyClosed(String info, Exception e, SdlDisconnectedReason reason) {
		stopSelf();
		if(reason.equals(SdlDisconnectedReason.LANGUAGE_CHANGE) && BuildConfig.TRANSPORT.equals("MBT")){
			Intent intent = new Intent(TransportConstants.START_ROUTER_SERVICE_ACTION);
			intent.putExtra(SdlReceiver.RECONNECT_LANG_CHANGE, true);
			sendBroadcast(intent);
		}
	}

	@Override
	public void onOnHMIStatus(OnHMIStatus notification) {
		if(notification.getHmiLevel().equals(HMILevel.HMI_FULL)){			
			if (notification.getFirstRun()) {
				// send welcome message if applicable
				performWelcomeMessage();
			}
			// Other HMI (Show, PerformInteraction, etc.) would go here
		}

		if(!notification.getHmiLevel().equals(HMILevel.HMI_NONE)
				&& firstNonHmiNone){
			sendCommands();
			//uploadImages();
			firstNonHmiNone = false;
			
			// Other app setup (SubMenu, CreateChoiceSet, etc.) would go here
		}else{
			//We have HMI_NONE
			if(notification.getFirstRun()){
				uploadImages();
			}
		}
	}

	/**
	 * Listener for handling when a lockscreen image is downloaded.
	 */
	private class LockScreenDownloadedListener implements LockScreenManager.OnLockScreenIconDownloadedListener{

		@Override
		public void onLockScreenIconDownloaded(Bitmap icon) {
			Log.i(TAG, "Lock screen icon downloaded successfully");
			LockScreenActivity.updateLockScreenImage(icon);
		}

		@Override
		public void onLockScreenIconDownloadError(Exception e) {
			Log.e(TAG, "Couldn't download lock screen icon, resorting to default.");
			LockScreenActivity.updateLockScreenImage(BitmapFactory.decodeResource(getResources(),
					R.drawable.sdl));
		}
	}
	
	/**
	 * Will show a sample welcome message on screen as well as speak a sample welcome message
	 */
	private void performWelcomeMessage(){
		try {
			Image image = new Image();
			image.setValue(SDL_IMAGE_FILENAME);
			image.setImageType(ImageType.DYNAMIC);

			//Set the welcome message on screen
			proxy.show(APP_NAME, WELCOME_SHOW, null, null, null, null, null, image, null, null, TextAlignment.CENTERED, CorrelationIdGenerator.generateId());
			
			//Say the welcome message
			proxy.speak(WELCOME_SPEAK, CorrelationIdGenerator.generateId());
			
		} catch (SdlException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 *  Requests list of images to SDL, and uploads images that are missing.
	 */
	private void uploadImages(){
		ListFiles listFiles = new ListFiles();
		listFiles.setOnRPCResponseListener(new OnRPCResponseListener() {
			@Override
			public void onResponse(int correlationId, RPCResponse response) {
				if(response.getSuccess()){
					remoteFiles = ((ListFilesResponse) response).getFilenames();
				}

				// Check the mutable set for the AppIcon
				// If not present, upload the image
				if(remoteFiles== null || !remoteFiles.contains(SdlService.ICON_FILENAME)){
					sendIcon();
				}else{
					// If the file is already present, send the SetAppIcon request
					try {
						proxy.setappicon(ICON_FILENAME, CorrelationIdGenerator.generateId());
					} catch (SdlException e) {
						e.printStackTrace();
					}
				}

				// Check the mutable set for the SDL image
				// If not present, upload the image
				if(remoteFiles== null || !remoteFiles.contains(SdlService.SDL_IMAGE_FILENAME)){
					uploadImage(R.drawable.sdl, SDL_IMAGE_FILENAME, CorrelationIdGenerator.generateId(), true);
				}
			}
		});
		this.sendRpcRequest(listFiles);
	}

	@Override
	public void onListFilesResponse(ListFilesResponse response) {
		Log.i(TAG, "onListFilesResponse from SDL ");
	}

	@Override
	public void onPutFileResponse(PutFileResponse response) {
		Log.i(TAG, "onPutFileResponse from SDL");
		if(response.getCorrelationID() == iconCorrelationId){ //If we have successfully uploaded our icon, we want to set it
			try {
				proxy.setappicon(ICON_FILENAME, CorrelationIdGenerator.generateId());
			} catch (SdlException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void onOnLockScreenNotification(OnLockScreenStatus notification) {
		LockScreenActivity.updateLockScreenStatus(notification.getShowLockScreen());
	}

	@Override
	public void onOnCommand(OnCommand notification){
		Integer id = notification.getCmdID();
		if(id != null){
			switch(id){
				case TEST_COMMAND_ID:
					showTest();
					break;
			}
		}
	}

	/**
	 *  Callback method that runs when the add command response is received from SDL.
	 */
	@Override
	public void onAddCommandResponse(AddCommandResponse response) {
		Log.i(TAG, "AddCommand response from SDL: " + response.getResultCode().name());

	}

	/*  Vehicle Data   */
	@Override
	public void onOnPermissionsChange(OnPermissionsChange notification) {
		Log.i(TAG, "Permision changed: " + notification);

		/* Uncomment to subscribe to vehicle data
		List<PermissionItem> permissions = notification.getPermissionItem();
		for(PermissionItem permission:permissions){
			if(permission.getRpcName().equalsIgnoreCase(FunctionID.SUBSCRIBE_VEHICLE_DATA.name())){
				if(permission.getHMIPermissions().getAllowed()!=null && permission.getHMIPermissions().getAllowed().size()>0){
					if(!isVehicleDataSubscribed){ //If we haven't already subscribed we will subscribe now
						//TODO: Add the vehicle data items you want to subscribe to
						//proxy.subscribevehicledata(gps, speed, rpm, fuelLevel, fuelLevel_State, instantFuelConsumption, externalTemperature, prndl, tirePressure, odometer, beltStatus, bodyInformation, deviceStatus, driverBraking, correlationID);
						proxy.subscribevehicledata(false, true, rpm, false, false, false, false, false, false, false, false, false, false, false, autoIncCorrId++);
					}
				}
			}
		}
		*/
	}

	/**
	 * Rest of the SDL callbacks from the head unit
	 */

	@Override
	public void onSubscribeVehicleDataResponse(SubscribeVehicleDataResponse response) {
		if(response.getSuccess()){
			Log.i(TAG, "Subscribed to vehicle data");
			this.isVehicleDataSubscribed = true;
		}
	}
	
	@Override
	public void onOnVehicleData(OnVehicleData notification) {
		Log.i(TAG, "Vehicle data notification from SDL");
		//TODO Put your vehicle data code here
		//ie, notification.getSpeed().
	}
	
	@Override
	public void onAddSubMenuResponse(AddSubMenuResponse response) {
        Log.i(TAG, "AddSubMenu response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
	}

	@Override
	public void onCreateInteractionChoiceSetResponse(CreateInteractionChoiceSetResponse response) {
        Log.i(TAG, "CreateInteractionChoiceSet response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
	}

	@Override
	public void onAlertResponse(AlertResponse response) {
        Log.i(TAG, "Alert response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
	}

	@Override
	public void onDeleteCommandResponse(DeleteCommandResponse response) {
        Log.i(TAG, "DeleteCommand response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
	}

	@Override
	public void onDeleteInteractionChoiceSetResponse(DeleteInteractionChoiceSetResponse response) {
        Log.i(TAG, "DeleteInteractionChoiceSet response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
	}

	@Override
	public void onDeleteSubMenuResponse(DeleteSubMenuResponse response) {
        Log.i(TAG, "DeleteSubMenu response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
	}

	@Override
	public void onPerformInteractionResponse(PerformInteractionResponse response) {
        Log.i(TAG, "PerformInteraction response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
	}

	@Override
	public void onResetGlobalPropertiesResponse(
			ResetGlobalPropertiesResponse response) {
        Log.i(TAG, "ResetGlobalProperties response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
	}

	@Override
	public void onSetGlobalPropertiesResponse(SetGlobalPropertiesResponse response) {
        Log.i(TAG, "SetGlobalProperties response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
	}

	@Override
	public void onSetMediaClockTimerResponse(SetMediaClockTimerResponse response) {
        Log.i(TAG, "SetMediaClockTimer response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
	}

	@Override
	public void onShowResponse(ShowResponse response) {
        Log.i(TAG, "Show response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
	}

	@Override
	public void onSpeakResponse(SpeakResponse response) {
        Log.i(TAG, "SpeakCommand response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
	}

	@Override
	public void onOnButtonEvent(OnButtonEvent notification) {
        Log.i(TAG, "OnButtonEvent notification from SDL: " + notification);
	}

	@Override
	public void onOnButtonPress(OnButtonPress notification) {
        Log.i(TAG, "OnButtonPress notification from SDL: " + notification);
	}

	@Override
	public void onSubscribeButtonResponse(SubscribeButtonResponse response) {
        Log.i(TAG, "SubscribeButton response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
	}

	@Override
	public void onUnsubscribeButtonResponse(UnsubscribeButtonResponse response) {
        Log.i(TAG, "UnsubscribeButton response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
	}

	@Override
	public void onOnTBTClientState(OnTBTClientState notification) {
        Log.i(TAG, "OnTBTClientState notification from SDL: " + notification);
	}

	@Override
	public void onUnsubscribeVehicleDataResponse(
			UnsubscribeVehicleDataResponse response) {
        Log.i(TAG, "UnsubscribeVehicleData response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
	}

	@Override
	public void onGetVehicleDataResponse(GetVehicleDataResponse response) {
        Log.i(TAG, "GetVehicleData response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
	}

	@Override
	public void onReadDIDResponse(ReadDIDResponse response) {
        Log.i(TAG, "ReadDID response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
	}

	@Override
	public void onGetDTCsResponse(GetDTCsResponse response) {
        Log.i(TAG, "GetDTCs response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
	}

	@Override
	public void onPerformAudioPassThruResponse(PerformAudioPassThruResponse response) {
        Log.i(TAG, "PerformAudioPassThru response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
	}

	@Override
	public void onEndAudioPassThruResponse(EndAudioPassThruResponse response) {
        Log.i(TAG, "EndAudioPassThru response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
	}

	@Override
	public void onOnAudioPassThru(OnAudioPassThru notification) {
		Log.i(TAG, "OnAudioPassThru notification from SDL: " + notification );
	}

	@Override
	public void onDeleteFileResponse(DeleteFileResponse response) {
        Log.i(TAG, "DeleteFile response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
	}

	@Override
	public void onSetAppIconResponse(SetAppIconResponse response) {
        Log.i(TAG, "SetAppIcon response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
	}

	@Override
	public void onScrollableMessageResponse(ScrollableMessageResponse response) {
        Log.i(TAG, "ScrollableMessage response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
	}

	@Override
	public void onChangeRegistrationResponse(ChangeRegistrationResponse response) {
        Log.i(TAG, "ChangeRegistration response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
	}

	@Override
	public void onSetDisplayLayoutResponse(SetDisplayLayoutResponse response) {
        Log.i(TAG, "SetDisplayLayout response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
	}

	@Override
	public void onOnLanguageChange(OnLanguageChange notification) {
        Log.i(TAG, "OnLanguageChange notification from SDL: " + notification);
	}

	@Override
	public void onSliderResponse(SliderResponse response) {
        Log.i(TAG, "Slider response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
	}

	@Override
	public void onOnHashChange(OnHashChange notification) {
        Log.i(TAG, "OnHashChange notification from SDL: " + notification);
	}

	@Override
	public void onOnSystemRequest(OnSystemRequest notification) {
        Log.i(TAG, "OnSystemRequest notification from SDL: " + notification);

		// Download the lockscreen icon Core desires
		if(notification.getRequestType().equals(RequestType.LOCK_SCREEN_ICON_URL) && lockScreenUrlFromCore == null){
			lockScreenUrlFromCore = notification.getUrl();
			if(lockScreenUrlFromCore != null && lockScreenManager.getLockScreenIcon() == null){
				lockScreenManager.downloadLockScreenIcon(lockScreenUrlFromCore, new LockScreenDownloadedListener());
			}
		}
	}

	@Override
	public void onSystemRequestResponse(SystemRequestResponse response) {
        Log.i(TAG, "SystemRequest response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
	}

	@Override
	public void onOnKeyboardInput(OnKeyboardInput notification) {
        Log.i(TAG, "OnKeyboardInput notification from SDL: " + notification);
	}

	@Override
	public void onOnTouchEvent(OnTouchEvent notification) {
        Log.i(TAG, "OnTouchEvent notification from SDL: " + notification);
	}

	@Override
	public void onDiagnosticMessageResponse(DiagnosticMessageResponse response) {
        Log.i(TAG, "DiagnosticMessage response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
	}

	@Override
	public void onOnStreamRPC(OnStreamRPC notification) {
        Log.i(TAG, "OnStreamRPC notification from SDL: " + notification);
	}

	@Override
	public void onStreamRPCResponse(StreamRPCResponse response) {
        Log.i(TAG, "StreamRPC response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
	}

	@Override
	public void onDialNumberResponse(DialNumberResponse response) {
        Log.i(TAG, "DialNumber response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
	}

	@Override
	public void onSendLocationResponse(SendLocationResponse response) {
        Log.i(TAG, "SendLocation response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
	}

	@Override
	public void onServiceEnded(OnServiceEnded serviceEnded) {

	}

	@Override
	public void onServiceNACKed(OnServiceNACKed serviceNACKed) {

	}

	@Override
	public void onShowConstantTbtResponse(ShowConstantTbtResponse response) {
        Log.i(TAG, "ShowConstantTbt response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
	}

	@Override
	public void onAlertManeuverResponse(AlertManeuverResponse response) {
        Log.i(TAG, "AlertManeuver response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
	}

	@Override
	public void onUpdateTurnListResponse(UpdateTurnListResponse response) {
        Log.i(TAG, "UpdateTurnList response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
	}

	@Override
	public void onServiceDataACK(int dataSize) {

	}

	@Override
	public void onGetWayPointsResponse(GetWayPointsResponse response) {
		Log.i(TAG, "GetWayPoints response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
	}

	@Override
	public void onSubscribeWayPointsResponse(SubscribeWayPointsResponse response) {
		Log.i(TAG, "SubscribeWayPoints response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
	}

	@Override
	public void onUnsubscribeWayPointsResponse(UnsubscribeWayPointsResponse response) {
		Log.i(TAG, "UnsubscribeWayPoints response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
	}

	@Override
	public void onOnWayPointChange(OnWayPointChange notification) {
		Log.i(TAG, "OnWayPointChange notification from SDL: " + notification);
	}

	@Override
	public void onOnDriverDistraction(OnDriverDistraction notification) {
		// Some RPCs (depending on region) cannot be sent when driver distraction is active.
	}

	@Override
	public void onError(String info, Exception e) {
	}

	@Override
	public void onGenericResponse(GenericResponse response) {
        Log.i(TAG, "Generic response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
	}

	@Override
	public void onGetSystemCapabilityResponse(GetSystemCapabilityResponse response) {
		Log.i(TAG, "GetSystemCapabilityResponse from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
	}

	@Override
	public void onSendHapticDataResponse(SendHapticDataResponse response){
		Log.i(TAG, "SendHapticData response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
	}

	@Override
	public void onOnRCStatus(OnRCStatus notification) {
		Log.i(TAG, "OnRCStatus notification from SDL: " + notification);

	}

	@Override
	public void onButtonPressResponse(ButtonPressResponse response) {
		Log.i(TAG, "ButtonPress response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
	}

	@Override
	public void onSetInteriorVehicleDataResponse(SetInteriorVehicleDataResponse response) {
		Log.i(TAG, "SetInteriorVehicleData response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
	}

	@Override
	public void onGetInteriorVehicleDataResponse(GetInteriorVehicleDataResponse response) {
		Log.i(TAG, "GetInteriorVehicleData response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
	}

	@Override
	public void onOnInteriorVehicleData(OnInteriorVehicleData notification) {
		Log.i(TAG, "OnInteriorVehicleData from SDL: " + notification);

	}

}
