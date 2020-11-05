package com.smartdevicelink.test.rpc.responses;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.DeleteFileResponse;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Hashtable;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.fail;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.DeleteFileResponse}
 */
public class DeleteFileResponseTests extends BaseRpcTests {

    @Override
    protected RPCMessage createMessage() {
        DeleteFileResponse msg = new DeleteFileResponse();

        msg.setSpaceAvailable(TestValues.GENERAL_INT);

        return msg;
    }

    @Override
    protected String getMessageType() {
        return RPCMessage.KEY_RESPONSE;
    }

    @Override
    protected String getCommandType() {
        return FunctionID.DELETE_FILE.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion) {
        JSONObject result = new JSONObject();

        try {
            result.put(DeleteFileResponse.KEY_SPACE_AVAILABLE, TestValues.GENERAL_INT);
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
        int spaceAvailable = ((DeleteFileResponse) msg).getSpaceAvailable();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, spaceAvailable);

        // Invalid/Null Tests
        DeleteFileResponse msg = new DeleteFileResponse();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getSpaceAvailable());
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
            DeleteFileResponse cmd = new DeleteFileResponse(hash);

            JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
            assertNotNull(TestValues.NOT_NULL, body);

            // Test everything in the json body.
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
            assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

            JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
            assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, DeleteFileResponse.KEY_SPACE_AVAILABLE), cmd.getSpaceAvailable());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}