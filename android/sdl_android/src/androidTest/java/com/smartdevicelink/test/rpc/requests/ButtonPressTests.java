package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.ButtonPress;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.proxy.rpc.enums.ButtonPressMode;
import com.smartdevicelink.proxy.rpc.enums.ModuleType;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Hashtable;

import static android.support.test.InstrumentationRegistry.getContext;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.fail;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.rpc.ButtonPress}
 */
public class ButtonPressTests extends BaseRpcTests {

    @Override
    protected RPCMessage createMessage(){
        ButtonPress msg = new ButtonPress();

        msg.setModuleType(TestValues.GENERAL_MODULETYPE);
        msg.setButtonPressMode(TestValues.GENERAL_BUTTONPRESSMODE);
        msg.setButtonName(TestValues.GENERAL_BUTTONNAME);
        msg.setModuleId(TestValues.GENERAL_STRING);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_REQUEST;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.BUTTON_PRESS.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(ButtonPress.KEY_MODULE_TYPE, TestValues.GENERAL_MODULETYPE);
            result.put(ButtonPress.KEY_BUTTON_NAME, TestValues.GENERAL_BUTTONNAME);
            result.put(ButtonPress.KEY_BUTTON_PRESS_MODE, TestValues.GENERAL_BUTTONPRESSMODE);
            result.put(ButtonPress.KEY_MODULE_ID, TestValues.GENERAL_STRING);
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
        ModuleType testModuleType = ( (ButtonPress) msg ).getModuleType();
        ButtonName testButtonName = ( (ButtonPress) msg ).getButtonName();
        ButtonPressMode testButtonPressMode = ( (ButtonPress) msg ).getButtonPressMode();
        String testButtonId = ((ButtonPress) msg).getModuleId();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_MODULETYPE, testModuleType);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BUTTONNAME, testButtonName);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BUTTONPRESSMODE, testButtonPressMode);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testButtonId);

        // Invalid/Null Tests
        ButtonPress msg = new ButtonPress();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getModuleType());
        assertNull(TestValues.NULL, msg.getButtonName());
        assertNull(TestValues.NULL, msg.getButtonPressMode());
        assertNull(TestValues.NULL, msg.getModuleId());
    }

    /**
     * Tests a valid JSON construction of this RPC message.
     */
    @Test
    public void testJsonConstructor () {
        JSONObject commandJson = JsonFileReader.readId(getContext(), getCommandType(), getMessageType());
        assertNotNull(TestValues.NOT_NULL, commandJson);

        try {
            Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
            ButtonPress cmd = new ButtonPress(hash);

            JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
            assertNotNull(TestValues.NOT_NULL, body);

            // Test everything in the json body.
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
            assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

            JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);

            assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(parameters, ButtonPress.KEY_MODULE_TYPE).toString(), cmd.getModuleType().toString());
            assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(parameters, ButtonPress.KEY_BUTTON_NAME).toString(), cmd.getButtonName().toString());
            assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(parameters, ButtonPress.KEY_BUTTON_PRESS_MODE).toString(), cmd.getButtonPressMode().toString());
            assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(parameters, ButtonPress.KEY_MODULE_ID), cmd.getModuleId());
        }catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}