package com.smartdevicelink.abstraction;

import java.util.Vector;

import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.exception.WrongThreadException;
import com.smartdevicelink.proxy.SdlProxyConfigurationResources;
import com.smartdevicelink.proxy.interfaces.IProxyListenerALM;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.enums.AppHMIType;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.transport.BTTransportConfig;
import com.smartdevicelink.transport.BaseTransportConfig;

import android.app.Service;
import android.os.Looper;

public abstract class SdlBase extends SdlAbstraction {

		protected String appId;
		protected boolean isMedia;
		protected String appName;
		protected static Object SDL_LOCK = new Object();

		protected Service appService = null;
		protected IProxyListenerALM listener = null;
		protected SdlProxyConfigurationResources sdlProxyConfigurationResources = null; 
		protected Vector<TTSChunk> ttsName = null;
		protected String sNgnAppName = null;
		protected Vector<String> vrSynonyms = null;
		protected SdlMsgVersion sdlMsgVersion = null;
		protected Language languageDesired = null;
		protected Language hmiDisplayLanguageDesired = null;
		protected Vector<AppHMIType> appType = null; 
		protected String autoActivateID = null;
		protected boolean callbackToUIThread;
		protected boolean preRegister;
		protected BaseTransportConfig transportConfig = new BTTransportConfig();
		protected String sAppResumeHash;


		protected SdlBase() {
			super();
		}

		public void startProxy(){
			threadCheck();
			synchronized (SDL_LOCK) {
				try {
					super.startProxy(appName, isMedia, appId, appService, sdlProxyConfigurationResources, ttsName, sNgnAppName, vrSynonyms, sdlMsgVersion, languageDesired, hmiDisplayLanguageDesired, appType, autoActivateID, callbackToUIThread, preRegister, sAppResumeHash, transportConfig);
				} catch (SdlException e) {
					e.printStackTrace();
				}
			}
		}
		
		protected void threadCheck() {		
			if(Thread.currentThread() == Looper.getMainLooper().getThread())
				throw new WrongThreadException();		
		}
}
