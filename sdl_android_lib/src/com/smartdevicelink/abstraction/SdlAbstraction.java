package com.smartdevicelink.abstraction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import android.app.Service;
import android.util.Log;
import android.util.SparseArray;

import com.smartdevicelink.abstraction.listeners.AudioPassThruListener;
import com.smartdevicelink.abstraction.listeners.ButtonListener;
import com.smartdevicelink.abstraction.listeners.DriverDistractionListener;
import com.smartdevicelink.abstraction.listeners.FirstFullHMINotificationListener;
import com.smartdevicelink.abstraction.listeners.HMINotificationListener;
import com.smartdevicelink.abstraction.listeners.HashChangeListener;
import com.smartdevicelink.abstraction.listeners.OnCommandListener;
import com.smartdevicelink.abstraction.listeners.RPCListener;
import com.smartdevicelink.abstraction.listeners.ResumeDataPersistenceListener;
import com.smartdevicelink.abstraction.listeners.StreamRPCListener;
import com.smartdevicelink.abstraction.listeners.StreamRPCResponseListener;
import com.smartdevicelink.abstraction.listeners.SubscribeVehicleDataListener;
import com.smartdevicelink.abstraction.listeners.VehicleDataListener;
import com.smartdevicelink.exception.MissingListenerException;
import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.SdlProxyALM;
import com.smartdevicelink.proxy.SdlProxyConfigurationResources;
import com.smartdevicelink.proxy.interfaces.ISoftButton;
import com.smartdevicelink.proxy.rpc.AddCommand;
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
import com.smartdevicelink.proxy.rpc.OnVehicleData;
import com.smartdevicelink.proxy.rpc.PerformAudioPassThru;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.StreamRPCResponse;
import com.smartdevicelink.proxy.rpc.SubscribeButton;
import com.smartdevicelink.proxy.rpc.SubscribeVehicleData;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.enums.AppHMIType;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.proxy.rpc.enums.SdlDisconnectedReason;
import com.smartdevicelink.proxy.view.SDLView;
import com.smartdevicelink.proxy.view.SDLViewManager;
import com.smartdevicelink.transport.BaseTransportConfig;
import com.smartdevicelink.abstraction.listeners.AddCommandWithListener;
import com.smartdevicelink.proxy.rpc.RegisterAppInterfaceResponse;
import com.smartdevicelink.abstraction.listeners.PerformAudioPassThruWithListener;
import com.smartdevicelink.abstraction.listeners.RegisterAppInterfaceResponseListener;
import com.smartdevicelink.abstraction.listeners.SoftButtonWithListener;
import com.smartdevicelink.abstraction.listeners.SubscribeButtonWithListener;

public abstract class SdlAbstraction {
	
	//Proxy Functional Objects
	private SdlProxyListener mSdlProxyListener;
	private SDLViewManager mViewManager;
	
	//Life Cycle Listeners
	private FirstFullHMINotificationListener mFirstHMINotificationListener;
	private ArrayList<HMINotificationListener> mHMINotificationListeners;
	//private HMINotificationListener mHMINotificationListener;
	private HashChangeListener mHashChangeListener;
	private ResumeDataPersistenceListener mResumeDataPersistenceListener;

	//Response Listeners
	private SparseArray<RPCListener> mRPCResponseListeners;
	private SparseArray<SDLView> mViewResponseListeners;

	//Notification Listeners
	private Map<ButtonName, ButtonListener> mButtonListeners;
	private SparseArray<SoftButtonWithListener> mCustomButtonListeners;
	private AudioPassThruListener mAudioPassThruListener;
	private SparseArray<OnCommandListener> mOnCommandListeners;
	private VehicleDataListener mVehicleDataListener;
	private DriverDistractionListener mDriverDistractionListener;
	private RegisterAppInterfaceResponseListener mRegisterAppInterfaceResponseListener;
	private StreamRPCListener mStreamRPCListener;
	private StreamRPCResponseListener mStreamRPCResponseListener;
	private boolean mPutfileStreamSuccess = false;

	private SdlProxyALM mSdlProxy;

	private boolean mIsConnected= false;

	private int coorId = 0;
	private int cmdID = 0;
	private int softButtonID = 0;
	
	private static final int CORRELATION_ID  = 0;
	private static final int COMMAND_ID 	 = 1;
	private static final int SOFTBUTTON_ID   = 2;
	 
	//max value for correlation id = 65527 for legacy compatibility
	private static final int MAX_CORRELATION_ID = 65527;
	
	//min value for command id = 0 max value for command id =  2000000000 according to spec	
	private static final int MAX_COMMAND_ID = 2000000000;
	
	//min value for softbutton id = 0 max value for softbutton id = 65535 according to spec
	private static final int MAX_SOFTBUTTON_ID = 65535;
	
	public SdlAbstraction(){
		mRPCResponseListeners = new SparseArray<RPCListener>(5);
		mSdlProxyListener = new SdlProxyListener(this);
		mButtonListeners = new HashMap<ButtonName, ButtonListener>();
		mCustomButtonListeners = new SparseArray<SoftButtonWithListener>();
		mOnCommandListeners = new SparseArray<OnCommandListener>();
		mViewResponseListeners = new SparseArray<SDLView>();
		mHMINotificationListeners = new ArrayList<HMINotificationListener>();
		mViewManager = new SDLViewManager();
		mHMINotificationListeners.add(mViewManager.hmiListener);
	}

	//start proxy
	protected final void startProxy(String appName, boolean isMedia, String appId, Service appService, SdlProxyConfigurationResources sdlProxyConfigurationResources, Vector<TTSChunk> ttsName, String sNgnAppName, Vector<String> vrSynonyms, SdlMsgVersion sdlMsgVersion, Language languageDesired, Language hmiDisplayLanguageDesired, Vector<AppHMIType> appType, String autoActivateID, boolean callbackToUIThread, boolean preRegister, String sAppResumeHash, BaseTransportConfig transportConfig) throws SdlException{		
		if (mSdlProxy == null)
		{
			mSdlProxy = new SdlProxyALM(appService,mSdlProxyListener,null,appName,ttsName,sNgnAppName,vrSynonyms,isMedia,null,languageDesired,hmiDisplayLanguageDesired,appType,appId,null,false,false,sAppResumeHash,transportConfig);						
		}
	}

	public final boolean PutFileStream(String sPath, String sdlFileName, Integer iOffset, FileType fileType, Boolean bPersistentFile, Boolean bSystemFile, Integer iCorrelationID) throws SdlException {
	    if (mSdlProxy != null)
	    {
	    	mPutfileStreamSuccess = mSdlProxy.PutFileStream(sPath, sdlFileName, iOffset, fileType, bPersistentFile, bSystemFile, iCorrelationID);
	    }
	    return mPutfileStreamSuccess;
	}

	public final void sendRPCRequest(RPCRequest request) throws SdlException {
		sendRPCRequest(request, null);
	}

	public final void sendRPCRequest(RPCRequest request, RPCListener rpcListener) throws SdlException {
		autoIncID(CORRELATION_ID);
		Integer coorid =  getAutoIncID(CORRELATION_ID);
		request.setCorrelationID(coorid);
		if(rpcListener != null)
			mRPCResponseListeners.put(coorid, rpcListener);
		
		if(request instanceof SDLView){
			mViewManager.viewInitialized((SDLView)request);
			mViewResponseListeners.put(coorid, (SDLView)request);
		}
		
		if (request instanceof ISoftButton) {
			ISoftButton buttonRPC = (ISoftButton)request;
			List<SoftButton> buttons = buttonRPC.getSoftButtons();
			if (buttons != null && buttons.size() > 0){
				for (int i = 0; i < buttons.size(); i++) {
					try {
						SoftButtonWithListener buttonWithListener = (SoftButtonWithListener)buttons.get(i);
						if(buttonWithListener.getListener() == null)
							throw new MissingListenerException(request);
						autoIncID(SOFTBUTTON_ID);
						Integer iSoftBtnID = getAutoIncID(SOFTBUTTON_ID);
						buttonWithListener.setSoftButtonID(iSoftBtnID);
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

			autoIncID(COMMAND_ID);
			Integer iCommandID = getAutoIncID(COMMAND_ID);
			((AddCommand)request).setCmdID(iCommandID);
			mOnCommandListeners.put(iCommandID, ((AddCommandWithListener)request).getListener());

		} else if(request instanceof SubscribeButton){
			if(!(request instanceof SubscribeButtonWithListener)) throw new MissingListenerException(request);
			if(((SubscribeButtonWithListener)request).getListener() == null) throw new MissingListenerException(request);			

			mButtonListeners.put(((SubscribeButtonWithListener)request).getButtonName(), ((SubscribeButtonWithListener)request).getListener());
		} else if(request instanceof SubscribeVehicleData){
			if(!(request instanceof SubscribeVehicleDataListener)) throw new MissingListenerException(request);
			if(((SubscribeVehicleDataListener)request).getListener() == null) throw new MissingListenerException(request);

			mVehicleDataListener = ((SubscribeVehicleDataListener)request).getListener();
		}
		
		//TODO add more "notification" type RPCs
		if (mSdlProxy != null)
		{
			mSdlProxy.sendRPCRequest(request);
		}
		else
		{
			//TODO Add the appropriate exception here
			Log.e("sendRPCRequest", "proxy object is null, cannot send RPC request");
		}
	}

	public final void handleResponse(RPCResponse response){
		int coorId = response.getCorrelationID();
		if(mViewResponseListeners.get(coorId) != null){
			mViewManager.viewShown(mViewResponseListeners.get(coorId));			
			mViewResponseListeners.remove(coorId);
		}
		
		RPCListener listener = mRPCResponseListeners.get(coorId);
		if(listener != null){
			listener.handleResponse(response);
			mRPCResponseListeners.remove(coorId);
		}
	}


	public final void handleNotification(RPCNotification notification){
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
			mAudioPassThruListener.handleAudioData(audioPass);
		} else if(notification instanceof OnCommand){
			OnCommandListener listener = mOnCommandListeners.get(((OnCommand)notification).getCmdID());
			listener.handleCommand(((OnCommand)notification));
		} else if(notification instanceof OnVehicleData){
			mVehicleDataListener.handleVehicleData((OnVehicleData)notification);
		}

	}

	public final void setDriverDistractionListener(DriverDistractionListener listener) {
		this.mDriverDistractionListener = listener;
	}
	
	public final void setResumeDataPersistenceListener(ResumeDataPersistenceListener listener){
		mResumeDataPersistenceListener = listener;
	}

	public final void setHashChangeListener(HashChangeListener listener){
		mHashChangeListener = listener;
	}

	public final void addHMINotificationListener(HMINotificationListener listener){
		mHMINotificationListeners.add(listener);
	}
	public final void setRegisterAppInterfaceResponseListener(RegisterAppInterfaceResponseListener listener){
		mRegisterAppInterfaceResponseListener = listener;
	}
	public final void setFirstFullHMINotificationListener(FirstFullHMINotificationListener listener){
		mFirstHMINotificationListener = listener;
	}

	public final void onResumeDataPersistenceListener(Boolean bSuccess)
	{
		if(mResumeDataPersistenceListener != null)
			mResumeDataPersistenceListener.onResumeDataPersistence(bSuccess);
	}
	public final void onRegisterAppInterfaceResponse(RegisterAppInterfaceResponse response)
	{
		if(mRegisterAppInterfaceResponseListener != null)
			mRegisterAppInterfaceResponseListener.onRegisterAppInterfaceResponse(response);
	}
	public final void onHashChange(OnHashChange notification)
	{
		if(mHashChangeListener != null)
			mHashChangeListener.onHashChange(notification);
	}

	public final void onStreamRPCListener(OnStreamRPC notification)
	{
		if(mStreamRPCListener != null)
			mStreamRPCListener.onStreamRPC(notification);
	}

	public final void onStreamRPCResponseListener(StreamRPCResponse response)
	{
		if(mStreamRPCResponseListener != null)
			mStreamRPCResponseListener.onStreamRPCResponse(response);
	}

	public final void setStreamRPCListener(StreamRPCListener listener){
		mStreamRPCListener = listener;
	}

	public final void setStreamRPCResponseListener(StreamRPCResponseListener listener){
		mStreamRPCResponseListener = listener;
	}

	public final void onHMIStatus(OnHMIStatus status){
		if(!mIsConnected){
			mIsConnected = true;
		}
		
		for (HMINotificationListener hmiListener : mHMINotificationListeners) {
			if(hmiListener != null)
				hmiListener.onHMIStatus(status);			
        }
		
		if(status.getHmiLevel() == HMILevel.HMI_FULL 
				&& status.getFirstRun() 
				&& mFirstHMINotificationListener != null){
			mFirstHMINotificationListener.onFirstHMIFull();
			sendAboutAddCommand();
		}
	}

	public final boolean getIsSdlConnected(){
	       return mIsConnected;
	}
	public final void onError(String arg0, Exception arg1){
		mSdlProxy = null;
		mIsConnected = false;
		onError(arg1, arg0);
	}

	public final void onProxyClosed(String error, Exception ex,SdlDisconnectedReason reason){
		mSdlProxy = null;
		mIsConnected = false;
		onProxyClosed(ex, reason);
	}
	
	public abstract void onOnLockScreenNotification(OnLockScreenStatus status);
	public abstract void onOnLanguageChange(OnLanguageChange languageChange);
	public abstract void onOnPermissionsChange(OnPermissionsChange permissions);
	public abstract void onProxyClosed(Exception ex,SdlDisconnectedReason reason);
	public abstract void onError(Exception ex,String info);
	
	public final void onDriverDistraction(OnDriverDistraction arg0){
//		mDriverDistractionListener.onDriverDistraction(arg0.getState());
		mDriverDistractionListener.onDriverDistraction(arg0);
	}
		
	private Integer getAutoIncID(int iTypePar){
		if (iTypePar == CORRELATION_ID)
			return coorId;
		else if (iTypePar == COMMAND_ID)
			return cmdID;
		else if (iTypePar == SOFTBUTTON_ID)
			return softButtonID;

		return null;
	}

	
	private void autoIncID(int iTypePar){
		if (iTypePar == CORRELATION_ID)
		{
			 if (coorId < MAX_CORRELATION_ID)
				 coorId++;
			 else
				 coorId = 0;
		}
		else if (iTypePar == COMMAND_ID)
		{
			 if (cmdID < MAX_COMMAND_ID)
				 cmdID++;
			 else
				 cmdID = 0;
		}
		else if (iTypePar == SOFTBUTTON_ID)
		{
			 if (softButtonID < MAX_SOFTBUTTON_ID)
				 softButtonID++;
			 else
				 softButtonID = 0;
		}
	}
	
	private void sendAboutAddCommand(){
		AddCommandWithListener ac = new AddCommandWithListener();
		autoIncID(CORRELATION_ID);		
		ac.setCorrelationID(getAutoIncID(CORRELATION_ID));
		MenuParams menuParams = new MenuParams();
		menuParams.setMenuName("About SDL");
		ac.setMenuParams(menuParams);
		Vector<String> vrCommands = new Vector<String>();
		vrCommands.add("About SDL");
		ac.setVrCommands(vrCommands);
		ac.setListener(aboutSdlListener);
		try {
			sendRPCRequest(ac, new RPCListener() {

				@Override
				public void handleResponse(RPCResponse response) {
					if(!response.getSuccess())
						Log.w("SDL Abstraction", "Unable to add about menu");
				}
			});
		} catch (SdlException e) {
			e.printStackTrace();
		}
	}

	private OnCommandListener aboutSdlListener = new OnCommandListener() {

		@Override
		public void handleCommand(OnCommand command) {
			Log.i("Sdl Abstraction", "About Pressed");

		}
	};
}
