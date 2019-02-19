package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.GetSystemCapability;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

public class GetSystemCapabilityTests extends BaseRpcTests {

    @Override
    protected RPCMessage createMessage(){
        GetSystemCapability msg = new GetSystemCapability();

        msg.setSystemCapabilityType(Test.GENERAL_SYSTEMCAPABILITYTYPE);
        msg.setSubscribe(Test.GENERAL_BOOLEAN);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_REQUEST;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.GET_SYSTEM_CAPABILITY.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(GetSystemCapability.KEY_SYSTEM_CAPABILITY_TYPE, Test.GENERAL_SYSTEMCAPABILITYTYPE);
            result.put(GetSystemCapability.KEY_SUBSCRIBE, Test.GENERAL_BOOLEAN);
        }catch(JSONException e){
            fail(Test.JSON_FAIL);
        }

        return result;
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues () {
        // Test Values
        SystemCapabilityType testType = ( (GetSystemCapability) msg ).getSystemCapabilityType();
        boolean testSubscribe = ( (GetSystemCapability) msg ).getSubscribe();

        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_SYSTEMCAPABILITYTYPE, testType);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, testSubscribe);

        // Invalid/Null Tests
        GetSystemCapability msg = new GetSystemCapability();
        assertNotNull(Test.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(Test.NULL, msg.getSystemCapabilityType());
        assertNull(Test.NULL, msg.getSubscribe());
    }

    /**
     * Tests a valid JSON construction of this RPC message.
     */
    public void testJsonConstructor () {
        JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
        assertNotNull(Test.NOT_NULL, commandJson);

        try {
            Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
            GetSystemCapability cmd = new GetSystemCapability(hash);

            JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
            assertNotNull(Test.NOT_NULL, body);

            // Test everything in the json body.
            assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
            assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

            JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);

            assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(parameters, GetSystemCapability.KEY_SYSTEM_CAPABILITY_TYPE).toString(), cmd.getSystemCapabilityType().toString());
            assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(parameters, GetSystemCapability.KEY_SUBSCRIBE), cmd.getSubscribe());
        }catch (JSONException e) {
            fail(Test.JSON_FAIL);
        }
    }
}