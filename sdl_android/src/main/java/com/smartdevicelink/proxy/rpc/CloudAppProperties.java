package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.HybridAppPreference;

import java.util.Hashtable;

/**
 * Properties that relate to a a cloud app entry.
 */
public class CloudAppProperties extends RPCStruct {
    public static final String KEY_APP_NAME                 = "appName";
    public static final String KEY_APP_ID                   = "appID";
    public static final String KEY_ENABLED                  = "enabled";
    public static final String KEY_APP_AUTH_TOKEN           = "appAuthToken";
    public static final String KEY_CLOUD_TRANSPORT_TYPE     = "cloudTransportType";
    public static final String KEY_HYBRID_APP_PREFERENCE    = "hybridAppPreference";
    public static final String KEY_ENDPOINT                 = "endpoint";


    public CloudAppProperties(){}

    public CloudAppProperties(Hashtable<String, Object> hash) {
        super(hash);
    }

    public CloudAppProperties(@NonNull String appName, @NonNull String appID){
        this();
        setValue(KEY_APP_NAME, appName);
        setValue(KEY_APP_ID, appID);
    }

    public void setAppName(String appName){
        setValue(KEY_APP_NAME, appName);
    }

    public String getAppName(){
        return getString((KEY_APP_NAME));
    }

    public void setAppID(String appID){
        setValue(KEY_APP_ID, appID);
    }

    public String getAppID(){
        return getString((KEY_APP_ID));
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
        return getBoolean((KEY_ENABLED));
    }

    public void setAppAuthToken(String token){
        setValue(KEY_APP_AUTH_TOKEN, token);
    }

    public String getAppAuthToken(){
        return getString((KEY_APP_AUTH_TOKEN));
    }

    public void setCloudTransportType(String transportType){
        setValue(KEY_CLOUD_TRANSPORT_TYPE, transportType);
    }

    public String getCloudTransportType(){
        return getString((KEY_CLOUD_TRANSPORT_TYPE));
    }

    public void setHybridAppPreference(HybridAppPreference hybridAppPreference){
        setValue(KEY_HYBRID_APP_PREFERENCE, hybridAppPreference);
    }

    public HybridAppPreference getHybridAppPreference(){
        return (HybridAppPreference)getObject(HybridAppPreference.class, KEY_HYBRID_APP_PREFERENCE);
    }



}
