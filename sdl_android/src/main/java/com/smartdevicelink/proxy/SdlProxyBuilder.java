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

public class SdlProxyBuilder {
    // Required parameters
    private IProxyListenerALM listener;
    private String appId;
    private String appName;
    private Boolean isMediaApp;
    private Context context;
    private BaseTransportConfig mTransport;

    // Optional parameters
    private Service service;
    private SdlProxyConfigurationResources sdlProxyConfigurationResources;
    private Vector<TTSChunk> ttsChunks;
    private String sShortAppName;
    private Vector<String> vrSynonyms;
    private SdlMsgVersion sdlMessageVersion;
    private Language lang;
    private Language hmiLang;
    private Vector<AppHMIType> vrAppHMITypes;
    private String autoActivateID;
    private boolean callbackToUIThread;
    private boolean preRegister;
    private String sAppResumeHash;
    private List<Class<? extends SdlSecurityBase>> sdlSecList;

    private SdlProxyBuilder() {
        service = null;
        sdlProxyConfigurationResources = null;
        ttsChunks = null;
        sShortAppName = null;
        vrSynonyms = null;
        sdlMessageVersion = null;
        lang = Language.EN_US;
        hmiLang = Language.EN_US;
        vrAppHMITypes = null;
        autoActivateID = null;
        callbackToUIThread = false;
        preRegister = false;
        sAppResumeHash = null;
        sdlSecList = null;
    }

    public static class Builder {
        SdlProxyBuilder sdlProxyBuilder;

        /**
         * @deprecated Use Builder(IProxyListenerALM, String, String, Boolean, Context) instead
         */
        @Deprecated
        public Builder(IProxyListenerALM listener, String appId, String appName, Boolean isMediaApp) {
            sdlProxyBuilder = new SdlProxyBuilder();
            sdlProxyBuilder.listener = listener;
            sdlProxyBuilder.appId = appId;
            sdlProxyBuilder.appName = appName;
            sdlProxyBuilder.isMediaApp = isMediaApp;
            sdlProxyBuilder.mTransport = new BTTransportConfig();
        }

        public Builder(IProxyListenerALM listener, String appId, String appName, Boolean isMediaApp, Context context) {
            sdlProxyBuilder = new SdlProxyBuilder();
            sdlProxyBuilder.listener = listener;
            sdlProxyBuilder.appId = appId;
            sdlProxyBuilder.appName = appName;
            sdlProxyBuilder.isMediaApp = isMediaApp;
            sdlProxyBuilder.context = context;
            sdlProxyBuilder.mTransport = new MultiplexTransportConfig(context, appId);
        }

        public Builder setService(Service val) {
            sdlProxyBuilder.service = val;
            return this;
        }

        public Builder setSdlProxyConfigurationResources(SdlProxyConfigurationResources val) {
            sdlProxyBuilder.sdlProxyConfigurationResources = val;
            return this;
        }

        public Builder setTtsName(Vector<TTSChunk> val) {
            sdlProxyBuilder.ttsChunks = val;
            return this;
        }

        public Builder setShortAppName(String val) {
            sdlProxyBuilder.sShortAppName = val;
            return this;
        }

        public Builder setVrSynonyms(Vector<String> val) {
            sdlProxyBuilder.vrSynonyms = val;
            return this;
        }

        public Builder setSdlMessageVersion(SdlMsgVersion val) {
            sdlProxyBuilder.sdlMessageVersion = val;
            return this;
        }

        public Builder setLangDesired(Language val) {
            sdlProxyBuilder.lang = val;
            return this;
        }

        public Builder setHMILangDesired(Language val) {
            sdlProxyBuilder.hmiLang = val;
            return this;
        }

        public Builder setVrAppHMITypes(Vector<AppHMIType> val) {
            sdlProxyBuilder.vrAppHMITypes = val;
            return this;
        }

        public Builder setAutoActivateID(String val) {
            sdlProxyBuilder.autoActivateID = val;
            return this;
        }

        public Builder setCallbackToUIThread(boolean val) {
            sdlProxyBuilder.callbackToUIThread = val;
            return this;
        }

        public Builder setPreRegister(boolean val) {
            sdlProxyBuilder.preRegister = val;
            return this;
        }

        public Builder setAppResumeDataHash(String val) {
            sdlProxyBuilder.sAppResumeHash = val;
            return this;
        }

        public Builder setTransportType(BaseTransportConfig val) {
            sdlProxyBuilder.mTransport = val;
            return this;
        }

        public Builder setSdlSecurity(List<Class<? extends SdlSecurityBase>> val) {
            sdlProxyBuilder.sdlSecList = val;
            return this;
        }

        public SdlProxyALM build() throws SdlException {
            SdlProxyALM proxy = new SdlProxyALM(sdlProxyBuilder.service, sdlProxyBuilder.listener,
                    sdlProxyBuilder.sdlProxyConfigurationResources, sdlProxyBuilder.appName,
                    sdlProxyBuilder.ttsChunks, sdlProxyBuilder.sShortAppName, sdlProxyBuilder.vrSynonyms,
                    sdlProxyBuilder.isMediaApp, sdlProxyBuilder.sdlMessageVersion, sdlProxyBuilder.lang,
                    sdlProxyBuilder.hmiLang, sdlProxyBuilder.vrAppHMITypes, sdlProxyBuilder.appId,
                    sdlProxyBuilder.autoActivateID, sdlProxyBuilder.callbackToUIThread, sdlProxyBuilder.preRegister,
                    sdlProxyBuilder.sAppResumeHash, sdlProxyBuilder.mTransport);
            proxy.setSdlSecurityClassList(sdlProxyBuilder.sdlSecList);
            return proxy;
        }
    }
}

