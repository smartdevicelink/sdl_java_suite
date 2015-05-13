package com.smartdevicelink.test.rpc.requests;

import java.util.Hashtable;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.AddSubMenu;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

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
    
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(getCommandType(), getMessageType());
    	assertNotNull("Command object is null", commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			AddSubMenu cmd = new AddSubMenu(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull("Command type doesn't match expected message type", body);
			
			// test everything in the body
			assertEquals("Command name doesn't match input name", JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals("Correlation ID doesn't match input ID", JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			
			assertEquals("Menu ID doesn't match input ID", JsonUtils.readIntegerFromJsonObject(parameters, AddSubMenu.KEY_MENU_ID), cmd.getMenuID());
			assertEquals("Position doesn't match input position", JsonUtils.readIntegerFromJsonObject(parameters, AddSubMenu.KEY_POSITION), cmd.getPosition());
			assertEquals("Menu name doesn't match input name", JsonUtils.readStringFromJsonObject(parameters, AddSubMenu.KEY_MENU_NAME), cmd.getMenuName());
			
			
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
    	
    }

}
