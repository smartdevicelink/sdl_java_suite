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

        msg.setAppName(Test.GENERAL_STRING);
        msg.setAppId(Test.GENERAL_FULL_APP_ID);
        msg.setEnabled(Test.GENERAL_BOOLEAN);
        msg.setCloudAppAuthToken(Test.GENERAL_STRING);
        msg.setHybridAppPreference(Test.GENERAL_HYBRID_APP_PREFERENCE);

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
            result.put(SetCloudAppProperties.KEY_APP_NAME, Test.GENERAL_STRING);
            result.put(SetCloudAppProperties.KEY_APP_ID, Test.GENERAL_FULL_APP_ID);
            result.put(SetCloudAppProperties.KEY_ENABLED, Test.GENERAL_BOOLEAN);
            result.put(SetCloudAppProperties.KEY_CLOUD_APP_AUTH_TOKEN, Test.GENERAL_STRING);
            result.put(SetCloudAppProperties.KEY_HYBRID_APP_PREFERENCE, Test.GENERAL_HYBRID_APP_PREFERENCE);
        }catch(JSONException e){
            fail(Test.JSON_FAIL);
        }

        return result;
    }

}
