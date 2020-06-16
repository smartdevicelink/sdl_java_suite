package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.CreateWindow;
import com.smartdevicelink.proxy.rpc.enums.WindowType;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.CreateWindow}
 */
public class CreateWindowTests extends BaseRpcTests {

    @Override
    protected RPCMessage createMessage() {
        CreateWindow msg = new CreateWindow();

        msg.setWindowID(TestValues.GENERAL_INT);
        msg.setWindowName(TestValues.GENERAL_STRING);
        msg.setType(TestValues.GENERAL_WINDOWTYPE);
        msg.setAssociatedServiceType(TestValues.GENERAL_STRING);
        msg.setDuplicateUpdatesFromWindowID(TestValues.GENERAL_INT);

        return msg;
    }

    @Override
    protected String getMessageType() {
        return RPCMessage.KEY_REQUEST;
    }

    @Override
    protected String getCommandType() {
        return FunctionID.CREATE_WINDOW.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion) {
        JSONObject result = new JSONObject();

        try {
            result.put(CreateWindow.KEY_WINDOW_ID, TestValues.GENERAL_INT);
            result.put(CreateWindow.KEY_WINDOW_NAME, TestValues.GENERAL_STRING);
            result.put(CreateWindow.KEY_TYPE, TestValues.GENERAL_WINDOWTYPE);
            result.put(CreateWindow.KEY_ASSOCIATED_SERVICE_TYPE, TestValues.GENERAL_STRING);
            result.put(CreateWindow.KEY_DUPLICATE_UPDATES_FROM_WINDOW_ID, TestValues.GENERAL_INT);
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }

        return result;
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values
        int testWindowID = ((CreateWindow) msg).getWindowID();
        String testWindowName = ((CreateWindow) msg).getWindowName();
        WindowType testType = ((CreateWindow) msg).getType();
        String testAssociatedServiceType = ((CreateWindow) msg).getAssociatedServiceType();
        int testDuplicateUpdatesFromWindowID = ((CreateWindow) msg).getDuplicateUpdatesFromWindowID();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, testWindowID);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testWindowName);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_WINDOWTYPE, testType);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testAssociatedServiceType);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, testDuplicateUpdatesFromWindowID);

        // Invalid/Null Tests
        CreateWindow msg = new CreateWindow();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getWindowID());
        assertNull(TestValues.NULL, msg.getWindowName());
        assertNull(TestValues.NULL, msg.getType());
        assertNull(TestValues.NULL, msg.getAssociatedServiceType());
        assertNull(TestValues.NULL, msg.getDuplicateUpdatesFromWindowID());
    }

    /**
     * Tests a valid JSON construction of this RPC message.
     */
    public void testJsonConstructor() {
        JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
        assertNotNull(TestValues.NOT_NULL, commandJson);

        try {
            Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
            CreateWindow cmd = new CreateWindow(hash);

            JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
            assertNotNull(TestValues.NOT_NULL, body);

            // Test everything in the json body.
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
            assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

            JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
            assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, CreateWindow.KEY_WINDOW_ID), cmd.getWindowID());
            assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(parameters, CreateWindow.KEY_WINDOW_NAME).toString(), cmd.getWindowName().toString());
            assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(parameters, CreateWindow.KEY_TYPE).toString(), cmd.getType().toString());
            assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(parameters, CreateWindow.KEY_ASSOCIATED_SERVICE_TYPE).toString(), cmd.getAssociatedServiceType().toString());
            assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, CreateWindow.KEY_DUPLICATE_UPDATES_FROM_WINDOW_ID), cmd.getDuplicateUpdatesFromWindowID());
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}