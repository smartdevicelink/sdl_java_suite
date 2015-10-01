package com.smartdevicelink.lifecycle;

import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.proxy.SdlProxyBase;
import com.smartdevicelink.proxy.interfaces.IProxyListenerBase;

class SdlProxyLCM extends SdlProxyBase<IProxyListenerBase>{

    SdlProxyLCM(SdlConnectionConfig config) throws SdlException{
        super(config.listener,
                config.sdlProxyConfigurationResources,
                config.enableAdvancedLifecycleManagement,
                config.appName,
                config.ttsName,
                config.ngnMediaScreenAppName,
                config.vrSynonyms,
                config.isMediaApp,
                config.sdlMsgVersion,
                config.languageDesired,
                config.hmiDisplayLanguageDesired,
                config.appType,
                config.appID,
                config.autoActivateID,
                config.callbackToUIThread,
                config.preRegister,
                config.sHashID,
                config.bEnableResume,
                config.transportConfig);
    }

}
