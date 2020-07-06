package com.smartdevicelink.test.rpc.responses;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.GetSystemCapabilityResponse;
import com.smartdevicelink.proxy.rpc.SystemCapability;
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
import static android.support.test.InstrumentationRegistry.getTargetContext;

public class GetSystemCapabilityResponseTests extends BaseRpcTests {

    @Override
    protected RPCMessage createMessage(){
        GetSystemCapabilityResponse msg = new GetSystemCapabilityResponse();

        msg.setSystemCapability(TestValues.GENERAL_SYSTEMCAPABILITY);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_RESPONSE;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.GET_SYSTEM_CAPABILITY.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(GetSystemCapabilityResponse.KEY_SYSTEM_CAPABILITY, JsonRPCMarshaller.serializeHashtable(TestValues.GENERAL_SYSTEMCAPABILITY.getStore()));
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
        SystemCapability testCapability = ( (GetSystemCapabilityResponse) msg ).getSystemCapability();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_SYSTEMCAPABILITY.getSystemCapabilityType(), testCapability.getSystemCapabilityType());

        // Invalid/Null Tests
        GetSystemCapabilityResponse msg = new GetSystemCapabilityResponse();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getSystemCapability());
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
            GetSystemCapabilityResponse cmd = new GetSystemCapabilityResponse(hash);

            JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
            assertNotNull(TestValues.NOT_NULL, body);

            // Test everything in the json body.
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
            assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

            JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);

            SystemCapability testCapability = new SystemCapability(JsonRPCMarshaller.deserializeJSONObject((JSONObject) JsonUtils.readObjectFromJsonObject(parameters, GetSystemCapabilityResponse.KEY_SYSTEM_CAPABILITY)));
            SystemCapability cmdCapability = cmd.getSystemCapability();
            assertEquals(TestValues.MATCH, testCapability.getSystemCapabilityType(), cmdCapability.getSystemCapabilityType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
