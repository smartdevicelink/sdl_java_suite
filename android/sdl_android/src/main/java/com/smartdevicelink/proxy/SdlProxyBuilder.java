/*
 * Copyright (c) 2017 - 2019, SmartDeviceLink Consortium, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the SmartDeviceLink Consortium, Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from this 
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.smartdevicelink.proxy;

import android.app.Service;
import android.content.Context;

import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.proxy.interfaces.IProxyListenerALM;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.TemplateColorScheme;
import com.smartdevicelink.proxy.rpc.enums.AppHMIType;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.security.SdlSecurityBase;
import com.smartdevicelink.transport.BTTransportConfig;
import com.smartdevicelink.transport.BaseTransportConfig;
import com.smartdevicelink.transport.MultiplexTransportConfig;

import java.util.List;
import java.util.Vector;

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
    private TemplateColorScheme dayColorScheme, nightColorScheme;

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
        dayColorScheme = null;
        nightColorScheme = null;    
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
 public Builder setDayColorScheme(TemplateColorScheme val) {
            sdlProxyBuilder.dayColorScheme = val;
            return this;
        }

        public Builder setNightColorScheme(TemplateColorScheme val) {
            sdlProxyBuilder.nightColorScheme = val;
            return this;
        }

        public SdlProxyALM build() throws SdlException {
            SdlProxyALM proxy = new SdlProxyALM(sdlProxyBuilder.service, sdlProxyBuilder.listener,
                    sdlProxyBuilder.sdlProxyConfigurationResources, sdlProxyBuilder.appName,
                    sdlProxyBuilder.ttsChunks, sdlProxyBuilder.sShortAppName, sdlProxyBuilder.vrSynonyms,
                    sdlProxyBuilder.isMediaApp, sdlProxyBuilder.sdlMessageVersion, sdlProxyBuilder.lang,
                    sdlProxyBuilder.hmiLang, sdlProxyBuilder.vrAppHMITypes, sdlProxyBuilder.appId,
                    sdlProxyBuilder.autoActivateID, sdlProxyBuilder.dayColorScheme, sdlProxyBuilder.nightColorScheme,
                    sdlProxyBuilder.callbackToUIThread, sdlProxyBuilder.preRegister,
                    sdlProxyBuilder.sAppResumeHash, sdlProxyBuilder.mTransport);
            proxy.setSdlSecurityClassList(sdlProxyBuilder.sdlSecList);
            return proxy;
        }
    }
}



