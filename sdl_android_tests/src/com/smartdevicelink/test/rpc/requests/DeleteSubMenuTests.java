package com.smartdevicelink.test.rpc.requests;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.DeleteSubMenu;
import com.smartdevicelink.test.BaseRpcTests;

public class DeleteSubMenuTests extends BaseRpcTests{

    private static final int MENU_ID = 2031;

    @Override
    protected RPCMessage createMessage(){
        DeleteSubMenu msg = new DeleteSubMenu();

        msg.setMenuID(MENU_ID);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_REQUEST;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.DELETE_SUB_MENU;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(DeleteSubMenu.KEY_MENU_ID, MENU_ID);
        }catch(JSONException e){
            /* do nothing */
        }

        return result;
    }

    public void testMenuId(){
        int cmdId = ( (DeleteSubMenu) msg ).getMenuID();
        assertEquals("Menu ID didn't match input menu ID.", MENU_ID, cmdId);
    }

    public void testNull(){
        DeleteSubMenu msg = new DeleteSubMenu();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);

        assertNull("Menu ID wasn't set, but getter method returned an object.", msg.getMenuID());
    }
}
