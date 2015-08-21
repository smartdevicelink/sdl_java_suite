package com.smartdevicelink.proxy;

import java.util.Vector;

import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.proxy.interfaces.IProxyListenerALM;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.enums.AppHMIType;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.transport.BTTransportConfig;
import com.smartdevicelink.transport.BaseTransportConfig;
import android.app.Service;

public class SdlProxyBuilder
{
	// Builder Pattern
	private IProxyListenerALM listener;
	private String appId;
	private String appName;
	private Boolean isMediaApp;

	private Service service;
	private SdlProxyConfigurationResources sdlProxyConfigurationResources;
	private Vector<TTSChunk> ttsChunks;
	private String sNgnAppName;
	private Vector<String>vrSynonyms;
	private SdlMsgVersion sdlMessageVersion;
	private Language lang;
	private Language hmiLang;
	private Vector<AppHMIType> vrAppHMITypes;
	private String autoActivateID;
	private boolean callbackToUIThread;
	private boolean preRegister;
	private String sAppResumeHash;
	private BaseTransportConfig mTransport;

	public static class Builder
	{
		// Required parameters
	    private IProxyListenerALM listener;
	    private String appId;
	    private String appName;
	    private Boolean isMediaApp;

	    // Optional parameters - initialized to default values
	    private Service service = null;
	    private SdlProxyConfigurationResources sdlProxyConfigurationResources = null;
	    private Vector<TTSChunk> ttsChunks = null;
	    private String sNgnAppName = null;
	    private Vector<String>vrSynonyms = null;
	    private SdlMsgVersion sdlMessageVersion = null;
	    private Language lang = Language.EN_US;
	    private Language hmiLang = Language.EN_US;
	    private Vector<AppHMIType> vrAppHMITypes = null;
	    private String autoActivateID = null;
	    private boolean callbackToUIThread = false;
	    private boolean preRegister = false;
	    private String sAppResumeHash = null;
	    private BaseTransportConfig mTransport = new BTTransportConfig();

	    public Builder(IProxyListenerALM listener, String appId, String appName, Boolean isMediaApp)
	    {
	    	this.listener 		= listener;
	        this.appId    		= appId;
	        this.appName		= appName;
	        this.isMediaApp		= isMediaApp;
	    }

	    public Builder service(Service val)
	    	{ service = val; return this; }
	    public Builder sdlProxyConfigurationResources(SdlProxyConfigurationResources val)
	    	{ sdlProxyConfigurationResources = val; return this; }
	    public Builder ttsChunks(Vector<TTSChunk> val)
	    	{ ttsChunks = val; return this; }
	    public Builder sNgnAppName(String val)
	    	{ sNgnAppName = val; return this; }
	    public Builder vrSynonyms(Vector<String> val)
	    	{ vrSynonyms = val; return this; }
	    public Builder sdlMessageVersion(SdlMsgVersion val)
	    	{ sdlMessageVersion = val; return this; }
	    public Builder lang(Language val)
	    	{ lang = val; return this; }
	    public Builder hmiLang(Language val)
	    	{ hmiLang = val; return this; }
	    public Builder vrAppHMITypes(Vector<AppHMIType> val)
	    	{ vrAppHMITypes = val; return this; }
	    public Builder autoActivateID(String val)
	    	{ autoActivateID = val; return this; }
	    public Builder callbackToUIThread(boolean val)
	    	{ callbackToUIThread = val; return this; }
	    public Builder preRegister(boolean val)
	    	{ preRegister = val; return this; }
	    public Builder sAppResumeHash(String val)
	    	{ sAppResumeHash = val; return this; }
	    public Builder mTransport(BaseTransportConfig val)
	    	{ mTransport = val; return this; }
	        
        public SdlProxyALM buildProxy() throws SdlException
        {
        	SdlProxyBuilder obj = new SdlProxyBuilder(this);
        	return new SdlProxyALM(obj.service,obj.listener,obj.sdlProxyConfigurationResources,obj.appName,obj.ttsChunks,obj.sNgnAppName,obj.vrSynonyms,obj.isMediaApp,obj.sdlMessageVersion,obj.lang,obj.hmiLang,obj.vrAppHMITypes,obj.appId,obj.autoActivateID,obj.callbackToUIThread,obj.preRegister,obj.sAppResumeHash,obj.mTransport);
        }
	}

	private SdlProxyBuilder(Builder builder)
	{
		listener   = builder.listener;
		appId 	   = builder.appId;
		appName	   = builder.appName;
		isMediaApp = builder.isMediaApp;

		service = builder.service;
		sdlProxyConfigurationResources = builder.sdlProxyConfigurationResources;
		ttsChunks = builder.ttsChunks;
		sNgnAppName = builder.sNgnAppName;
		vrSynonyms = builder.vrSynonyms;
		sdlMessageVersion = builder.sdlMessageVersion;
		lang = builder.lang;
		hmiLang = builder.hmiLang;
		vrAppHMITypes = builder.vrAppHMITypes;
		autoActivateID = builder.autoActivateID;
		callbackToUIThread = builder.callbackToUIThread;
		preRegister = builder.preRegister;
		sAppResumeHash = builder.sAppResumeHash;
		mTransport = builder.mTransport;
	}
}

