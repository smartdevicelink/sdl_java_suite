package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.AddSubMenu;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.enums.MenuLayout;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Hashtable;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.AddSubMenu}
 */
public class AddSubmenuTests extends BaseRpcTests {

    @Override
    protected RPCMessage createMessage() {
        AddSubMenu msg = new AddSubMenu();

        msg.setMenuID(TestValues.GENERAL_INT);
        msg.setMenuName(TestValues.GENERAL_STRING);
        msg.setPosition(TestValues.GENERAL_INT);
        msg.setMenuIcon(TestValues.GENERAL_IMAGE);
        msg.setMenuLayout(TestValues.GENERAL_MENU_LAYOUT);
        msg.setParentID(TestValues.GENERAL_MENU_MAX_ID);

        return msg;
    }

    @Override
    protected String getMessageType() {
        return RPCMessage.KEY_REQUEST;
    }

    @Override
    protected String getCommandType() {
        return FunctionID.ADD_SUB_MENU.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion) {
        JSONObject result = new JSONObject();

        try {
            result.put(AddSubMenu.KEY_MENU_ID, TestValues.GENERAL_INT);
            result.put(AddSubMenu.KEY_MENU_NAME, TestValues.GENERAL_STRING);
            result.put(AddSubMenu.KEY_POSITION, TestValues.GENERAL_INT);
            result.put(AddSubMenu.KEY_MENU_ICON, TestValues.JSON_IMAGE);
            result.put(AddSubMenu.KEY_MENU_LAYOUT, TestValues.GENERAL_MENU_LAYOUT);
            result.put(AddSubMenu.KEY_PARENT_ID, TestValues.GENERAL_MENU_MAX_ID);
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
        int testMenuId = ((AddSubMenu) msg).getMenuID();
        int testPosition = ((AddSubMenu) msg).getPosition();
        String testMenuName = ((AddSubMenu) msg).getMenuName();
        Image testMenuIcon = ((AddSubMenu) msg).getMenuIcon();
        MenuLayout testMenuLayout = ((AddSubMenu) msg).getMenuLayout();
        int testParentID = ((AddSubMenu) msg).getParentID();

        // Valid Tests
        assertEquals("Menu ID didn't match input menu ID.", TestValues.GENERAL_INT, testMenuId);
        assertEquals("Menu name didn't match input menu name.", TestValues.GENERAL_STRING, testMenuName);
        assertEquals("Position didn't match input position.", TestValues.GENERAL_INT, testPosition);
        assertTrue("Menu icon didn't match input icon.", Validator.validateImage(TestValues.GENERAL_IMAGE, testMenuIcon));
        assertEquals("Menu layout didn't match input menu layout.", TestValues.GENERAL_MENU_LAYOUT, testMenuLayout);
        assertEquals("Parent ID didn't match input Parent ID.", TestValues.GENERAL_MENU_MAX_ID, testParentID);


        // Invalid/Null Tests
        AddSubMenu msg = new AddSubMenu();
        assertNotNull("Null object creation failed.", msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getMenuID());
        assertNull(TestValues.NULL, msg.getMenuName());
        assertNull(TestValues.NULL, msg.getPosition());
        assertNull(TestValues.NULL, msg.getMenuIcon());
        assertNull(TestValues.NULL, msg.getMenuLayout());
        assertNull(TestValues.NULL, msg.getParentID());
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
            AddSubMenu cmd = new AddSubMenu(hash);

            JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
            assertNotNull(TestValues.NOT_NULL, body);

            // Test everything in the json body.
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
            assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

            JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);

            assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, AddSubMenu.KEY_MENU_ID), cmd.getMenuID());
            assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, AddSubMenu.KEY_POSITION), cmd.getPosition());
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, AddSubMenu.KEY_MENU_NAME), cmd.getMenuName());
            assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(parameters, AddSubMenu.KEY_MENU_LAYOUT), cmd.getMenuLayout());
            assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, AddSubMenu.KEY_PARENT_ID), cmd.getParentID());

            JSONObject menuIcon = JsonUtils.readJsonObjectFromJsonObject(parameters, AddSubMenu.KEY_MENU_ICON);
            Image referenceMenuIcon = new Image(JsonRPCMarshaller.deserializeJSONObject(menuIcon));
            assertTrue(TestValues.TRUE, Validator.validateImage(referenceMenuIcon, cmd.getMenuIcon()));
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}