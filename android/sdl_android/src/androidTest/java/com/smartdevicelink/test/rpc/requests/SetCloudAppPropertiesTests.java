package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.SetCloudAppProperties;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.TestValues;

import org.json.JSONException;
import org.json.JSONObject;

import static junit.framework.TestCase.fail;

public class SetCloudAppPropertiesTests extends BaseRpcTests {

    @Override
    protected RPCMessage createMessage(){
        SetCloudAppProperties msg = new SetCloudAppProperties();

        msg.setProperties(TestValues.GENERAL_CLOUDAPPPROPERTIES);

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
            result.put(SetCloudAppProperties.KEY_PROPERTIES, TestValues.GENERAL_CLOUDAPPPROPERTIES.serializeJSON());
        }catch(JSONException e){
            fail(TestValues.JSON_FAIL);
        }

        return result;
    }

}
