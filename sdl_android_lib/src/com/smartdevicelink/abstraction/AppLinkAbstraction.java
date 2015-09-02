package com.smartdevicelink.abstraction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import android.app.Service;
import android.util.SparseArray;

import com.smartdevicelink.abstraction.exception.MissingListenerException;
import com.smartdevicelink.abstraction.listener.AudioPassThruListener;
import com.smartdevicelink.abstraction.listener.ButtonListener;
import com.smartdevicelink.abstraction.listener.DriverDistractionListener;
import com.smartdevicelink.abstraction.listener.FirstFullHMINotificationListener;
import com.smartdevicelink.abstraction.listener.HMINotificationListener;
import com.smartdevicelink.abstraction.listener.HashChangeListener;
import com.smartdevicelink.abstraction.listener.OnCommandListener;
import com.smartdevicelink.abstraction.listener.PutFileStreamListener;
import com.smartdevicelink.abstraction.listener.RPCListener;
import com.smartdevicelink.abstraction.listener.ResumeDataPersistenceListener;
import com.smartdevicelink.abstraction.listener.SystemRequestListener;
import com.smartdevicelink.abstraction.listener.VehicleDataListener;
import com.smartdevicelink.abstraction.log.Log;
import com.smartdevicelink.abstraction.rpc.PerformAudioPassThruWithListener;
import com.smartdevicelink.abstraction.rpc.SoftButtonWithListener;
import com.smartdevicelink.abstraction.rpc.SubscribeButtonWithListener;
import com.smartdevicelink.abstraction.rpc.SubscribeVehicleDataWithListener;
import com.smartdevicelink.abstraction.rpc.SystemRequestWithListener;
import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.SdlProxyALM;
import com.smartdevicelink.proxy.SdlProxyConfigurationResources;
import com.smartdevicelink.proxy.interfaces.ISoftButton;
import com.smartdevicelink.proxy.rpc.AddCommand;
import com.smartdevicelink.proxy.rpc.Alert;
import com.smartdevicelink.proxy.rpc.MenuParams;
import com.smartdevicelink.proxy.rpc.OnAudioPassThru;
import com.smartdevicelink.proxy.rpc.OnButtonEvent;
import com.smartdevicelink.proxy.rpc.OnButtonPress;
import com.smartdevicelink.proxy.rpc.OnCommand;
import com.smartdevicelink.proxy.rpc.OnDriverDistraction;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.OnHashChange;
import com.smartdevicelink.proxy.rpc.OnLanguageChange;
import com.smartdevicelink.proxy.rpc.OnLockScreenStatus;
import com.smartdevicelink.proxy.rpc.OnPermissionsChange;
import com.smartdevicelink.proxy.rpc.OnStreamRPC;
import com.smartdevicelink.proxy.rpc.OnSystemRequest;
import com.smartdevicelink.proxy.rpc.OnVehicleData;
import com.smartdevicelink.proxy.rpc.PerformAudioPassThru;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.SubscribeButton;
import com.smartdevicelink.proxy.rpc.SubscribeVehicleData;
import com.smartdevicelink.proxy.rpc.SystemRequest;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.VehicleType;
import com.smartdevicelink.proxy.rpc.enums.AppHMIType;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.proxy.rpc.enums.SdlDisconnectedReason;
import com.smartdevicelink.transport.BaseTransportConfig;

public abstract class AppLinkAbstraction {
	
	private static final String TAG = AppLinkAbstraction.class.getSimpleName();

	private AppLinkProxyListener mAppLinkProxyListener;

	private SparseArray<RPCListener> mRPCResponseListeners;
	private ArrayList<DriverDistractionListener> mDriverDistractionListeners;
	private HMINotificationListener mHMINotificationListener;
	private HashChangeListener mHashChangeListener;
	private ResumeDataPersistenceListener mResumeDataPersistenceListener;
	private FirstFullHMINotificationListener mFirstHMINotificationListener;
	private Map<ButtonName, ButtonListener> mButtonListeners;
	private SparseArray<SoftButtonWithListener> mCustomButtonListeners;
	private AudioPassThruListener mAudioPassThruListener;
	private SparseArray<OnCommandListener> mOnCommandListeners;
	private VehicleDataListener mVehicleDataListener;
	private PutFileStreamListener mPutFileStreamListener;
	private SystemRequestListener mSystemRequestListener;

	private SdlProxyALM mAppLinkProxy;

	protected boolean mIsConnected = false;

	private int coorId = 3000;
	private int cmdID = 1000;
	private int softButtonID = 1;

	private static final int CORRELATION_ID = 0;
	private static final int COMMAND_ID 	 = 1;
	private static final int SOFTBUTTON_ID  = 2;

	public AppLinkAbstraction(){
		mRPCResponseListeners = new SparseArray<RPCListener>(5);
		mDriverDistractionListeners = new ArrayList<DriverDistractionListener>(5);
		mAppLinkProxyListener = new AppLinkProxyListener(this);
		mButtonListeners = new HashMap<ButtonName, ButtonListener>();
		mCustomButtonListeners = new SparseArray<SoftButtonWithListener>();
		mOnCommandListeners = new SparseArray<OnCommandListener>();
	}

	//start proxy
	protected final void startProxy(String appName, boolean isMedia, String appId, Service appService, SdlProxyConfigurationResources syncProxyConfigurationResources, Vector<TTSChunk> ttsName, String sNgnAppName, Vector<String> vrSynonyms, SdlMsgVersion syncMsgVersion, Language languageDesired, Language hmiDisplayLanguageDesired, Vector<AppHMIType> appType, String autoActivateID, boolean callbackToUIThread, boolean preRegister, String sAppResumeHash, BaseTransportConfig transportConfig) throws SdlException{		
		if (mAppLinkProxy == null)
		{
			//mAppLinkProxy = new SdlProxyALM(mAppLinkProxyListener, appName, isMedia, appId/*, myConfig*/);
			mAppLinkProxy = new SdlProxyALM(appService,mAppLinkProxyListener,null,appName,ttsName,sNgnAppName,vrSynonyms,isMedia,null,languageDesired,hmiDisplayLanguageDesired,appType,appId,null,false,false,sAppResumeHash,transportConfig);						
		}
	}

	public void sendRPCRequest(RPCRequest request) throws SdlException {
		sendRPCRequest(request, null);
	}

	public void sendRPCRequest(RPCRequest request, RPCListener rpcListener) {
		Integer coorid = autoIncID(CORRELATION_ID);
		request.setCorrelationID(coorid);
		if(rpcListener != null)
			mRPCResponseListeners.put(coorid, rpcListener);
		if (request instanceof ISoftButton) {
			ISoftButton buttonRPC = (ISoftButton)request;
			// TODO: was originally Vector<SoftButton> buttons = buttonRPC.getSoftButtons();
			List<SoftButton> buttons = buttonRPC.getSoftButtons();
			if (buttons != null && buttons.size() > 0){
				for (int i = 0; i < buttons.size(); i++) {
					try {
						SoftButtonWithListener buttonWithListener = (SoftButtonWithListener)buttons.get(i);
						if(buttonWithListener.getListener() == null)
							throw new MissingListenerException(request);
						Integer iSoftBtnID = autoIncID(SOFTBUTTON_ID);
						buttonWithListener.setSoftButtonID(iSoftBtnID);
						mCustomButtonListeners.put(buttonWithListener.getSoftButtonID(), buttonWithListener);
					} catch (ClassCastException e) {
						throw new MissingListenerException(request);
					}


				}
			}
		} else if (request instanceof Alert) {
			Alert alert = (Alert) request;
			// TODO: Was originally Vector<SoftButton> alertButtons = alert.getSoftButtons();
			List<SoftButton> alertButtons = alert.getSoftButtons();
			if (alertButtons != null && alertButtons.size() > 0){
				for (int i = 0; i < alertButtons.size(); i++) {
					try {
						SoftButtonWithListener buttonWithListener = (SoftButtonWithListener)alertButtons.get(i);
						if(buttonWithListener.getListener() == null)
							throw new MissingListenerException(request);
						mCustomButtonListeners.put(buttonWithListener.getSoftButtonID(), buttonWithListener);
					} catch (ClassCastException e) {
						throw new MissingListenerException(request);
					}


				}
			}
		} else if (request instanceof PerformAudioPassThru){
			if(!(request instanceof PerformAudioPassThruWithListener)) throw new MissingListenerException(request);
			if(((PerformAudioPassThruWithListener)request).getListener() == null) throw new MissingListenerException(request);			

			mAudioPassThruListener = ((PerformAudioPassThruWithListener)request).getListener();
		} else if(request instanceof AddCommand){
			if(!(request instanceof AddCommandWithListener)) throw new MissingListenerException(request);
			if(((AddCommandWithListener)request).getListener() == null) throw new MissingListenerException(request);			


			Integer iCommandID = autoIncID(COMMAND_ID);
			((AddCommand)request).setCmdID(iCommandID);
			mOnCommandListeners.put(iCommandID, ((AddCommandWithListener)request).getListener());

		} else if(request instanceof SubscribeButton){
			if(!(request instanceof SubscribeButtonWithListener)) throw new MissingListenerException(request);
			if(((SubscribeButtonWithListener)request).getListener() == null) throw new MissingListenerException(request);			

			mButtonListeners.put(((SubscribeButtonWithListener)request).getButtonName(), ((SubscribeButtonWithListener)request).getListener());
		}  else if(request instanceof SystemRequest){
			if(!(request instanceof SystemRequestWithListener)) throw new MissingListenerException(request);
			if(((SystemRequestWithListener)request).getListener() == null) throw new MissingListenerException(request);
			
			mSystemRequestListener = ((SystemRequestWithListener)request).getListener();
		} else if(request instanceof SubscribeVehicleData) {
			if(!(request instanceof SubscribeVehicleDataWithListener)) throw new MissingListenerException(request);
			if(((SubscribeVehicleDataWithListener)request).getListener() == null) throw new MissingListenerException(request);
			
			mVehicleDataListener = ((SubscribeVehicleDataWithListener)request).getListener();
		}
		//TODO add more "notification" type RPCs
		if (mAppLinkProxy != null)
		{
			try {mAppLinkProxy.sendRPCRequest(request);} catch (SdlException e) {}
		}
		else
		{
			//TODO Add the appropriate exception here
			Log.e(TAG, "sendRPCRequest - proxy object is null, cannot send RPC request");
		}
	}

	public void handleResponse(RPCResponse response){
		int coorId = response.getCorrelationID();
		RPCListener listener = mRPCResponseListeners.get(coorId);
		if(listener != null){
			Log.v(TAG, listener.getClass().getSimpleName());
			listener.handleResponse(response);
			mRPCResponseListeners.remove(coorId);
		}
	}

	// TODO: Add onSystemRequest
	public void handleNotification(RPCNotification notification){
		if(notification instanceof OnButtonPress){
			ButtonListener listener = null;
			OnButtonPress buttonPress = (OnButtonPress)notification;
			if(buttonPress.getButtonName() != ButtonName.CUSTOM_BUTTON)
				listener = mButtonListeners.get(buttonPress.getButtonName());
			else
			{
				//Since we have the SoftButtonWithListener object, we can get the listener and the text on the button for future use
				SoftButtonWithListener softButtonObj =  mCustomButtonListeners.get(buttonPress.getCustomButtonName());
				listener = softButtonObj.getListener();
			}
			if(listener!=null) listener.handleButtonPress((OnButtonPress)notification);
		} else if (notification instanceof OnButtonEvent){
			ButtonListener listener = null;
			OnButtonEvent buttonEvent = (OnButtonEvent)notification;
			if(buttonEvent.getButtonName() != ButtonName.CUSTOM_BUTTON)
				listener = mButtonListeners.get(buttonEvent.getButtonName());
			else
			{
				//Since we have the SoftButtonWithListener object, we can get the listener and the text on the button for future use
				SoftButtonWithListener softButtonObj = mCustomButtonListeners.get(buttonEvent.getCustomButtonID());
				listener = softButtonObj.getListener();
			}
			if(listener!=null) listener.handleButtonEvent((OnButtonEvent)buttonEvent);
		} else if(notification instanceof OnAudioPassThru){
			OnAudioPassThru audioPass = (OnAudioPassThru)notification;
			mAudioPassThruListener.handleAudioData(audioPass.getAPTData());
		} else if(notification instanceof OnCommand){
			OnCommandListener listener = mOnCommandListeners.get(((OnCommand)notification).getCmdID());
			listener.handleCommand(((OnCommand)notification));
		} else if(notification instanceof OnVehicleData){
			mVehicleDataListener.handleVehicleData((OnVehicleData)notification);
		} else if(notification instanceof OnStreamRPC){
			mPutFileStreamListener.handleOnStreamRPC((OnStreamRPC) notification);
		} else if(notification instanceof OnSystemRequest){
			if(mSystemRequestListener != null)
				mSystemRequestListener.handleSystemRequest((OnSystemRequest) notification);
		}

	}

	public void setResumeDataPersistenceListener(ResumeDataPersistenceListener listener){
		mResumeDataPersistenceListener = listener;
	}

	public void setHashChangeListener(HashChangeListener listener){
		mHashChangeListener = listener;
	}

	public void setHMINotificationListener(HMINotificationListener listener){
		mHMINotificationListener = listener;
	}

	public void setFirstFullHMINotificationListener(FirstFullHMINotificationListener listener){
		mFirstHMINotificationListener = listener;
	}

	public void onResumeDataPersistenceListener(Boolean bSuccess)
	{
		if(mResumeDataPersistenceListener != null)
			mResumeDataPersistenceListener.onResumeDataPersistence(bSuccess);
	}

	public void onHashChange(OnHashChange notification)
	{
		if(mHashChangeListener != null)
			mHashChangeListener.onHashChange(notification);
	}

	public void onHMIStatus(OnHMIStatus status){
		if(!mIsConnected){
			mIsConnected = true;
		}
		if(mHMINotificationListener != null)
			mHMINotificationListener.onHMIStatus(status);
		if(status.getHmiLevel() == HMILevel.HMI_FULL 
				&& status.getFirstRun() 
				&& mFirstHMINotificationListener != null){
			mFirstHMINotificationListener.onFirstHMIFull();
			sendAboutAddCommand();
			sendDIDForDD();
		}
	}

	protected VehicleType getVehicleType(){
		try {
			return mAppLinkProxy.getVehicleType();
		} catch (SdlException e) {
			Log.e(TAG, e.getMessage(), e);
			return null;
		}
	}
	
	protected SdlMsgVersion getSdlMsgVersion(){
		try {
			return mAppLinkProxy.getSdlMsgVersion();
		} catch (SdlException e) {
			Log.e(TAG, e.getMessage(), e);
			return null;
		}
	}

	public void addDriverDistractionListener(DriverDistractionListener listener) {
		this.mDriverDistractionListeners.add(listener);
	}

	protected void onError(String arg0, Exception arg1){
		Log.i(TAG, "OnError"); 
		mIsConnected = false;
	}

	public abstract void onOnLockScreenNotification(OnLockScreenStatus status);
	public abstract void onOnLanguageChange(OnLanguageChange languageChange);
	public abstract void onOnPermissionsChange(OnPermissionsChange permissions);

	public void onDriverDistraction(OnDriverDistraction arg0){
		Log.i(TAG, "OnDriverDistraction received");
		for(int i = 0; i < mDriverDistractionListeners.size(); i++){
			mDriverDistractionListeners.get(i).onDriverDistraction(arg0.getState());
		}
	}

	public void onProxyClosed(String error, Exception ex,SdlDisconnectedReason reason){
		Log.i(TAG, "On Proxy Closed");
		mAppLinkProxy = null;
		mIsConnected = false;
	}

	public void disposeProxy(){
		if(mAppLinkProxy == null)
			return;
		try {
			mAppLinkProxy.dispose();
			mAppLinkProxy = null;
		} catch (SdlException e) {
			Log.e(TAG, e.getMessage(), e);
		}
	}
	
	private Integer autoIncID(int iTypePar){
		if (iTypePar == CORRELATION_ID)
			return coorId++;
		else if (iTypePar == COMMAND_ID)
			return cmdID++;
		else if (iTypePar == SOFTBUTTON_ID)
			return softButtonID++;

		return null;
	}

	private void sendAboutAddCommand(){
		AddCommandWithListener ac = new AddCommandWithListener();
		ac.setCorrelationID(autoIncID(CORRELATION_ID));
		MenuParams menuParams = new MenuParams();
		menuParams.setMenuName("About AppLink");
		menuParams.setPosition(99);
		ac.setMenuParams(menuParams);
		Vector<String> vrCommands = new Vector<String>();
		vrCommands.add("About AppLink");
		ac.setVrCommands(vrCommands);
		ac.setListener(aboutAppLinkListener);
		sendRPCRequest(ac, new RPCListener() {

			@Override
			public void handleResponse(RPCResponse response) {
				if(!response.getSuccess())
					Log.w(TAG, "Unable to add about menu");
			}
		});

	}

	private void sendDIDForDD(){

	}

	private OnCommandListener aboutAppLinkListener = new OnCommandListener() {

		@Override
		public void handleCommand(OnCommand command) {
			Log.i(TAG, "About Pressed");

		}
	};

}
