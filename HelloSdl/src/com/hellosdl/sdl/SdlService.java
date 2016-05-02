package com.hellosdl.sdl;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.hellosdl.MainActivity;
import com.hellosdl.R;
import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.proxy.SdlProxyALM;
import com.smartdevicelink.proxy.callbacks.OnServiceEnded;
import com.smartdevicelink.proxy.callbacks.OnServiceNACKed;
import com.smartdevicelink.proxy.interfaces.IProxyListenerALM;
import com.smartdevicelink.proxy.rpc.AddCommandResponse;
import com.smartdevicelink.proxy.rpc.AddSubMenuResponse;
import com.smartdevicelink.proxy.rpc.AlertManeuverResponse;
import com.smartdevicelink.proxy.rpc.AlertResponse;
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
import com.smartdevicelink.proxy.rpc.OnStreamRPC;
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
import com.smartdevicelink.proxy.rpc.SendLocationResponse;
import com.smartdevicelink.proxy.rpc.SetAppIconResponse;
import com.smartdevicelink.proxy.rpc.SetDisplayLayoutResponse;
import com.smartdevicelink.proxy.rpc.SetGlobalPropertiesResponse;
import com.smartdevicelink.proxy.rpc.SetMediaClockTimerResponse;
import com.smartdevicelink.proxy.rpc.ShowConstantTbtResponse;
import com.smartdevicelink.proxy.rpc.ShowResponse;
import com.smartdevicelink.proxy.rpc.SliderResponse;
import com.smartdevicelink.proxy.rpc.SpeakResponse;
import com.smartdevicelink.proxy.rpc.StreamRPCResponse;
import com.smartdevicelink.proxy.rpc.SubscribeButtonResponse;
import com.smartdevicelink.proxy.rpc.SubscribeVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.SystemRequestResponse;
import com.smartdevicelink.proxy.rpc.UnsubscribeButtonResponse;
import com.smartdevicelink.proxy.rpc.UnsubscribeVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.UpdateTurnListResponse;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.SdlDisconnectedReason;
import com.smartdevicelink.proxy.rpc.enums.TextAlignment;
import com.smartdevicelink.transport.MultiplexTransportConfig;
import com.smartdevicelink.transport.SdlBroadcastReceiver;
import com.smartdevicelink.util.DebugTool;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * While this class is just an extension off the base Android Service class, we hope in the future we can offer something 
 * better that takes care of a lot of life cycle stuff.
 * <p>
 * For now this shows the most basic of operations to take when connecting via SDL
 * Here's what it covers
 * 1. Basic Sdl proxy life cycle
 * 2. Sending app icon
 * 3. When to launch activity and dealing with different HMI_STATUS's
 * 
 * 
 * @author Joey Grover
 *
 */
public class SdlService extends Service implements IProxyListenerALM{
	private static final String TAG = "SdlService";
	private static final String APP_NAME = "Hello Car";					//TODO enter your own app name here
	private static final String APP_ID = "7331";						//TODO enter your own app id here
	private static final String ICON_SYNC_FILENAME = "icon.png";
	private static final String ICON_FILENAME_SUFFIX = ".png";
	
	
	// variable to create and call functions of the SdlProxy
	private SdlProxyALM proxy = null;
	
	// variable used to increment correlation ID for every request sent to a SDL system
	public int autoIncCorrId = 0;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
        if(proxy == null){
        	startProxy();
        }else if (intent != null && intent.hasExtra(SdlBroadcastReceiver.FORCE_TRANSPORT_CONNECTED)){
        	proxy.forceOnConnected();
		}
        return START_STICKY;
	}
	
	@Override
	public void onDestroy() {
		disposeSyncProxy();
		super.onDestroy();
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
		PutFile putFile = new PutFile();
		putFile.setFileType(FileType.GRAPHIC_PNG);
		putFile.setSdlFileName(ICON_SYNC_FILENAME);
		putFile.setCorrelationID(autoIncCorrId++);
		putFile.setBulkData(contentsOfResource(R.drawable.ic_launcher));
		
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
			is = getResources().openRawResource(resource);
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
			Intent start = new Intent(this,MainActivity.class);
			start.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP + Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(start);

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
		
		
	}


	@Override
	public void onGenericResponse(GenericResponse response) {
		
		
	}


	@Override
	public void onOnCommand(OnCommand notification) {
		
		
	}


	@Override
	public void onAddCommandResponse(AddCommandResponse response) {
		
		
	}


	@Override
	public void onAddSubMenuResponse(AddSubMenuResponse response) {
		
		
	}


	@Override
	public void onCreateInteractionChoiceSetResponse(
			CreateInteractionChoiceSetResponse response) {
		
		
	}


	@Override
	public void onAlertResponse(AlertResponse response) {
		
		
	}


	@Override
	public void onDeleteCommandResponse(DeleteCommandResponse response) {
		
		
	}


	@Override
	public void onDeleteInteractionChoiceSetResponse(
			DeleteInteractionChoiceSetResponse response) {
		
		
	}


	@Override
	public void onDeleteSubMenuResponse(DeleteSubMenuResponse response) {
		
		
	}


	@Override
	public void onPerformInteractionResponse(PerformInteractionResponse response) {
		
		
	}


	@Override
	public void onResetGlobalPropertiesResponse(
			ResetGlobalPropertiesResponse response) {
		
		
	}


	@Override
	public void onSetGlobalPropertiesResponse(
			SetGlobalPropertiesResponse response) {
		
		
	}


	@Override
	public void onSetMediaClockTimerResponse(SetMediaClockTimerResponse response) {
		
		
	}


	@Override
	public void onShowResponse(ShowResponse response) {
		
		
	}


	@Override
	public void onSpeakResponse(SpeakResponse response) {
		
		
	}


	@Override
	public void onOnButtonEvent(OnButtonEvent notification) {
		
		
	}


	@Override
	public void onOnButtonPress(OnButtonPress notification) {
		
		
	}


	@Override
	public void onSubscribeButtonResponse(SubscribeButtonResponse response) {
		
		
	}


	@Override
	public void onUnsubscribeButtonResponse(UnsubscribeButtonResponse response) {
		
		
	}


	@Override
	public void onOnPermissionsChange(OnPermissionsChange notification) {
		
		
	}


	@Override
	public void onSubscribeVehicleDataResponse(
			SubscribeVehicleDataResponse response) {
		
		
	}


	@Override
	public void onUnsubscribeVehicleDataResponse(
			UnsubscribeVehicleDataResponse response) {
		
		
	}


	@Override
	public void onGetVehicleDataResponse(GetVehicleDataResponse response) {
		
		
	}


	@Override
	public void onOnVehicleData(OnVehicleData notification) {
		
		
	}


	@Override
	public void onPerformAudioPassThruResponse(
			PerformAudioPassThruResponse response) {
		
		
	}


	@Override
	public void onEndAudioPassThruResponse(EndAudioPassThruResponse response) {
		
		
	}


	@Override
	public void onOnAudioPassThru(OnAudioPassThru notification) {
		
		
	}


	@Override
	public void onPutFileResponse(PutFileResponse response) {
		
		
	}


	@Override
	public void onDeleteFileResponse(DeleteFileResponse response) {
		
		
	}


	@Override
	public void onListFilesResponse(ListFilesResponse response) {
		
		
	}


	@Override
	public void onSetAppIconResponse(SetAppIconResponse response) {
		
		
	}


	@Override
	public void onScrollableMessageResponse(ScrollableMessageResponse response) {
		
		
	}


	@Override
	public void onChangeRegistrationResponse(ChangeRegistrationResponse response) {
		
		
	}


	@Override
	public void onSetDisplayLayoutResponse(SetDisplayLayoutResponse response) {
		
		
	}


	@Override
	public void onOnLanguageChange(OnLanguageChange notification) {
		
		
	}


	@Override
	public void onOnHashChange(OnHashChange notification) {
		
		
	}


	@Override
	public void onSliderResponse(SliderResponse response) {
		
		
	}


	@Override
	public void onOnDriverDistraction(OnDriverDistraction notification) {
		
		
	}


	@Override
	public void onOnTBTClientState(OnTBTClientState notification) {
		
		
	}


	@Override
	public void onOnSystemRequest(OnSystemRequest notification) {
		
		
	}


	@Override
	public void onSystemRequestResponse(SystemRequestResponse response) {

	}


	@Override
	public void onOnKeyboardInput(OnKeyboardInput notification) {
	
	}


	@Override
	public void onOnTouchEvent(OnTouchEvent notification) {
	
	}


	@Override
	public void onDiagnosticMessageResponse(DiagnosticMessageResponse response) {
	
	}


	@Override
	public void onReadDIDResponse(ReadDIDResponse response) {
	
	}


	@Override
	public void onGetDTCsResponse(GetDTCsResponse response) {
	
	}


	@Override
	public void onOnLockScreenNotification(OnLockScreenStatus notification) {	
	
	}


	@Override
	public void onOnStreamRPC(OnStreamRPC notification) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onStreamRPCResponse(StreamRPCResponse response) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onDialNumberResponse(DialNumberResponse response) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onSendLocationResponse(SendLocationResponse response) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onServiceEnded(OnServiceEnded serviceEnded) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onServiceNACKed(OnServiceNACKed serviceNACKed) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onShowConstantTbtResponse(ShowConstantTbtResponse response) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onAlertManeuverResponse(AlertManeuverResponse response) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onUpdateTurnListResponse(UpdateTurnListResponse response) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onServiceDataACK() {
		// TODO Auto-generated method stub
		
	}

}
