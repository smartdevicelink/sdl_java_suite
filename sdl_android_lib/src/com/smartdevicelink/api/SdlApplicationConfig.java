package com.smartdevicelink.api;

import android.app.Service;

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
    private String appId;
    private String appName;
    private Boolean isMediaApp;

    // Optional parameters - initialized to default values
    private SdlProxyConfigurationResources sdlProxyConfigurationResources = null;
    private Vector<TTSChunk> ttsChunks = null;
    private String sShortAppName = null;
    private Vector<String>vrSynonyms = null;
    private Language lang = Language.EN_US;
    private Language hmiLang = Language.EN_US;
    private Vector<AppHMIType> vrAppHMITypes = null;
    private String autoActivateID = null;
    private BaseTransportConfig mTransport = new BTTransportConfig();

    // Handled internally
    private IProxyListenerALM listener;
    private Service service = null;
    private String sAppResumeHash = null;
    private boolean callbackToUIThread = false;
    private boolean preRegister = false;
    private SdlMsgVersion sdlMessageVersion = null;


    private SdlApplicationConfig(){

    }

    SdlProxyALM buildProxy(SdlApplication sdlApplication, String resumeHash, IProxyListenerALM listener){
        SdlProxyBuilder.Builder builder = new SdlProxyBuilder.Builder(listener, appId, appName, isMediaApp);
        builder.setService(sdlApplication)
                .setTtsName(ttsChunks)
                .setAppResumeDataHash(resumeHash)
                .setLangDesired(lang)
                .setHMILangDesired(hmiLang)
                .setVrAppHMITypes(vrAppHMITypes)
                .setTransportType(mTransport);
        try {
            return builder.build();
        } catch (SdlException e) {
            // TODO: Add better logging.
            e.printStackTrace();
            return null;
        }
    }

    public class Builder{

        public Builder(){

        }

        SdlApplicationConfig build(){
            return new SdlApplicationConfig();
        }
    }
}
