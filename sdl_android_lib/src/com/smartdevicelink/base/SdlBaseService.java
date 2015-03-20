package com.smartdevicelink.base;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.proxy.SdlProxyALM;
import com.smartdevicelink.proxy.interfaces.IProxyListenerALM;
import com.smartdevicelink.proxy.rpc.AddCommandResponse;
import com.smartdevicelink.proxy.rpc.AddSubMenuResponse;
import com.smartdevicelink.proxy.rpc.AlertResponse;
import com.smartdevicelink.proxy.rpc.ChangeRegistrationResponse;
import com.smartdevicelink.proxy.rpc.CreateInteractionChoiceSetResponse;
import com.smartdevicelink.proxy.rpc.DeleteCommandResponse;
import com.smartdevicelink.proxy.rpc.DeleteFileResponse;
import com.smartdevicelink.proxy.rpc.DeleteInteractionChoiceSetResponse;
import com.smartdevicelink.proxy.rpc.DeleteSubMenuResponse;
import com.smartdevicelink.proxy.rpc.DiagnosticMessageResponse;
import com.smartdevicelink.proxy.rpc.EndAudioPassThruResponse;
import com.smartdevicelink.proxy.rpc.GenericResponse;
import com.smartdevicelink.proxy.rpc.GetDTCsResponse;
import com.smartdevicelink.proxy.rpc.GetVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.ListFilesResponse;
import com.smartdevicelink.proxy.rpc.OnAudioPassThru;
import com.smartdevicelink.proxy.rpc.OnButtonEvent;
import com.smartdevicelink.proxy.rpc.OnButtonPress;
import com.smartdevicelink.proxy.rpc.OnCommand;
import com.smartdevicelink.proxy.rpc.OnDriverDistraction;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.OnHashChange;
import com.smartdevicelink.proxy.rpc.OnKeyboardInput;
import com.smartdevicelink.proxy.rpc.OnLanguageChange;
import com.smartdevicelink.proxy.rpc.OnLockScreenStatus;
import com.smartdevicelink.proxy.rpc.OnPermissionsChange;
import com.smartdevicelink.proxy.rpc.OnSystemRequest;
import com.smartdevicelink.proxy.rpc.OnTBTClientState;
import com.smartdevicelink.proxy.rpc.OnTouchEvent;
import com.smartdevicelink.proxy.rpc.OnVehicleData;
import com.smartdevicelink.proxy.rpc.PerformAudioPassThruResponse;
import com.smartdevicelink.proxy.rpc.PerformInteractionResponse;
import com.smartdevicelink.proxy.rpc.PutFile;
import com.smartdevicelink.proxy.rpc.PutFileResponse;
import com.smartdevicelink.proxy.rpc.ReadDIDResponse;
import com.smartdevicelink.proxy.rpc.ResetGlobalPropertiesResponse;
import com.smartdevicelink.proxy.rpc.ScrollableMessageResponse;
import com.smartdevicelink.proxy.rpc.SetAppIconResponse;
import com.smartdevicelink.proxy.rpc.SetDisplayLayoutResponse;
import com.smartdevicelink.proxy.rpc.SetGlobalPropertiesResponse;
import com.smartdevicelink.proxy.rpc.SetMediaClockTimerResponse;
import com.smartdevicelink.proxy.rpc.ShowResponse;
import com.smartdevicelink.proxy.rpc.SliderResponse;
import com.smartdevicelink.proxy.rpc.SpeakResponse;
import com.smartdevicelink.proxy.rpc.SubscribeButtonResponse;
import com.smartdevicelink.proxy.rpc.SubscribeVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.SystemRequestResponse;
import com.smartdevicelink.proxy.rpc.UnsubscribeButtonResponse;
import com.smartdevicelink.proxy.rpc.UnsubscribeVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.SdlDisconnectedReason;
import com.smartdevicelink.proxy.rpc.enums.TextAlignment;
import com.smartdevicelink.transport.MultiplexTransportConfig;
import com.smartdevicelink.transport.SdlBroadcastReceiver;

public class SdlBaseService extends Service implements IProxyListenerALM{

	private static final String TAG = "SdlService";
	private final String APP_NAME ;// = "Hello Car";					//TODO enter your own app name here
	private final String APP_ID;// = "7331";						//TODO enter your own app id here
	private static final String ICON_SYNC_FILENAME = "icon.png";
	private static final String ICON_FILENAME_SUFFIX = ".png";
	
	
	// variable to create and call functions of the SdlProxy
	private SdlProxyALM proxy = null;

	// variable used to increment correlation ID for every request sent to a SDL system
	public int autoIncCorrId = 0;
	
	/* ***********************************************************************************************************************************************************************
	 * ********************************************************************* Lifecycle Calls *********************************************************************************
	 *************************************************************************************************************************************************************************/
	/**
	 * This should be ok because it's a no param constructor. However we need to test to make sure this works.
	 */
	public SdlBaseService(){
		super();
		APP_NAME = getAppName();
		APP_ID = getAppId();
		
	}
		
		
	@Override
	public IBinder onBind(Intent intent) {
		isInitProperly();
		return null;
	}

	
	@Override
	public void onCreate() {
		super.onCreate();
		isInitProperly();
	}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
        	startProxy();
    		if(intent.hasExtra(SdlBroadcastReceiver.FORCE_TRANSPORT_CONNECTED)){
    			proxy.forceOnConnected();
			}
		}
			
        return START_STICKY;
	}
	
	@Override
	public void onDestroy() {
		disposeSyncProxy();
		super.onDestroy();
	}
	
	
	/**
	 * This method is to make developers fail fast so they know they need to set certain info before
	 * they can start their SDL Service.
	 */
	private void isInitProperly() {
		if(APP_NAME == null || APP_ID==null){
			throw new IllegalStateException("Sdl Service was not inited properly. Please make sure you have set app name and id");
		}
	}
	
	
	/* ***********************************************************************************************************************************************************************
	 * *******************************************************  Methods for SdlProxy Management *******************************************************************************
	 *************************************************************************************************************************************************************************/
	
	public void startProxy() {
		if (proxy == null) {
			try {
				proxy = new SdlProxyALM(this, APP_NAME, 
						true, APP_ID, 
						new MultiplexTransportConfig(getBaseContext(), APP_ID));
				
			} catch (SdlException e) {
				e.printStackTrace();
				// error creating proxy, returned proxy = null
				if (proxy == null) {
					stopSelf();
				}
			}
		}
	}
	
	public void disposeSyncProxy() {
		if (proxy != null) {
			try {
				proxy.dispose();
			} catch (SdlException e) {
				e.printStackTrace();
			}
			proxy = null;
		}
	}
	
	/**
	 * This will send the app icon over to the SDL system
	 * @throws SdlException
	 */
	private void sendIcon() throws SdlException {
		int appIconResource = this.getAppIconResource();
		if(appIconResource == -1){
			Log.e(TAG, "Unable to send app icon because no resource was provided");
			return;
		}
		PutFile putFile = new PutFile();
		putFile.setFileType(FileType.GRAPHIC_PNG);
		putFile.setSdlFileName(ICON_SYNC_FILENAME);
		putFile.setCorrelationID(autoIncCorrId++);
		putFile.setBulkData(contentsOfResource(appIconResource));
		
		proxy.sendRPCRequest(putFile);
	}

	/**
	 * This is a convience method that will grab all the binary data from a resource
	 * to be able to be sent to the SDL sytem
	 * @param resource
	 * @return
	 */
	private byte[] contentsOfResource(int resource) {
		InputStream is = null;
		try {
			is = getResources().openRawResource(resource);	//FIXME will throw an exception
			ByteArrayOutputStream os = new ByteArrayOutputStream(is.available());
			final int buffersize = 4096;
			final byte[] buffer = new byte[buffersize];
			int available = 0;
			while ((available = is.read(buffer)) >= 0) {
				os.write(buffer, 0, available);
			}
			return os.toByteArray();
		} catch (IOException e) {
			Log.w("SDL Service", "Can't read icon file", e);
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
	
	/* ***********************************************************************************************************************************************************************
	 * *******************************************************  Methods for IProxyListenerALM *******************************************************************************
	 *************************************************************************************************************************************************************************/

	@Override
	public void onOnHMIStatus(OnHMIStatus notification) {

	switch(notification.getHmiLevel()) {
		case HMI_FULL:
			Log.i(TAG, "HMI_FULL");
			//When we get this notification it means we have main access to the SDL System
			/*Intent start = new Intent(this,MainActivity.class);
			start.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP + Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(start);
			 */ //FIXME creat a callback for develoeprs to let them know this is when they should bring their app to the foreground
			if (notification.getFirstRun()) {
				// send welcome message if applicable
				try {
					proxy.show(APP_NAME + " this is the first", "show command", TextAlignment.CENTERED, autoIncCorrId++);
				}catch (SdlException e) {
					e.printStackTrace();
				}		
				
				// TODO this is where the developer should send addcommands and subsribe to buttons
			}else { //If this isn't our first time receiving HMI_FULL
				try {
					proxy.show("SdlProxy is", "Alive", TextAlignment.CENTERED, autoIncCorrId++);
				} catch (SdlException e) {
					e.printStackTrace();
				}
			}
			break;
		case HMI_LIMITED:
			Log.i(TAG, "HMI_LIMITED");
			break;
		case HMI_BACKGROUND:
			Log.i(TAG, "HMI_BACKGROUND");
			break;
		case HMI_NONE:
			Log.i(TAG, "HMI_NONE");
			//Since the first HMI status sent to an app is HMI_NONE we take this as we have just established a connection and we want to send our icon
			if(notification.getFirstRun()){
				try {
					sendIcon();
				} catch (SdlException e) {
					e.printStackTrace();
				}
			}
			break;
		default:
			return;
	}
		
	}


	@Override
	public void onProxyClosed(String info, Exception e, SdlDisconnectedReason reason) {
		//After we are asked to close the proxy we should shut down our service
		stopSelf();	
	}

	@Override
	public void onError(String info, Exception e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGenericResponse(GenericResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOnCommand(OnCommand notification) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAddCommandResponse(AddCommandResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAddSubMenuResponse(AddSubMenuResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCreateInteractionChoiceSetResponse(
			CreateInteractionChoiceSetResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAlertResponse(AlertResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeleteCommandResponse(DeleteCommandResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeleteInteractionChoiceSetResponse(
			DeleteInteractionChoiceSetResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeleteSubMenuResponse(DeleteSubMenuResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPerformInteractionResponse(PerformInteractionResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onResetGlobalPropertiesResponse(
			ResetGlobalPropertiesResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSetGlobalPropertiesResponse(
			SetGlobalPropertiesResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSetMediaClockTimerResponse(SetMediaClockTimerResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onShowResponse(ShowResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSpeakResponse(SpeakResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOnButtonEvent(OnButtonEvent notification) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOnButtonPress(OnButtonPress notification) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSubscribeButtonResponse(SubscribeButtonResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnsubscribeButtonResponse(UnsubscribeButtonResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOnPermissionsChange(OnPermissionsChange notification) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSubscribeVehicleDataResponse(
			SubscribeVehicleDataResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnsubscribeVehicleDataResponse(
			UnsubscribeVehicleDataResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetVehicleDataResponse(GetVehicleDataResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOnVehicleData(OnVehicleData notification) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPerformAudioPassThruResponse(
			PerformAudioPassThruResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEndAudioPassThruResponse(EndAudioPassThruResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOnAudioPassThru(OnAudioPassThru notification) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPutFileResponse(PutFileResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeleteFileResponse(DeleteFileResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onListFilesResponse(ListFilesResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSetAppIconResponse(SetAppIconResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScrollableMessageResponse(ScrollableMessageResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onChangeRegistrationResponse(ChangeRegistrationResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSetDisplayLayoutResponse(SetDisplayLayoutResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOnLanguageChange(OnLanguageChange notification) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOnHashChange(OnHashChange notification) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSliderResponse(SliderResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOnDriverDistraction(OnDriverDistraction notification) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOnTBTClientState(OnTBTClientState notification) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOnSystemRequest(OnSystemRequest notification) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSystemRequestResponse(SystemRequestResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOnKeyboardInput(OnKeyboardInput notification) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOnTouchEvent(OnTouchEvent notification) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDiagnosticMessageResponse(DiagnosticMessageResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReadDIDResponse(ReadDIDResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetDTCsResponse(GetDTCsResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOnLockScreenNotification(OnLockScreenStatus notification) {
		// TODO Auto-generated method stub
		
	}
	
	/* ***********************************************************************************************************************************************************************
	 * *************************************************************** Convince Methods for Developers ***********************************************************************
	 *************************************************************************************************************************************************************************/
	
	
	
	/* ***********************************************************************************************************************************************************************
	 * ************************************************* Abstract Methods for developers to overwrite ***********************************************************************
	 *************************************************************************************************************************************************************************/
	//FIXME actually make this abstract once we make the class abstract
	public int getAppIconResource(){
		return -1;
	}
	
	public static String getAppName(){
		return null;
	}
	
	public static String getAppId(){
		return null;
	}
	
	

}
