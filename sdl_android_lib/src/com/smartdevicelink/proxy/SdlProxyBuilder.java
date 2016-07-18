package com.smartdevicelink.proxy;

import java.util.List;
import java.util.Vector;

import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.proxy.interfaces.IProxyListenerALM;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.enums.AppHMIType;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.security.SdlSecurityBase;
import com.smartdevicelink.transport.BTTransportConfig;
import com.smartdevicelink.transport.BaseTransportConfig;
import com.smartdevicelink.transport.MultiplexTransportConfig;

import android.app.Service;
import android.content.Context;

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
	private String sShortAppName;
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
	private List<Class<? extends SdlSecurityBase>> sdlSecList;	
	
	public static class Builder
	{
		// Required parameters
	    private IProxyListenerALM listener;
	    private String appId;
	    private String appName;
	    private Boolean isMediaApp;
	    private Context context;

	    // Optional parameters - initialized to default values
	    private Service service = null;
	    private SdlProxyConfigurationResources sdlProxyConfigurationResources = null;
	    private Vector<TTSChunk> ttsChunks = null;
	    private String sShortAppName = null;
	    private Vector<String>vrSynonyms = null;
	    private SdlMsgVersion sdlMessageVersion = null;
	    private Language lang = Language.EN_US;
	    private Language hmiLang = Language.EN_US;
	    private Vector<AppHMIType> vrAppHMITypes = null;
	    private String autoActivateID = null;
	    private boolean callbackToUIThread = false;
	    private boolean preRegister = false;
	    private String sAppResumeHash = null;
	    private List<Class<? extends SdlSecurityBase>> sdlSecList = null;
	    private BaseTransportConfig mTransport; //Initialized in constructor
	    
	    /**
	     * @deprecated Use Builder(IProxyListenerALM, String, String, Boolean, Context) instead
	     */
	    @Deprecated
	    public Builder(IProxyListenerALM listener, String appId, String appName, Boolean isMediaApp)
	    {
	    	this.listener 		= listener;
	        this.appId    		= appId;
	        this.appName		= appName;
	        this.isMediaApp		= isMediaApp;
	        this.mTransport 	= new BTTransportConfig();
	    }
	    
	    public Builder(IProxyListenerALM listener, String appId, String appName, Boolean isMediaApp, Context context)
	    {
	    	this.listener 		= listener;
	        this.appId    		= appId;
	        this.appName		= appName;
	        this.isMediaApp		= isMediaApp;
	        this.context 		= context;
	        this.mTransport 	= new MultiplexTransportConfig(context, appId);
	    }
	    
	    public Builder setService(Service val)
	    	{ service = val; return this; }
	    public Builder setSdlProxyConfigurationResources(SdlProxyConfigurationResources val)
	    	{ sdlProxyConfigurationResources = val; return this; }
	    public Builder setTtsName(Vector<TTSChunk> val)
	    	{ ttsChunks = val; return this; }
	    public Builder setShortAppName(String val)
	    	{ sShortAppName = val; return this; }
	    public Builder setVrSynonyms(Vector<String> val)
	    	{ vrSynonyms = val; return this; }
	    public Builder setSdlMessageVersion(SdlMsgVersion val)
	    	{ sdlMessageVersion = val; return this; }
	    public Builder setLangDesired(Language val)
	    	{ lang = val; return this; }
	    public Builder setHMILangDesired(Language val)
	    	{ hmiLang = val; return this; }
	    public Builder setVrAppHMITypes(Vector<AppHMIType> val)
	    	{ vrAppHMITypes = val; return this; }
	    public Builder setAutoActivateID(String val)
	    	{ autoActivateID = val; return this; }
	    public Builder setCallbackToUIThread(boolean val)
	    	{ callbackToUIThread = val; return this; }
	    public Builder setPreRegister(boolean val)
	    	{ preRegister = val; return this; }
	    public Builder setAppResumeDataHash(String val)
	    	{ sAppResumeHash = val; return this; }
	    public Builder setTransportType(BaseTransportConfig val)
	    	{ mTransport = val; return this; }
	    public Builder setSdlSecurity(List<Class<? extends SdlSecurityBase>> val)
    		{ sdlSecList = val; return this; }
	        
        public SdlProxyALM build() throws SdlException
        {
        	SdlProxyBuilder obj = new SdlProxyBuilder(this);
        	SdlProxyALM proxy = new SdlProxyALM(obj.service,obj.listener,obj.sdlProxyConfigurationResources,obj.appName,obj.ttsChunks,obj.sShortAppName,obj.vrSynonyms,obj.isMediaApp,obj.sdlMessageVersion,obj.lang,obj.hmiLang,obj.vrAppHMITypes,obj.appId,obj.autoActivateID,obj.callbackToUIThread,obj.preRegister,obj.sAppResumeHash,obj.mTransport);
        	proxy.setSdlSecurityClassList(obj.sdlSecList);
        	return proxy;
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
		sShortAppName = builder.sShortAppName;
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
		sdlSecList = builder.sdlSecList;
	}
}

