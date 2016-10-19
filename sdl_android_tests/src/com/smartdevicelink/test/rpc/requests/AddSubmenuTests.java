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
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.AddSubmenu}
 */
public class AddSubmenuTests extends BaseRpcTests{

    @Override
    protected RPCMessage createMessage(){
        AddSubMenu msg = new AddSubMenu();

        msg.setMenuID(Test.GENERAL_INT);
        msg.setMenuName(Test.GENERAL_STRING);
        msg.setPosition(Test.GENERAL_INT);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_REQUEST;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ADD_SUB_MENU.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(AddSubMenu.KEY_MENU_ID, Test.GENERAL_INT);
            result.put(AddSubMenu.KEY_MENU_NAME, Test.GENERAL_STRING);
            result.put(AddSubMenu.KEY_POSITION, Test.GENERAL_INT);
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
        int testMenuId      = ( (AddSubMenu) msg ).getMenuID();
        int testPosition    = ( (AddSubMenu) msg ).getPosition();
        String testMenuName = ( (AddSubMenu) msg ).getMenuName();
        
        // Valid Tests
        assertEquals("Menu ID didn't match input menu ID.", Test.GENERAL_INT, testMenuId);
        assertEquals("Menu name didn't match input menu name.", Test.GENERAL_STRING, testMenuName);
        assertEquals("Position didn't match input position.", Test.GENERAL_INT, testPosition);
    
    	// Invalid/Null Tests
        AddSubMenu msg = new AddSubMenu();
        assertNotNull("Null object creation failed.", msg);
        testNullBase(msg);

        assertNull(Test.NULL, msg.getMenuID());
        assertNull(Test.NULL, msg.getMenuName());
        assertNull(Test.NULL, msg.getPosition());
    }
    
    /**
     * Tests a valid JSON construction of this RPC message.
     */
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
    	assertNotNull(Test.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			AddSubMenu cmd = new AddSubMenu(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, AddSubMenu.KEY_MENU_ID), cmd.getMenuID());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, AddSubMenu.KEY_POSITION), cmd.getPosition());
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, AddSubMenu.KEY_MENU_NAME), cmd.getMenuName());
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}
    }
}