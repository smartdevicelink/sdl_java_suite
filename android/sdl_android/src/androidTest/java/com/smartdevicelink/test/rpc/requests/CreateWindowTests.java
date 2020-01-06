package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.CreateWindow;
import com.smartdevicelink.proxy.rpc.enums.WindowType;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
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

        msg.setWindowID(Test.GENERAL_INT);
        msg.setWindowName(Test.GENERAL_STRING);
        msg.setType(Test.GENERAL_WINDOWTYPE);
        msg.setAssociatedServiceType(Test.GENERAL_STRING);
        msg.setDuplicateUpdatesFromWindowID(Test.GENERAL_INT);

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
            result.put(CreateWindow.KEY_WINDOW_ID, Test.GENERAL_INT);
            result.put(CreateWindow.KEY_WINDOW_NAME, Test.GENERAL_STRING);
            result.put(CreateWindow.KEY_TYPE, Test.GENERAL_WINDOWTYPE);
            result.put(CreateWindow.KEY_ASSOCIATED_SERVICE_TYPE, Test.GENERAL_STRING);
            result.put(CreateWindow.KEY_DUPLICATE_UPDATES_FROM_WINDOW_ID, Test.GENERAL_INT);
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
        int testWindowID = ((CreateWindow) msg).getWindowID();
        String testWindowName = ((CreateWindow) msg).getWindowName();
        WindowType testType = ((CreateWindow) msg).getType();
        String testAssociatedServiceType = ((CreateWindow) msg).getAssociatedServiceType();
        int testDuplicateUpdatesFromWindowID = ((CreateWindow) msg).getDuplicateUpdatesFromWindowID();

        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_INT, testWindowID);
        assertEquals(Test.MATCH, Test.GENERAL_STRING, testWindowName);
        assertEquals(Test.MATCH, Test.GENERAL_WINDOWTYPE, testType);
        assertEquals(Test.MATCH, Test.GENERAL_STRING, testAssociatedServiceType);
        assertEquals(Test.MATCH, Test.GENERAL_INT, testDuplicateUpdatesFromWindowID);

        // Invalid/Null Tests
        CreateWindow msg = new CreateWindow();
        assertNotNull(Test.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(Test.NULL, msg.getWindowID());
        assertNull(Test.NULL, msg.getWindowName());
        assertNull(Test.NULL, msg.getType());
        assertNull(Test.NULL, msg.getAssociatedServiceType());
        assertNull(Test.NULL, msg.getDuplicateUpdatesFromWindowID());
    }

    /**
     * Tests a valid JSON construction of this RPC message.
     */
    public void testJsonConstructor() {
        JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
        assertNotNull(Test.NOT_NULL, commandJson);

        try {
            Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
            CreateWindow cmd = new CreateWindow(hash);

            JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
            assertNotNull(Test.NOT_NULL, body);

            // Test everything in the json body.
            assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
            assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

            JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
            assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, CreateWindow.KEY_WINDOW_ID), cmd.getWindowID());
            assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(parameters, CreateWindow.KEY_WINDOW_NAME).toString(), cmd.getWindowName().toString());
            assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(parameters, CreateWindow.KEY_TYPE).toString(), cmd.getType().toString());
            assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(parameters, CreateWindow.KEY_ASSOCIATED_SERVICE_TYPE).toString(), cmd.getAssociatedServiceType().toString());
            assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, CreateWindow.KEY_DUPLICATE_UPDATES_FROM_WINDOW_ID), cmd.getDuplicateUpdatesFromWindowID());
        } catch (JSONException e) {
            fail(Test.JSON_FAIL);
        }
    }
}