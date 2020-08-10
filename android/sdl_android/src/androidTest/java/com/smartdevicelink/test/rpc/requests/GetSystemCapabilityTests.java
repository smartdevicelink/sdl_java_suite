package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.GetSystemCapability;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Hashtable;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.fail;
import static androidx.test.platform.app.InstrumentationRegistry.getTargetContext;

public class GetSystemCapabilityTests extends BaseRpcTests {

    @Override
    protected RPCMessage createMessage(){
        GetSystemCapability msg = new GetSystemCapability();

        msg.setSystemCapabilityType(TestValues.GENERAL_SYSTEMCAPABILITYTYPE);
        msg.setSubscribe(TestValues.GENERAL_BOOLEAN);

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
            result.put(GetSystemCapability.KEY_SYSTEM_CAPABILITY_TYPE, TestValues.GENERAL_SYSTEMCAPABILITYTYPE);
            result.put(GetSystemCapability.KEY_SUBSCRIBE, TestValues.GENERAL_BOOLEAN);
        }catch(JSONException e){
            fail(TestValues.JSON_FAIL);
        }

        return result;
    }

    /**
     * Tests the expected values of the RPC message.
     */
    @Test
    public void testRpcValues () {
        // Test Values
        SystemCapabilityType testType = ( (GetSystemCapability) msg ).getSystemCapabilityType();
        boolean testSubscribe = ( (GetSystemCapability) msg ).getSubscribe();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_SYSTEMCAPABILITYTYPE, testType);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, testSubscribe);

        // Invalid/Null Tests
        GetSystemCapability msg = new GetSystemCapability();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getSystemCapabilityType());
        assertNull(TestValues.NULL, msg.getSubscribe());
    }

    /**
     * Tests a valid JSON construction of this RPC message.
     */
    @Test
    public void testJsonConstructor () {
        JSONObject commandJson = JsonFileReader.readId(getTargetContext(), getCommandType(), getMessageType());
        assertNotNull(TestValues.NOT_NULL, commandJson);

        try {
            Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
            GetSystemCapability cmd = new GetSystemCapability(hash);

            JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
            assertNotNull(TestValues.NOT_NULL, body);

            // Test everything in the json body.
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
            assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

            JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);

            assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(parameters, GetSystemCapability.KEY_SYSTEM_CAPABILITY_TYPE).toString(), cmd.getSystemCapabilityType().toString());
            assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(parameters, GetSystemCapability.KEY_SUBSCRIBE), cmd.getSubscribe());
        }catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}