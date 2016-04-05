package com.smartdevicelink.api;

import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.proxy.SdlProxyALM;
import com.smartdevicelink.proxy.SdlProxyBuilder;
import com.smartdevicelink.proxy.SdlProxyConfigurationResources;
import com.smartdevicelink.proxy.interfaces.IProxyListenerALM;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.enums.AppHMIType;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.transport.BTTransportConfig;
import com.smartdevicelink.transport.BaseTransportConfig;

import java.util.Vector;

public class SdlApplicationConfig {

    // Required parameters
    private String mAppId;
    private String mAppName;
    private Boolean isMediaApp;
    private Class<? extends SdlActivity> mMainSdlActivity;

    // Optional parameters - initialized to default values
    private SdlProxyConfigurationResources mSdlProxyConfigurationResources;
    private Vector<TTSChunk> mTTSChunks;
    private String mShortAppName;
    private Vector<String> mVrSynonyms;
    private Language mLang;
    private Language mHmiLang;
    private Vector<AppHMIType> mVrAppHMITypes;
    private String mAutoActivateID;
    private BaseTransportConfig mTransport ;

    // Handled internally
    private boolean isCallbackToUIThread = false;
    private boolean isPreRegister = false;
    private SdlMsgVersion mSdlMsgVersion = null;


    private SdlApplicationConfig(SdlApplicationConfig.Builder builder){
        this.mAppId = builder.appId;
        this.mAppName = builder.appName;
        this.isMediaApp = builder.isMediaApp;
        this.mMainSdlActivity = builder.mainSdlActivity;

        this.mSdlProxyConfigurationResources = builder.mSdlProxyConfigurationResources;
        this.mTTSChunks = builder.ttsChunks;
        this.mShortAppName = builder.shortAppName;
        this.mVrSynonyms = builder.vrSynonyms;
        this.mLang = builder.language;
        this.mHmiLang = builder.hmiLanguage;
        this.mVrAppHMITypes = builder.vrAppHMITypes;
        this.mAutoActivateID = builder.autoActivateID;
        this.mTransport = builder.transport;

    }

    String getAppId(){
        return mAppId;
    }

    Class<? extends SdlActivity> getMainSdlActivity(){
        return mMainSdlActivity;
    }

    SdlProxyALM buildProxy(SdlApplication sdlApplication, String resumeHash, IProxyListenerALM listener){
        SdlProxyBuilder.Builder builder = new SdlProxyBuilder.Builder(listener, mAppId, mAppName, isMediaApp);
        builder.setService(sdlApplication)
                .setAppResumeDataHash(resumeHash)
                .setTransportType(mTransport)
                .setAutoActivateID(mAutoActivateID)
                .setCallbackToUIThread(isCallbackToUIThread)
                .setPreRegister(isPreRegister)
                .setSdlMessageVersion(mSdlMsgVersion)
                .setSdlProxyConfigurationResources(mSdlProxyConfigurationResources)
                .setShortAppName(mShortAppName)
                .setVrSynonyms(mVrSynonyms)
                .setVrAppHMITypes(mVrAppHMITypes)
                .setTtsName(mTTSChunks)
                .setLangDesired(mLang)
                .setHMILangDesired(mHmiLang);
        try {
            return builder.build();
        } catch (SdlException e) {
            // TODO: Add better logging.
            e.printStackTrace();
            return null;
        }
    }

    public void setIsCallbackToUIThread(boolean isCallbackToUIThread) {
        this.isCallbackToUIThread = isCallbackToUIThread;
    }

    public void setIsPreRegister(boolean isPreRegister) {
        this.isPreRegister = isPreRegister;
    }

    public void setSdlMsgVersion(SdlMsgVersion sdlMsgVersion) {
        mSdlMsgVersion = sdlMsgVersion;
    }

    public class Builder{
        // Required parameters
        String appId;
        String appName;
        boolean isMediaApp;
        Class<? extends SdlActivity> mainSdlActivity;

        // Optional parameters - initialized to default values
        private SdlProxyConfigurationResources mSdlProxyConfigurationResources = null;
        private Vector<TTSChunk> ttsChunks = null;
        private String shortAppName = null;
        private Vector<String> vrSynonyms = null;
        private Language language = Language.EN_US;
        private Language hmiLanguage = Language.EN_US;
        private Vector<AppHMIType> vrAppHMITypes = null;
        private String autoActivateID = null;
        private BaseTransportConfig transport = new BTTransportConfig();

        public Builder(String appId, String appName, boolean isMedia,
                       Class<? extends SdlActivity> mainSdlActivity){
            this.appId = appId;
            this.appName = appName;
            this.isMediaApp = isMedia;
            this.mainSdlActivity = mainSdlActivity;
        }

        SdlApplicationConfig build(){
            return new SdlApplicationConfig(this);
        }

        public void setSdlProxyConfigurationResources(SdlProxyConfigurationResources sdlProxyConfigurationResources) {
            mSdlProxyConfigurationResources = sdlProxyConfigurationResources;
        }

        public void setTtsChunks(Vector<TTSChunk> ttsChunks) {
            this.ttsChunks = ttsChunks;
        }

        public void setShortAppName(String shortAppName) {
            this.shortAppName = shortAppName;
        }

        public void setVrSynonyms(Vector<String> vrSynonyms) {
            this.vrSynonyms = vrSynonyms;
        }

        public void setLanguage(Language language) {
            this.language = language;
        }

        public void setHmiLanguage(Language hmiLanguage) {
            this.hmiLanguage = hmiLanguage;
        }

        public void setVrAppHMITypes(Vector<AppHMIType> vrAppHMITypes) {
            this.vrAppHMITypes = vrAppHMITypes;
        }

        public void setAutoActivateID(String autoActivateID) {
            this.autoActivateID = autoActivateID;
        }

        public void setTransport(BaseTransportConfig transport) {
            this.transport = transport;
        }
    }
}
