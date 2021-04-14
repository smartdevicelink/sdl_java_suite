package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.MenuParams;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.MenuParams}
 */
public class MenuParamsTests extends TestCase {

    private MenuParams msg;

    @Override
    public void setUp() {
        msg = new MenuParams();

        msg.setMenuName(TestValues.GENERAL_STRING);
        msg.setParentID(TestValues.GENERAL_INT);
        msg.setPosition(TestValues.GENERAL_INT);
        msg.setSecondaryText(TestValues.GENERAL_STRING);
        msg.setTertiaryText(TestValues.GENERAL_STRING);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values
        String menuName = msg.getMenuName();
        int parentId = msg.getParentID();
        int position = msg.getPosition();
        String secondaryText = msg.getSecondaryText();
        String tertiaryText = msg.getTertiaryText();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, menuName);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, parentId);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, position);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, secondaryText);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, tertiaryText);

        // Invalid/Null Tests
        MenuParams msg = new MenuParams();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getMenuName());
        assertNull(TestValues.NULL, msg.getParentID());
        assertNull(TestValues.NULL, msg.getPosition());
        assertNull(TestValues.NULL, msg.getSecondaryText());
        assertNull(TestValues.NULL, msg.getTertiaryText());
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(MenuParams.KEY_MENU_NAME, TestValues.GENERAL_STRING);
            reference.put(MenuParams.KEY_PARENT_ID, TestValues.GENERAL_INT);
            reference.put(MenuParams.KEY_POSITION, TestValues.GENERAL_INT);
            reference.put(MenuParams.KEY_SECONDARY_TEXT, TestValues.GENERAL_STRING);
            reference.put(MenuParams.KEY_TERTIARY_TEXT, TestValues.GENERAL_STRING);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
            }
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}