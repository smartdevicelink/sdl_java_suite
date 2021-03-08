package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.AddCommand;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.MenuParams;
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
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertNull;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.AddCommand}
 */
public class AddCommandTests extends BaseRpcTests {

    @Override
    protected RPCMessage createMessage() {
        AddCommand msg = new AddCommand();

        msg.setCmdIcon(TestValues.GENERAL_IMAGE);
        msg.setMenuParams(TestValues.GENERAL_MENUPARAMS);
        msg.setVrCommands(TestValues.GENERAL_STRING_LIST);
        msg.setCmdID(TestValues.GENERAL_INT);
        msg.setSecondaryImage(TestValues.GENERAL_IMAGE);

        return msg;
    }

    @Override
    protected String getMessageType() {
        return RPCMessage.KEY_REQUEST;
    }

    @Override
    protected String getCommandType() {
        return FunctionID.ADD_COMMAND.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion) {
        JSONObject result = new JSONObject();

        try {
            result.put(AddCommand.KEY_CMD_ICON, TestValues.JSON_IMAGE);
            result.put(AddCommand.KEY_MENU_PARAMS, TestValues.JSON_MENUPARAMS);
            result.put(AddCommand.KEY_VR_COMMANDS, JsonUtils.createJsonArray(TestValues.GENERAL_STRING_LIST));
            result.put(AddCommand.KEY_CMD_ID, TestValues.GENERAL_INT);
            result.put(AddCommand.KEY_SECONDARY_IMAGE, TestValues.JSON_IMAGE);
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
        int testCmdId = ((AddCommand) msg).getCmdID();
        Image testImage = ((AddCommand) msg).getCmdIcon();
        MenuParams testMenuParams = ((AddCommand) msg).getMenuParams();
        List<String> testVrCommands = ((AddCommand) msg).getVrCommands();
        Image testSecondaryImage = ((AddCommand) msg).getSecondaryImage();

        // Valid Tests
        assertNotNull(TestValues.NOT_NULL, testMenuParams);
        assertNotNull(TestValues.NOT_NULL, testImage);
        assertNotNull(TestValues.NOT_NULL, testVrCommands);
        assertNotNull(TestValues.NOT_NULL, testSecondaryImage);

        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, testCmdId);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING_LIST.size(), testVrCommands.size());

        assertTrue(TestValues.TRUE, Validator.validateMenuParams(TestValues.GENERAL_MENUPARAMS, testMenuParams));
        assertTrue(TestValues.TRUE, Validator.validateImage(TestValues.GENERAL_IMAGE, testImage));
        assertTrue(TestValues.TRUE, Validator.validateStringList(TestValues.GENERAL_STRING_LIST, testVrCommands));
        assertTrue(TestValues.TRUE, Validator.validateImage(TestValues.GENERAL_IMAGE, testSecondaryImage));

        // Invalid/Null Tests
        AddCommand msg = new AddCommand();
        assertNotNull(TestValues.NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getCmdIcon());
        assertNull(TestValues.NULL, msg.getCmdID());
        assertNull(TestValues.NULL, msg.getMenuParams());
        assertNull(TestValues.NULL, msg.getVrCommands());
        assertNull(TestValues.NULL, msg.getSecondaryImage());
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
            AddCommand cmd = new AddCommand(hash);

            JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
            assertNotNull(TestValues.NOT_NULL, body);

            // Test everything in the json body.
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
            assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

            JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);

            List<String> vrCommandsList = JsonUtils.readStringListFromJsonObject(parameters, AddCommand.KEY_VR_COMMANDS);
            List<String> testCommandsList = cmd.getVrCommands();
            assertEquals(TestValues.MATCH, vrCommandsList.size(), testCommandsList.size());
            assertTrue(TestValues.TRUE, Validator.validateStringList(vrCommandsList, testCommandsList));

            assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, AddCommand.KEY_CMD_ID), cmd.getCmdID());

            JSONObject menuParams = JsonUtils.readJsonObjectFromJsonObject(parameters, AddCommand.KEY_MENU_PARAMS);
            MenuParams referenceMenuParams = new MenuParams(JsonRPCMarshaller.deserializeJSONObject(menuParams));
            assertTrue(TestValues.TRUE, Validator.validateMenuParams(referenceMenuParams, cmd.getMenuParams()));

            JSONObject cmdIcon = JsonUtils.readJsonObjectFromJsonObject(parameters, AddCommand.KEY_CMD_ICON);
            Image referenceCmdIcon = new Image(JsonRPCMarshaller.deserializeJSONObject(cmdIcon));
            assertTrue(TestValues.TRUE, Validator.validateImage(referenceCmdIcon, cmd.getCmdIcon()));

            JSONObject secondaryIcon = JsonUtils.readJsonObjectFromJsonObject(parameters, AddCommand.KEY_SECONDARY_IMAGE);
            Image referenceSecondaryIcon = new Image(JsonRPCMarshaller.deserializeJSONObject(secondaryIcon));
            assertTrue(TestValues.TRUE, Validator.validateImage(referenceSecondaryIcon, cmd.getSecondaryImage()));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}