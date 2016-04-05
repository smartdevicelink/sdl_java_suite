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
    private String mAppId;
    private String mAppName;
    private Boolean isMediaApp;

    // Optional parameters - initialized to default values
    private SdlProxyConfigurationResources mSdlProxyConfigurationResources = null;
    private Vector<TTSChunk> mTTSChunks = null;
    private String mShortAppName = null;
    private Vector<String> mVrSynonyms = null;
    private Language mLang = Language.EN_US;
    private Language mHmiLang = Language.EN_US;
    private Vector<AppHMIType> mVrAppHMITypes = null;
    private String mAutoActivateID = null;
    private BaseTransportConfig mTransport = new BTTransportConfig();

    // Handled internally
    private IProxyListenerALM mProxyListenerALM;
    private Service mService = null;
    private String mAppResumeHash = null;
    private boolean isCallbackToUIThread = false;
    private boolean isPreRegister = false;
    private SdlMsgVersion mSdlMsgVersion = null;


    private SdlApplicationConfig(){

    }

    String getAppId(){
        return mAppId;
    }

    SdlProxyALM buildProxy(SdlApplication sdlApplication, String resumeHash, IProxyListenerALM listener){
        SdlProxyBuilder.Builder builder = new SdlProxyBuilder.Builder(listener, mAppId, mAppName, isMediaApp);
        builder.setService(sdlApplication)
                .setTtsName(mTTSChunks)
                .setAppResumeDataHash(resumeHash)
                .setLangDesired(mLang)
                .setHMILangDesired(mHmiLang)
                .setVrAppHMITypes(mVrAppHMITypes)
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
