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
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.rpc.ButtonPress}
 */
public class ButtonPressTests extends BaseRpcTests {

    @Override
    protected RPCMessage createMessage(){
        ButtonPress msg = new ButtonPress();

        msg.setModuleType(Test.GENERAL_MODULETYPE);
        msg.setButtonPressMode(Test.GENERAL_BUTTONPRESSMODE);
        msg.setButtonName(Test.GENERAL_BUTTONNAME);

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
            result.put(ButtonPress.KEY_MODULE_TYPE, Test.GENERAL_MODULETYPE);
            result.put(ButtonPress.KEY_BUTTON_NAME, Test.GENERAL_BUTTONNAME);
            result.put(ButtonPress.KEY_BUTTON_PRESS_MODE, Test.GENERAL_BUTTONPRESSMODE);
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
        ModuleType testModuleType = ( (ButtonPress) msg ).getModuleType();
        ButtonName testButtonName = ( (ButtonPress) msg ).getButtonName();
        ButtonPressMode testButtonPressMode = ( (ButtonPress) msg ).getButtonPressMode();

        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_MODULETYPE, testModuleType);
        assertEquals(Test.MATCH, Test.GENERAL_BUTTONNAME, testButtonName);
        assertEquals(Test.MATCH, Test.GENERAL_BUTTONPRESSMODE, testButtonPressMode);

        // Invalid/Null Tests
        ButtonPress msg = new ButtonPress();
        assertNotNull(Test.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(Test.NULL, msg.getModuleType());
        assertNull(Test.NULL, msg.getButtonName());
        assertNull(Test.NULL, msg.getButtonPressMode());
    }

    /**
     * Tests a valid JSON construction of this RPC message.
     */
    public void testJsonConstructor () {
        JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
        assertNotNull(Test.NOT_NULL, commandJson);

        try {
            Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
            ButtonPress cmd = new ButtonPress(hash);

            JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
            assertNotNull(Test.NOT_NULL, body);

            // Test everything in the json body.
            assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
            assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

            JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);

            assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(parameters, ButtonPress.KEY_MODULE_TYPE).toString(), cmd.getModuleType().toString());
            assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(parameters, ButtonPress.KEY_BUTTON_NAME).toString(), cmd.getButtonName().toString());
            assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(parameters, ButtonPress.KEY_BUTTON_PRESS_MODE).toString(), cmd.getButtonPressMode().toString());
        }catch (JSONException e) {
            fail(Test.JSON_FAIL);
        }
    }
}