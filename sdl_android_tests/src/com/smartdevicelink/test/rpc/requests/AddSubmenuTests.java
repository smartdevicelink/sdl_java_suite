package com.smartdevicelink.test.rpc.requests;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.AddSubMenu;
import com.smartdevicelink.test.BaseRpcTests;

public class AddSubmenuTests extends BaseRpcTests{

    private static final int    MENU_ID   = 100;
    private static final int    POSITION  = 0;
    private static final String MENU_NAME = "My Submenu";

    @Override
    protected RPCMessage createMessage(){
        AddSubMenu msg = new AddSubMenu();

        msg.setMenuID(MENU_ID);
        msg.setMenuName(MENU_NAME);
        msg.setPosition(POSITION);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_REQUEST;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ADD_SUB_MENU;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(AddSubMenu.KEY_MENU_ID, MENU_ID);
            result.put(AddSubMenu.KEY_MENU_NAME, MENU_NAME);
            result.put(AddSubMenu.KEY_POSITION, POSITION);
        }catch(JSONException e){
            /* do nothing */
        }

        return result;
    }

    public void testMenuId(){
        int menuId = ( (AddSubMenu) msg ).getMenuID();
        assertEquals("Menu ID didn't match input menu ID.", MENU_ID, menuId);
    }

    public void testMenuName(){
        String menuName = ( (AddSubMenu) msg ).getMenuName();
        assertEquals("Menu name didn't match input menu name.", MENU_NAME, menuName);
    }

    public void testMenuPosition(){
        int position = ( (AddSubMenu) msg ).getPosition();
        assertEquals("Position didn't match input position.", POSITION, position);
    }

    public void testNull(){
        AddSubMenu msg = new AddSubMenu();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);

        assertNull("Menu ID wasn't set, but getter method returned an object.", msg.getMenuID());
        assertNull("Menu name wasn't set, but getter method returned an object.", msg.getMenuName());
        assertNull("Position wasn't set, but getter method returned an object.", msg.getPosition());
    }

}
