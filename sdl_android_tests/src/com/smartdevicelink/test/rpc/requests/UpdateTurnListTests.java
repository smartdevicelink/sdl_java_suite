package com.smartdevicelink.test.rpc.requests;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.Turn;
import com.smartdevicelink.proxy.rpc.UpdateTurnList;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.SoftButtonType;
import com.smartdevicelink.proxy.rpc.enums.SystemAction;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

public class UpdateTurnListTests extends BaseRpcTests {
	
	private final List<SoftButton> SOFT_BUTTON_LIST = new ArrayList<SoftButton>();
    private static final Boolean SOFT_BUTTON_HIGHLIGHTED = true;
    private static final Integer SOFT_BUTTON_ID = 236;
	private static final SystemAction SOFT_BUTTON_SYSTEM_ACTION = SystemAction.STEAL_FOCUS;
    private static final String SOFT_BUTTON_TEXT = "Hello";
    private static final SoftButtonType SOFT_BUTTON_KEY_TYPE = SoftButtonType.SBT_TEXT;
    private static final Image image = new Image();
    private static final Image SOFT_BUTTON_IMAGE = new Image();
    private static final String SOFT_BUTTON_VALUE = "buttonImage.jpg";
    private static final ImageType SOFT_BUTTON_IMAGE_TYPE = ImageType.STATIC;
    
    List<Turn> TURN_LIST = new ArrayList<Turn>();
    
    @Override
    protected RPCMessage createMessage() {
    	UpdateTurnList msg = new UpdateTurnList();
    	
    	createCustomObjects();

    	msg.setTurnList(TURN_LIST);
    	msg.setSoftButtons(SOFT_BUTTON_LIST);
    	
    	return msg;
    }
    
    private void createCustomObjects() {		
		SOFT_BUTTON_IMAGE.setValue(SOFT_BUTTON_VALUE);
		SOFT_BUTTON_IMAGE.setImageType(SOFT_BUTTON_IMAGE_TYPE);
		
		SoftButton softButton = new SoftButton();
		softButton.setIsHighlighted(SOFT_BUTTON_HIGHLIGHTED);
		softButton.setSoftButtonID(SOFT_BUTTON_ID);
		softButton.setSystemAction(SOFT_BUTTON_SYSTEM_ACTION);
		softButton.setText(SOFT_BUTTON_TEXT);
		softButton.setType(SOFT_BUTTON_KEY_TYPE);
		softButton.setImage(SOFT_BUTTON_IMAGE);
		SOFT_BUTTON_LIST.add(softButton);
		
		image.setValue("image.png");
		image.setImageType(ImageType.DYNAMIC);
		
		Turn turn = new Turn();
		turn.setNavigationText("nav text");
		turn.setTurnIcon(image);
		
		TURN_LIST.add(turn);
	}
    
    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_REQUEST;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.UPDATE_TURN_LIST;
    }
    
    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();
        JSONArray turnList = new JSONArray();
        JSONArray softButtons = new JSONArray();
        
        try{
        	JSONObject softButton = new JSONObject();
			softButton.put(SoftButton.KEY_IS_HIGHLIGHTED , SOFT_BUTTON_HIGHLIGHTED);
			softButton.put(SoftButton.KEY_SOFT_BUTTON_ID, SOFT_BUTTON_ID);
			softButton.put(SoftButton.KEY_SYSTEM_ACTION, SOFT_BUTTON_SYSTEM_ACTION);
			softButton.put(SoftButton.KEY_TEXT, SOFT_BUTTON_TEXT);
			softButton.put(SoftButton.KEY_TYPE, SOFT_BUTTON_KEY_TYPE);
			softButton.put(SoftButton.KEY_IMAGE, SOFT_BUTTON_IMAGE.serializeJSON());
			softButtons.put(softButton);
			
			JSONObject turnItem = new JSONObject();
			turnItem.put(Turn.KEY_NAVIGATION_TEXT, "nav text");
			turnItem.put(Turn.KEY_TURN_IMAGE, image.serializeJSON());
			turnList.put(turnItem);
			
			result.put(UpdateTurnList.KEY_SOFT_BUTTONS, softButtons);
			result.put(UpdateTurnList.KEY_TURN_LIST, turnList);
			
        } catch(JSONException e){
            /* do nothing */
        }

        return result;
    }
    
    public void testSoftButtons () {
		List<SoftButton> copy = ( (UpdateTurnList) msg ).getSoftButtons();
		
		assertNotNull("Null turn list returned.", copy);
		assertTrue("Input value didn't match expected value.", Validator.validateSoftButtons(SOFT_BUTTON_LIST, copy));
	}
    
    public void testTurnList () {
		List<Turn> copy = ( (UpdateTurnList) msg ).getTurnList();
		
		assertNotNull("Null turn list returned.", copy);
		assertTrue("Input value didn't match expected value.", Validator.validateTurnList(TURN_LIST, copy));
	}
    
    public void testNull(){
        UpdateTurnList msg = new UpdateTurnList();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);

        assertNull("Turn list wasn't set, but getter method returned an object.", msg.getTurnList());
        assertNull("Soft button list wasn't set, but getter method returned an object.", msg.getSoftButtons());
    }
    
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(getCommandType(), getMessageType());
    	assertNotNull("Command object is null", commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			UpdateTurnList cmd = new UpdateTurnList(hash);
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull("Command type doesn't match expected message type", body);
			
			// test everything in the body
			assertEquals("Command name doesn't match input name", JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals("Correlation ID doesn't match input ID", JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			JSONArray softButtonArray = JsonUtils.readJsonArrayFromJsonObject(parameters, UpdateTurnList.KEY_SOFT_BUTTONS);
			List<SoftButton> softButtonList = new ArrayList<SoftButton>();
			for (int index = 0; index < softButtonArray.length(); index++) {
				SoftButton chunk = new SoftButton(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)softButtonArray.get(index)) );
				softButtonList.add(chunk);
			}
			assertTrue("Soft button list doesn't match input button list",  Validator.validateSoftButtons(softButtonList, cmd.getSoftButtons()));
			
			JSONArray turnArray = JsonUtils.readJsonArrayFromJsonObject(parameters, UpdateTurnList.KEY_TURN_LIST);
			List<Turn> turnList = new ArrayList<Turn>();
			for (int index = 0; index < turnArray.length(); index++) {
				Turn chunk = new Turn(JsonRPCMarshaller.deserializeJSONObject((JSONObject) turnArray.get(index)));
				if (chunk != null)
					turnList.add(chunk);
			}
			assertTrue("Turn list doesn't match input button list",  Validator.validateTurnList(turnList, cmd.getTurnList()));
		} catch (JSONException e) {
			e.printStackTrace();
		}
    }
}