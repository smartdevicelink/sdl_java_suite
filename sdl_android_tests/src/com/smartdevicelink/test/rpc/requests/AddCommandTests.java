package com.smartdevicelink.test.rpc.requests;

import java.util.Hashtable;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.AddCommand;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.MenuParams;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.AddCommand}
 */
public class AddCommandTests extends BaseRpcTests{

    @Override
    protected RPCMessage createMessage(){
        AddCommand msg = new AddCommand();

        msg.setCmdIcon(Test.GENERAL_IMAGE);
        msg.setMenuParams(Test.GENERAL_MENUPARAMS);
        msg.setVrCommands(Test.GENERAL_STRING_LIST);
        msg.setCmdID(Test.GENERAL_INT);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_REQUEST;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ADD_COMMAND.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(AddCommand.KEY_CMD_ICON, Test.JSON_IMAGE);
            result.put(AddCommand.KEY_MENU_PARAMS, Test.JSON_MENUPARAMS);            
            result.put(AddCommand.KEY_VR_COMMANDS, JsonUtils.createJsonArray(Test.GENERAL_STRING_LIST));
            result.put(AddCommand.KEY_CMD_ID, Test.GENERAL_INT);
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
    	int          testCmdId      = ( (AddCommand) msg ).getCmdID();
    	Image        testImage      = ( (AddCommand) msg ).getCmdIcon();
    	MenuParams   testMenuParams = ( (AddCommand) msg ).getMenuParams();
    	List<String> testVrCommands = ( (AddCommand) msg ).getVrCommands();
    	
    	// Valid Tests
    	assertNotNull(Test.NOT_NULL, testMenuParams);
    	assertNotNull(Test.NOT_NULL, testImage);
    	assertNotNull(Test.NOT_NULL, testVrCommands);
    	
    	assertEquals(Test.MATCH, Test.GENERAL_INT, testCmdId);
    	assertEquals(Test.MATCH, Test.GENERAL_STRING_LIST.size(), testVrCommands.size());
    	
    	assertTrue(Test.TRUE, Validator.validateMenuParams(Test.GENERAL_MENUPARAMS, testMenuParams));
    	assertTrue(Test.TRUE, Validator.validateImage(Test.GENERAL_IMAGE, testImage));
    	assertTrue(Test.TRUE, Validator.validateStringList(Test.GENERAL_STRING_LIST, testVrCommands));
    	
    	// Invalid/Null Tests
    	AddCommand msg = new AddCommand();
    	assertNotNull(Test.NULL, msg);
    	testNullBase(msg);
    	
    	assertNull(Test.NULL, msg.getCmdIcon());
        assertNull(Test.NULL, msg.getCmdID());
        assertNull(Test.NULL, msg.getMenuParams());
        assertNull(Test.NULL, msg.getVrCommands());
    }
    
    /**
     * Tests a valid JSON construction of this RPC message.
     */
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
    	assertNotNull(Test.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			AddCommand cmd = new AddCommand(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			
			List<String> vrCommandsList = JsonUtils.readStringListFromJsonObject(parameters, AddCommand.KEY_VR_COMMANDS);
			List<String> testCommandsList = cmd.getVrCommands();
			assertEquals(Test.MATCH, vrCommandsList.size(), testCommandsList.size());
			assertTrue(Test.TRUE, Validator.validateStringList(vrCommandsList, testCommandsList));
			
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, AddCommand.KEY_CMD_ID), cmd.getCmdID());
			
			JSONObject menuParams = JsonUtils.readJsonObjectFromJsonObject(parameters, AddCommand.KEY_MENU_PARAMS);
			MenuParams referenceMenuParams = new MenuParams(JsonRPCMarshaller.deserializeJSONObject(menuParams));
			assertTrue(Test.TRUE, Validator.validateMenuParams(referenceMenuParams, cmd.getMenuParams()));
			
			JSONObject cmdIcon = JsonUtils.readJsonObjectFromJsonObject(parameters, AddCommand.KEY_CMD_ICON);
			Image referenceCmdIcon = new Image(JsonRPCMarshaller.deserializeJSONObject(cmdIcon));
			assertTrue(Test.TRUE, Validator.validateImage(referenceCmdIcon, cmd.getCmdIcon()));
			
		} catch (JSONException e) {
			e.printStackTrace();
		}    	
    }
}