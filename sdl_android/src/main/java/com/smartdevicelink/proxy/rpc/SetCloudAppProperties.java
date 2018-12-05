package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.HybridAppPreference;

import java.util.Hashtable;

public class SetCloudAppProperties extends RPCRequest {

    public static final String KEY_APP_NAME                 = "appName";
    public static final String KEY_APP_ID                   = "appId";
    public static final String KEY_ENABLED                  = "enabled";
    public static final String KEY_CLOUD_APP_AUTH_TOKEN     = "cloudAppAuthToken";
    public static final String KEY_CLOUD_TRANSPORT_TYPE     = "cloudTransportType";
    public static final String KEY_HYBRID_APP_PREFERENCE    = "hybridAppPreference";



    public SetCloudAppProperties(){
        super(FunctionID.SET_CLOUD_APP_PROPERTIES.toString());
    }

    public SetCloudAppProperties(Hashtable<String, Object> hash) {
        super(hash);
    }

    public SetCloudAppProperties(String appName, String appId){
        this();
        setParameters(KEY_APP_NAME, appName);
        setParameters(KEY_APP_ID, appId);
    }

    public void setAppName(String appName){
        setParameters(KEY_APP_NAME, appName);
    }

    public String getAppName(){
        return getString((KEY_APP_NAME));
    }

    public void setAppId(String appId){
        setParameters(KEY_APP_ID, appId);
    }

    public String getAppId(){
        return getString((KEY_APP_ID));
    }

    public void setEnabled(boolean enabled){
        setParameters(KEY_ENABLED, enabled);
    }

    public Boolean isEnabled(){
        return getBoolean((KEY_ENABLED));
    }

    public void setCloudAppAuthToken(String token){
        setParameters(KEY_CLOUD_APP_AUTH_TOKEN, token);
    }

    public String getCloudAppAuthToken(){
        return getString((KEY_CLOUD_APP_AUTH_TOKEN));
    }

    public void setCloudTransportType(String transportType){
        setParameters(KEY_CLOUD_TRANSPORT_TYPE, transportType);
    }

    public String getCloudTransportType(){
        return getString((KEY_CLOUD_TRANSPORT_TYPE));
    }

    public void setHybridAppPreference(HybridAppPreference hybridAppPreference){
        setParameters(KEY_HYBRID_APP_PREFERENCE, hybridAppPreference);
    }

    public HybridAppPreference getHybridAppPreference(){
        return (HybridAppPreference)getObject(HybridAppPreference.class, KEY_HYBRID_APP_PREFERENCE);
    }

}
