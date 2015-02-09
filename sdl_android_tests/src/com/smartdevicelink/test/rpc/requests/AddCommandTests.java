package com.smartdevicelink.test.rpc.requests;

import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.AddCommand;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.MenuParams;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.json.rpc.JsonFileReader;
import com.smartdevicelink.test.utils.JsonUtils;
import com.smartdevicelink.test.utils.Validator;

public class AddCommandTests extends BaseRpcTests{

    // TODO: Add tests for JSON constructors
    
    private static final int       CMD_ID      = 100;
    private static final int       PARENT_ID   = -1;
    private static final int       POSITION    = 0;
    private static final String    CMD_NAME    = "My Command";
    private static final String    IMG_NAME    = "some_icon.png";
    private static final ImageType IMG_TYPE    = ImageType.DYNAMIC;
    private static final String[]  VR_COMMANDS = new String[] { "My Command", "Command 1" };

    private Image                  image;
    private MenuParams             menuParams;
    private Vector<String>         vrCommands;

    @Override
    protected RPCMessage createMessage(){
        AddCommand msg = new AddCommand();

        createCustomObjects();
        
        msg.setCmdIcon(image);
        msg.setMenuParams(menuParams);
        msg.setVrCommands(vrCommands);
        msg.setCmdID(CMD_ID);

        return msg;
    }
    
    public void createCustomObjects () {
        image = new Image();
        image.setImageType(IMG_TYPE);
        image.setValue(IMG_NAME);
        
        menuParams = new MenuParams();
        menuParams.setMenuName(CMD_NAME);
        menuParams.setParentID(PARENT_ID);
        menuParams.setPosition(POSITION);
        
        vrCommands = new Vector<String>(VR_COMMANDS.length);
        for(String command : VR_COMMANDS){
            vrCommands.add(command);
        }
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_REQUEST;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ADD_COMMAND;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject(), image = new JSONObject(), menuParams = new JSONObject();

        try{
            image.put(Image.KEY_IMAGE_TYPE, IMG_TYPE);
            image.put(Image.KEY_VALUE, IMG_NAME);
            result.put(AddCommand.KEY_CMD_ICON, image);
            
            menuParams.put(MenuParams.KEY_MENU_NAME, CMD_NAME);
            menuParams.put(MenuParams.KEY_PARENT_ID, PARENT_ID);
            menuParams.put(MenuParams.KEY_POSITION, POSITION);
            result.put(AddCommand.KEY_MENU_PARAMS, menuParams);
            
            result.put(AddCommand.KEY_VR_COMMANDS, JsonUtils.createJsonArray(vrCommands));
            result.put(AddCommand.KEY_CMD_ID, CMD_ID);
        }catch(JSONException e){
            /* do nothing */
        }

        return result;
    }

    public void testCommandId(){
        int cmdId = ( (AddCommand) msg ).getCmdID();
        assertEquals("Command ID didn't match input command ID.", CMD_ID, cmdId);
    }

    public void testMenuParams(){
        MenuParams copy = ( (AddCommand) msg ).getMenuParams();

        assertNotNull("Menu parameters were null.", copy);

        // make sure a copy of the params was made
        assertNotSame("Menu parameters were not defensive copied.", menuParams, copy);

        // MenuParams doesn't override equals, so have to do a manual compare of all variables
        assertTrue("Menu parameters did not match input menu parameters.",
                Validator.validateMenuParams(menuParams, copy));
    }

    public void testImage(){
        Image copy = ( (AddCommand) msg ).getCmdIcon();

        assertNotNull("Image was null.", copy);
        assertNotSame("Image was not defensive copied.", image, copy);

        // Image doesn't override equals, so have to do a manual compare of all variables
        assertTrue("Image did not match input image.", Validator.validateImage(copy, image));
    }

    public void testVrCommands(){
        List<String> copy = ( (AddCommand) msg ).getVrCommands();

        assertNotNull("VR commands was null.", copy);
        assertNotSame("VR commands was not defensive copied.", vrCommands, copy);
        assertEquals("VR commands size doesn't match input size.", vrCommands.size(), copy.size());
        assertTrue("VR commands did not match input VR commands.", Validator.validateStringList(copy, vrCommands));
    }

    public void testNull(){
        AddCommand msg = new AddCommand();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);

        assertNull("Image wasn't set, but getter method returned an object.", msg.getCmdIcon());
        assertNull("Command ID wasn't set, but getter method returned an object.", msg.getCmdID());
        assertNull("Menu params weren't set, but getter method returned an object.", msg.getMenuParams());
        assertNull("VR commands weren't set, but getter method returned an object.", msg.getVrCommands());
    }
    
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(getCommandType(), getMessageType());
    	assertNotNull("Command object is null", commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			AddCommand cmd = new AddCommand(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull("Command type doesn't match expected message type", body);
			
			// test everything in the body
			assertEquals("Command name doesn't match input name", JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals("Correlation ID doesn't match input ID", JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			
			List<String> vrCommandsList = JsonUtils.readStringListFromJsonObject(parameters, AddCommand.KEY_VR_COMMANDS);
			List<String> testCommandsList = cmd.getVrCommands();
			assertEquals("VR command list length not same as reference VR command list length", vrCommandsList.size(), testCommandsList.size());
			assertTrue("VR command list doesn't match input command list", Validator.validateStringList(vrCommandsList, testCommandsList));
			
			assertEquals("Command ID doesn't match input ID", JsonUtils.readIntegerFromJsonObject(parameters, AddCommand.KEY_CMD_ID), cmd.getCmdID());
			
			JSONObject menuParams = JsonUtils.readJsonObjectFromJsonObject(parameters, AddCommand.KEY_MENU_PARAMS);
			MenuParams referenceMenuParams = new MenuParams(JsonRPCMarshaller.deserializeJSONObject(menuParams));
			assertTrue("Menu params doesn't match expected menu params", Validator.validateMenuParams(referenceMenuParams, cmd.getMenuParams()));
			
			JSONObject cmdIcon = JsonUtils.readJsonObjectFromJsonObject(parameters, AddCommand.KEY_CMD_ICON);
			Image referenceCmdIcon = new Image(JsonRPCMarshaller.deserializeJSONObject(cmdIcon));
			assertTrue("Image doesn't match expected image", Validator.validateImage(referenceCmdIcon, cmd.getCmdIcon()));
			
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
    	
    }
}
