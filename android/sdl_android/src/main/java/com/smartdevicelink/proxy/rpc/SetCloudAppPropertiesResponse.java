package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

import java.util.Hashtable;

public class SetCloudAppPropertiesResponse extends RPCResponse {

    public SetCloudAppPropertiesResponse(){
        super(FunctionID.SET_CLOUD_APP_PROPERTIES.toString());
    }
    public SetCloudAppPropertiesResponse(Hashtable<String, Object> hash) {
        super(hash);
    }


}
