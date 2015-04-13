package com.smartdevicelink.test.rpc.requests;

import java.util.Hashtable;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.DeleteSubMenu;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.json.rpc.JsonFileReader;
import com.smartdevicelink.test.utils.JsonUtils;

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
    
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(getCommandType(), getMessageType());
    	assertNotNull("Command object is null", commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			DeleteSubMenu cmd = new DeleteSubMenu(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull("Command type doesn't match expected message type", body);
			
			// test everything in the body
			assertEquals("Command name doesn't match input name", JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals("Correlation ID doesn't match input ID", JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals("Menu ID doesn't match input ID", JsonUtils.readIntegerFromJsonObject(parameters, DeleteSubMenu.KEY_MENU_ID), cmd.getMenuID());
			
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
    	
    }
    
}
