package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

import java.util.Hashtable;

public class GetCloudAppPropertiesResponse extends RPCResponse {

    public static final String KEY_PROPERTIES         = "properties";

    public GetCloudAppPropertiesResponse() {
        super(FunctionID.GET_CLOUD_APP_PROPERTIES.toString());
    }
    public GetCloudAppPropertiesResponse(Hashtable<String, Object> hash) {
        super(hash);
    }

    public void setCloudAppProperties(@NonNull CloudAppProperties cloudAppProperties){
        setParameters(KEY_PROPERTIES, cloudAppProperties);
    }

    public CloudAppProperties getCloudAppProperties(){
        return (CloudAppProperties) getObject(CloudAppProperties.class, KEY_PROPERTIES);
    }
}
