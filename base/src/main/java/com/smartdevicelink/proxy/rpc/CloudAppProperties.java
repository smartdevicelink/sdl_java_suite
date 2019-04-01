/*
 * Copyright (c) 2019 Livio, Inc.
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
 * Neither the name of the Livio Inc. nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
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
package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.HybridAppPreference;

import java.util.Hashtable;
import java.util.List;

/**
 * Properties that relate to a a cloud app entry.
 */
public class CloudAppProperties extends RPCStruct {
    public static final String KEY_NICKNAMES                = "nicknames";
    public static final String KEY_APP_ID                   = "appID";
    public static final String KEY_ENABLED                  = "enabled";
    public static final String KEY_AUTH_TOKEN               = "authToken";
    public static final String KEY_CLOUD_TRANSPORT_TYPE     = "cloudTransportType";
    public static final String KEY_HYBRID_APP_PREFERENCE    = "hybridAppPreference";
    public static final String KEY_ENDPOINT                 = "endpoint";


    public CloudAppProperties(){}

    public CloudAppProperties(Hashtable<String, Object> hash) {
        super(hash);
    }

    public CloudAppProperties(@NonNull String appID){
        this();
        setValue(KEY_APP_ID, appID);
    }

    public void setNicknames(List<String> nicknames){
        setValue(KEY_NICKNAMES, nicknames);
    }

    @SuppressWarnings("unchecked")
    public List<String> getNicknames(){
        return (List<String>) getObject(String.class, KEY_NICKNAMES);
    }

    public void setAppID(@NonNull String appID){
        setValue(KEY_APP_ID, appID);
    }

    public String getAppID(){
        return getString(KEY_APP_ID);
    }

    /**
     * If true, this cloud app entry will designate it should appear in the HMI
     * @param enabled if the app should be
     */
    public void setEnabled(boolean enabled){
        setValue(KEY_ENABLED, enabled);
    }

    /**
     * @return if this cloud app entry will designate it should appear in the HMI
     */
    public Boolean isEnabled(){
        return getBoolean(KEY_ENABLED);
    }

    public void setAuthToken(String token){
        setValue(KEY_AUTH_TOKEN, token);
    }

    public String getAuthToken(){
        return getString(KEY_AUTH_TOKEN);
    }

    public void setCloudTransportType(String transportType){
        setValue(KEY_CLOUD_TRANSPORT_TYPE, transportType);
    }

    public String getCloudTransportType(){
        return getString(KEY_CLOUD_TRANSPORT_TYPE);
    }

    public void setHybridAppPreference(HybridAppPreference hybridAppPreference){
        setValue(KEY_HYBRID_APP_PREFERENCE, hybridAppPreference);
    }

    public HybridAppPreference getHybridAppPreference(){
        return (HybridAppPreference)getObject(HybridAppPreference.class, KEY_HYBRID_APP_PREFERENCE);
    }

    /**
     * @param token - max length ="65535"
     */
    public void setEndpoint(String token){
        setValue(KEY_ENDPOINT, token);
    }

    /**
     * @return token - max length ="65535"
     */
    public String getEndpoint(){
        return getString(KEY_ENDPOINT);
    }

}
