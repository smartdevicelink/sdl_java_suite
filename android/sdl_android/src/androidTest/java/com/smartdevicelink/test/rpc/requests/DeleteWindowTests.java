package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.DeleteWindow;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.DeleteWindow}
 */
public class DeleteWindowTests extends BaseRpcTests {

    @Override
    protected RPCMessage createMessage() {
        DeleteWindow msg = new DeleteWindow();

        msg.setWindowID(Test.GENERAL_INT);

        return msg;
    }

    @Override
    protected String getMessageType() {
        return RPCMessage.KEY_REQUEST;
    }

    @Override
    protected String getCommandType() {
        return FunctionID.DELETE_WINDOW.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion) {
        JSONObject result = new JSONObject();

        try {
            result.put(DeleteWindow.KEY_WINDOW_ID, Test.GENERAL_INT);
        } catch (JSONException e) {
            fail(Test.JSON_FAIL);
        }

        return result;
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values
        int testWindowID = ((DeleteWindow) msg).getWindowID();

        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_INT, testWindowID);

        // Invalid/Null Tests
        DeleteWindow msg = new DeleteWindow();
        assertNotNull(Test.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(Test.NULL, msg.getWindowID());
    }

    /**
     * Tests a valid JSON construction of this RPC message.
     */
    public void testJsonConstructor() {
        JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
        assertNotNull(Test.NOT_NULL, commandJson);

        try {
            Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
            DeleteWindow cmd = new DeleteWindow(hash);

            JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
            assertNotNull(Test.NOT_NULL, body);

            // Test everything in the json body.
            assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
            assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

            JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
            assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, DeleteWindow.KEY_WINDOW_ID), cmd.getWindowID());
        } catch (JSONException e) {
            fail(Test.JSON_FAIL);
        }
    }
}