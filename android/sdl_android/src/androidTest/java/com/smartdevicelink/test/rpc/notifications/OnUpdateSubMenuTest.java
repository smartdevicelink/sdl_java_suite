package com.smartdevicelink.test.rpc.notifications;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.OnUpdateSubMenu;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.TestValues;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.OnUpdateSubMenu}
 */
public class OnUpdateSubMenuTest extends BaseRpcTests {
    @Override
    protected RPCMessage createMessage() {
        OnUpdateSubMenu msg = new OnUpdateSubMenu();

        msg.setMenuID(TestValues.GENERAL_INT);
        msg.setUpdateSubCells(TestValues.GENERAL_BOOLEAN);

        return msg;
    }

    @Override
    protected String getMessageType() {
        return RPCMessage.KEY_NOTIFICATION;
    }

    @Override
    protected String getCommandType() {
        return FunctionID.ON_UPDATE_SUB_MENU.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion) {
        JSONObject result = new JSONObject();

        try {
            result.put(OnUpdateSubMenu.KEY_MENU_ID, TestValues.GENERAL_INT);
            result.put(OnUpdateSubMenu.KEY_UPDATE_SUB_CELLS, TestValues.GENERAL_BOOLEAN);
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
        int menuId = ((OnUpdateSubMenu) msg).getMenuID();
        boolean updateSubCells = ((OnUpdateSubMenu) msg).getUpdateSubCells();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, menuId);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, updateSubCells);

        // Invalid/Null Tests
        OnUpdateSubMenu msg = new OnUpdateSubMenu();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getMenuID());
        assertNull(TestValues.NULL, msg.getUpdateSubCells());
    }
}
