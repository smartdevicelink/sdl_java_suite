package com.smartdevicelink.lifecycle;

import com.smartdevicelink.proxy.SdlProxyConfigurationResources;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.enums.AppHMIType;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.transport.BTTransportConfig;
import com.smartdevicelink.transport.BaseTransportConfig;

import java.util.Vector;

public class SdlConnectionConfig {
    // Required handled by constructor
    String appName;
    String appID;

    // Optional set by setters
    boolean isMediaApp;
    SdlProxyConfigurationResources sdlProxyConfigurationResources;
    boolean enableAdvancedLifecycleManagement;
    Vector<TTSChunk> ttsName;
    String ngnMediaScreenAppName;
    Vector<String> vrSynonyms;
    SdlMsgVersion sdlMsgVersion;
    Language languageDesired;
    Language hmiDisplayLanguageDesired;
    Vector<AppHMIType> appType;
    String autoActivateID;
    boolean callbackToUIThread;
    boolean preRegister;
    String sHashID;
    Boolean bEnableResume;
    BaseTransportConfig transportConfig;

    Class<? extends SdlLifecycleService> serviceClass;

    public SdlConnectionConfig(String appID, String appName){
        this.appID = appID;
        this.appName = appName;
        this.isMediaApp = false;
        this.languageDesired = this.hmiDisplayLanguageDesired = Language.EN_US;

        this.enableAdvancedLifecycleManagement = false;
        this.transportConfig = new BTTransportConfig();
        this.serviceClass = SdlLifecycleService.class;
    }

    public void setIsMediaApp(boolean isMediaApp) {
        this.isMediaApp = isMediaApp;
    }

    public void setSdlProxyConfigurationResources(SdlProxyConfigurationResources sdlProxyConfigurationResources) {
        this.sdlProxyConfigurationResources = sdlProxyConfigurationResources;
    }

    public void setEnableAdvancedLifecycleManagement(boolean enableAdvancedLifecycleManagement) {
        this.enableAdvancedLifecycleManagement = enableAdvancedLifecycleManagement;
    }

    public void setTtsName(Vector<TTSChunk> ttsName) {
        this.ttsName = ttsName;
    }

    public void setShortAppName(String shortAppName) {
        this.ngnMediaScreenAppName = shortAppName;
    }

    public void setVrSynonyms(Vector<String> vrSynonyms) {
        this.vrSynonyms = vrSynonyms;
    }

    public void setSdlMsgVersion(SdlMsgVersion sdlMsgVersion) {
        this.sdlMsgVersion = sdlMsgVersion;
    }

    public void setTtsLanguageDesired(Language languageDesired) {
        this.languageDesired = languageDesired;
    }

    public void setHmiDisplayLanguageDesired(Language hmiDisplayLanguageDesired) {
        this.hmiDisplayLanguageDesired = hmiDisplayLanguageDesired;
    }

    public void setServiceClass(Class<? extends SdlLifecycleService> serviceClass) {
        this.serviceClass = serviceClass;
    }

    public void setAppType(Vector<AppHMIType> appType) {
        this.appType = appType;
    }

    public void setAutoActivateID(String autoActivateID) {
        this.autoActivateID = autoActivateID;
    }

    public void setCallbackToUIThread(boolean callbackToUIThread) {
        this.callbackToUIThread = callbackToUIThread;
    }

    public void setPreRegister(boolean preRegister) {
        this.preRegister = preRegister;
    }

    public void setsHashID(String sHashID) {
        this.sHashID = sHashID;
    }

    public void setbEnableResume(Boolean bEnableResume) {
        this.bEnableResume = bEnableResume;
    }

    public void setTransportConfig(BaseTransportConfig transportConfig) {
        this.transportConfig = transportConfig;
    }

}
