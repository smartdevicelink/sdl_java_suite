package com.smartdevicelink.abstraction;

import java.util.Vector;

import android.app.Service;
import android.os.Looper;

import com.smartdevicelink.abstraction.exception.WrongThreadException;
import com.smartdevicelink.abstraction.log.Log;
import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.proxy.SdlProxyConfigurationResources;
import com.smartdevicelink.proxy.interfaces.IProxyListenerALM;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.enums.AppHMIType;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.proxy.rpc.enums.SdlDisconnectedReason;
import com.smartdevicelink.transport.BTTransportConfig;
import com.smartdevicelink.transport.BaseTransportConfig;

public abstract class AppLinkBase extends AppLinkAbstraction{
	
	private static final String TAG = AppLinkAbstraction.class.getSimpleName();

	protected String appId;
	protected boolean isMedia;
	protected String appName;
	protected static Object APPLINK_LOCK = new Object();

	protected Service appService = null;
	protected IProxyListenerALM listener = null;
	protected SdlProxyConfigurationResources syncProxyConfigurationResources = null; 
	protected Vector<TTSChunk> ttsName = null;
	protected String sNgnAppName = null;
	protected Vector<String> vrSynonyms = null;
	protected SdlMsgVersion syncMsgVersion = null;
	protected Language languageDesired = null;
	protected Language hmiDisplayLanguageDesired = null;
	protected Vector<AppHMIType> appType = null; 
	protected String autoActivateID = null;
	protected boolean callbackToUIThread;
	protected boolean preRegister;
	protected BaseTransportConfig transportConfig = new BTTransportConfig();
	protected String sAppResumeHash;


	protected AppLinkBase() {
		super();
	}

	public void startProxy(){
		threadCheck();
		synchronized (APPLINK_LOCK) {
			try {
				super.startProxy(appName, isMedia, appId, appService, syncProxyConfigurationResources, ttsName, sNgnAppName, vrSynonyms, syncMsgVersion, languageDesired, hmiDisplayLanguageDesired, appType, autoActivateID, callbackToUIThread, preRegister, sAppResumeHash, transportConfig);
			} catch (SdlException e) {
				Log.e(TAG, e.getMessage(), e);
			}
		}
	}
	
	protected void threadCheck() {
		if(Thread.currentThread() == Looper.getMainLooper().getThread())
			throw new WrongThreadException();
	}

	public void onProxyClosed(String error, Exception ex, SdlDisconnectedReason reason){
		super.onProxyClosed(error, ex, reason);
	}

}
