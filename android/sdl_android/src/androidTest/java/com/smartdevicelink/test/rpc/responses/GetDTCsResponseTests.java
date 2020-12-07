package com.smartdevicelink.test.rpc.responses;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.GetDTCsResponse;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Hashtable;
import java.util.List;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.GetDTCsResponse}
 */
public class GetDTCsResponseTests extends BaseRpcTests {

    @Override
    protected RPCMessage createMessage() {
        GetDTCsResponse msg = new GetDTCsResponse();

        msg.setDtc(TestValues.GENERAL_STRING_LIST);

        return msg;
    }

    @Override
    protected String getMessageType() {
        return RPCMessage.KEY_RESPONSE;
    }

    @Override
    protected String getCommandType() {
        return FunctionID.GET_DTCS.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion) {
        JSONObject result = new JSONObject();

        try {
            result.put(GetDTCsResponse.KEY_DTC, JsonUtils.createJsonArray(TestValues.GENERAL_STRING_LIST));
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }

        return result;
    }

    /**
     * Tests the expected values of the RPC message.
     */
    @Test
    public void testRpcValues() {
        // Test Values
        List<String> cmdId = ((GetDTCsResponse) msg).getDtc();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING_LIST.size(), cmdId.size());

        for (int i = 0; i < TestValues.GENERAL_STRING_LIST.size(); i++) {
            assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING_LIST.get(i), cmdId.get(i));
        }

        // Invalid/Null Tests
        GetDTCsResponse msg = new GetDTCsResponse();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getDtc());
    }

    /**
     * Tests a valid JSON construction of this RPC message.
     */
    @Test
    public void testJsonConstructor() {
        JSONObject commandJson = JsonFileReader.readId(getInstrumentation().getTargetContext(), getCommandType(), getMessageType());
        assertNotNull(TestValues.NOT_NULL, commandJson);

        try {
            Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
            GetDTCsResponse cmd = new GetDTCsResponse(hash);

            JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
            assertNotNull(TestValues.NOT_NULL, body);

            // Test everything in the json body.
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
            assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

            JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);

            List<String> dtcList = JsonUtils.readStringListFromJsonObject(parameters, GetDTCsResponse.KEY_DTC);
            List<String> testDtcList = cmd.getDtc();
            assertEquals(TestValues.MATCH, dtcList.size(), testDtcList.size());
            assertTrue(TestValues.TRUE, Validator.validateStringList(dtcList, testDtcList));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}