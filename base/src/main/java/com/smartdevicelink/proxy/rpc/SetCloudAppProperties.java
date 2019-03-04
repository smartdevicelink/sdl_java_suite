package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

import java.util.Hashtable;

public class SetCloudAppProperties extends RPCRequest {

    public static final String KEY_PROPERTIES         = "properties";

    public SetCloudAppProperties(){
        super(FunctionID.SET_CLOUD_APP_PROPERTIES.toString());
    }

    public SetCloudAppProperties(Hashtable<String, Object> hash) {
        super(hash);
    }

    public SetCloudAppProperties(@NonNull CloudAppProperties cloudAppProperties){
        this();
        setParameters(KEY_PROPERTIES, cloudAppProperties);
    }

    public void setProperties(@NonNull CloudAppProperties cloudAppProperties){
        setParameters(KEY_PROPERTIES, cloudAppProperties);
    }

    public CloudAppProperties getProperties(){
        return (CloudAppProperties) getObject(CloudAppProperties.class, KEY_PROPERTIES);
    }


}
