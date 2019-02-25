package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

import java.util.Hashtable;

public class GetCloudAppProperties extends RPCRequest {

    public static final String KEY_APP_ID                   = "appID";

    public GetCloudAppProperties() {
        super(FunctionID.SET_CLOUD_APP_PROPERTIES.toString());
    }

    public GetCloudAppProperties(Hashtable<String, Object> hash) {
        super(hash);
    }

    public GetCloudAppProperties(@NonNull String appID){
        this();
        setParameters(KEY_APP_ID, appID);
    }

    public void setAppId(String appId){
        setParameters(KEY_APP_ID, appId);
    }

    public String getAppId(){
        return getString((KEY_APP_ID));
    }

}
