package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.SetCloudAppProperties;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.Test;

import org.json.JSONException;
import org.json.JSONObject;

public class SetCloudAppPropertiesTests extends BaseRpcTests {

    @Override
    protected RPCMessage createMessage(){
        SetCloudAppProperties msg = new SetCloudAppProperties();

        msg.setProperties(Test.GENERAL_CLOUDAPPPROPERTIES);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_REQUEST;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.SET_CLOUD_APP_PROPERTIES.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(SetCloudAppProperties.KEY_PROPERTIES, Test.GENERAL_CLOUDAPPPROPERTIES.serializeJSON());
        }catch(JSONException e){
            fail(Test.JSON_FAIL);
        }

        return result;
    }

}
