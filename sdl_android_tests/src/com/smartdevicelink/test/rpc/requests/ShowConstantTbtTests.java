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
import com.smartdevicelink.proxy.rpc.ShowConstantTbt;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.SoftButtonType;
import com.smartdevicelink.proxy.rpc.enums.SystemAction;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.json.rpc.JsonFileReader;
import com.smartdevicelink.test.utils.JsonUtils;
import com.smartdevicelink.test.utils.Validator;

public class ShowConstantTbtTests extends BaseRpcTests {
	
	public static final String KEY_TEXT1                   = "message1";
    public static final String KEY_TEXT2                   = "message2";
    public static final String KEY_ETA                     = "42";
    public static final String KEY_TOTAL_DISTANCE          = "42";
    public static final Double KEY_MANEUVER_DISTANCE       = 42.42;
    public static final Double KEY_MANEUVER_DISTANCE_SCALE = 42.42;
    public static final Image KEY_MANEUVER_IMAGE           = new Image();
    public static final Image KEY_NEXT_MANEUVER_IMAGE      = new Image();
    public static final Boolean KEY_MANEUVER_COMPLETE      = true;
    public static final String KEY_TIME_TO_DESTINATION     = "timeToDestination";
    public static final Image image = new Image();
    private final List<SoftButton> SOFT_BUTTON_LIST = new ArrayList<SoftButton>();
    private static final Boolean SOFT_BUTTON_HIGHLIGHTED = true;
    private static final Integer SOFT_BUTTON_ID = 236;
	private static final SystemAction SOFT_BUTTON_SYSTEM_ACTION = SystemAction.STEAL_FOCUS;
    private static final String SOFT_BUTTON_TEXT = "Hello";
    private static final SoftButtonType SOFT_BUTTON_KEY_TYPE = SoftButtonType.SBT_TEXT;

	@Override
	protected RPCMessage createMessage() {
		ShowConstantTbt msg = new ShowConstantTbt();
				
		KEY_MANEUVER_IMAGE.setValue("turnIcon");
		KEY_MANEUVER_IMAGE.setImageType(ImageType.STATIC);
		KEY_NEXT_MANEUVER_IMAGE.setValue("nextTurnIcon");
		KEY_NEXT_MANEUVER_IMAGE.setImageType(ImageType.STATIC);
		
		image.setValue("softbutton");
		image.setImageType(ImageType.STATIC);
		
		SoftButton softButton = new SoftButton();
		softButton.setIsHighlighted(SOFT_BUTTON_HIGHLIGHTED);
		softButton.setSoftButtonID(SOFT_BUTTON_ID);
		softButton.setSystemAction(SOFT_BUTTON_SYSTEM_ACTION);
		softButton.setText(SOFT_BUTTON_TEXT);
		softButton.setType(SOFT_BUTTON_KEY_TYPE);
		softButton.setImage(image);
		SOFT_BUTTON_LIST.add(softButton);
		
		msg.setDistanceToManeuver(KEY_MANEUVER_DISTANCE);
		msg.setDistanceToManeuverScale(KEY_MANEUVER_DISTANCE);
		msg.setEta(KEY_ETA);
		msg.setManeuverComplete(KEY_MANEUVER_COMPLETE);
		msg.setNavigationText1(KEY_TEXT1);
		msg.setNavigationText2(KEY_TEXT2);
		msg.setNextTurnIcon(KEY_NEXT_MANEUVER_IMAGE);
		msg.setSoftButtons(SOFT_BUTTON_LIST);
		msg.setTimeToDestination(KEY_TIME_TO_DESTINATION);
		msg.setTotalDistance(KEY_TOTAL_DISTANCE);
		msg.setTurnIcon(KEY_MANEUVER_IMAGE);
		
		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.SHOW_CONSTANT_TBT;
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();
        JSONArray softButtons = new JSONArray();
        
        try {
        	JSONObject softButton = new JSONObject();
			softButton.put(SoftButton.KEY_IS_HIGHLIGHTED , SOFT_BUTTON_HIGHLIGHTED);
			softButton.put(SoftButton.KEY_SOFT_BUTTON_ID, SOFT_BUTTON_ID);
			softButton.put(SoftButton.KEY_SYSTEM_ACTION, SOFT_BUTTON_SYSTEM_ACTION);
			softButton.put(SoftButton.KEY_TEXT, SOFT_BUTTON_TEXT);
			softButton.put(SoftButton.KEY_TYPE, SOFT_BUTTON_KEY_TYPE);
			softButton.put(SoftButton.KEY_IMAGE, image.serializeJSON());
			softButtons.put(softButton);
			
			result.put(ShowConstantTbt.KEY_SOFT_BUTTONS, softButtons);
        	result.put(ShowConstantTbt.KEY_ETA, KEY_ETA);
        	result.put(ShowConstantTbt.KEY_MANEUVER_COMPLETE, true);
        	result.put(ShowConstantTbt.KEY_MANEUVER_DISTANCE, KEY_MANEUVER_DISTANCE);
        	result.put(ShowConstantTbt.KEY_MANEUVER_DISTANCE_SCALE, KEY_MANEUVER_DISTANCE_SCALE);
        	result.put(ShowConstantTbt.KEY_TEXT1, KEY_TEXT1);
        	result.put(ShowConstantTbt.KEY_TEXT2, KEY_TEXT2);
        	result.put(ShowConstantTbt.KEY_TIME_TO_DESTINATION, KEY_TIME_TO_DESTINATION);
        	result.put(ShowConstantTbt.KEY_TOTAL_DISTANCE, KEY_TOTAL_DISTANCE);
        	result.put(ShowConstantTbt.KEY_MANEUVER_IMAGE, KEY_MANEUVER_IMAGE.serializeJSON());
        	result.put(ShowConstantTbt.KEY_NEXT_MANEUVER_IMAGE, KEY_NEXT_MANEUVER_IMAGE.serializeJSON());
        	
        } catch(JSONException e){
            /* do nothing */
        }
        
		return result;
	}
	
	public void testNavigationText1 () {
		String text = ((ShowConstantTbt) msg).getNavigationText1();
		assertEquals("Navigation text 1 didn't match input navigation text 1.", KEY_TEXT1, text);
	}
	
	public void testNavigationText2 () {
		String text = ((ShowConstantTbt) msg).getNavigationText2();
		assertEquals("Navigation text 2 didn't match input navigation text 2.", KEY_TEXT2, text);
	}
	
	public void testEta () {
		String text = ((ShowConstantTbt) msg).getEta();
		assertEquals("Eta didn't match input eta.", KEY_ETA, text);
	}
	
	public void testTotalDistance () {
		String total = ((ShowConstantTbt) msg).getTotalDistance();
		assertEquals("Total distance didn't match input total distance.", KEY_TOTAL_DISTANCE, total);
	}
	
	public void testManeuverDistance () {
		Double distance = ((ShowConstantTbt) msg).getDistanceToManeuver();
		assertEquals("Maneuver distance didn't match input maneuver distance.", KEY_MANEUVER_DISTANCE, distance);
	}
	
	public void testManeuverDistanceScale () {
		Double scale = ((ShowConstantTbt) msg).getDistanceToManeuverScale();
		assertEquals("Maneuver distance scale didn't match input maneuver distance.", KEY_MANEUVER_DISTANCE_SCALE, scale);
	}
	
	public void testManeuverImage () {
		Image image = ((ShowConstantTbt) msg).getTurnIcon();
		assertTrue("Maneuver image didn't match input maneuver image.", Validator.validateImage(KEY_MANEUVER_IMAGE, image));
	}
	
	public void testNextManeuverImage () {
		Image image = ((ShowConstantTbt) msg).getNextTurnIcon();
		assertTrue("Next maneuver image didn't match input next maneuver image.", Validator.validateImage(KEY_NEXT_MANEUVER_IMAGE, image));
	}
	
	public void testManeuverComplete () {
		Boolean stat = ((ShowConstantTbt) msg).getManeuverComplete();
		assertEquals("Maneuver complete didn't match input maneuver complete status.", KEY_MANEUVER_COMPLETE, stat);
	}
	
	public void testSoftButtons () {
		List<SoftButton> copy = ((ShowConstantTbt) msg).getSoftButtons();		
		assertTrue("Input value didn't match expected value.", Validator.validateSoftButtons(SOFT_BUTTON_LIST, copy));
	}
	
	public void testTimeToDestination () {
		String ttd = ((ShowConstantTbt) msg).getTimeToDestination();
		assertEquals("Input value didn't match expected value.",KEY_TIME_TO_DESTINATION, ttd);
	}
	
	public void testNull () {
		ShowConstantTbt msg = new ShowConstantTbt();
		assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);
        
        assertNull("Soft buttons wasn't set, but getter method returned an object.", msg.getSoftButtons());
        assertNull("Text 1 wasn't set, but getter method returned an object.", msg.getNavigationText1());
        assertNull("Text 2 wasn't set, but getter method returned an object.", msg.getNavigationText2());
        assertNull("Maneuver distance wasn't set, but getter method returned an object.", msg.getDistanceToManeuver());
        assertNull("Maneuver distance scale wasn't set, but getter method returned an object.", msg.getDistanceToManeuverScale());
        assertNull("Eta wasn't set, but getter method returned an object.", msg.getEta());
        assertNull("Maneuver status wasn't set, but getter method returned an object.", msg.getManeuverComplete());
        assertNull("Next turn icon wasn't set, but getter method returned an object.", msg.getNextTurnIcon());
        assertNull("Time to destination wasn't set, but getter method returned an object.", msg.getTimeToDestination());
        assertNull("Total distance wasn't set, but getter method returned an object.", msg.getTotalDistance());
        assertNull("Turn icon wasn't set, but getter method returned an object.", msg.getTurnIcon());
	}
	
	public void testJsonConstructor () {
		JSONObject commandJson = JsonFileReader.readId(getCommandType(), getMessageType());
    	assertNotNull("Command object is null", commandJson);
    	
    	try {
    		Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
    		ShowConstantTbt cmd = new ShowConstantTbt(hash);
    		JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull("Command type doesn't match expected message type", body);
			
			// test everything in the body
			assertEquals("Command name doesn't match input name", JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals("Correlation ID doesn't match input ID", JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals("Value doesn't match input (eta).", JsonUtils.readBooleanFromJsonObject(parameters, ShowConstantTbt.KEY_ETA), cmd.getEta());
			assertEquals("Value doesn't match input (maneuver complete).", JsonUtils.readBooleanFromJsonObject(parameters, ShowConstantTbt.KEY_MANEUVER_COMPLETE), cmd.getManeuverComplete());
			assertEquals("Value doesn't match input (maneuver distance).", JsonUtils.readBooleanFromJsonObject(parameters, ShowConstantTbt.KEY_MANEUVER_DISTANCE), cmd.getDistanceToManeuver());
			assertEquals("Value doesn't match input (maneuver distance scale).", JsonUtils.readBooleanFromJsonObject(parameters, ShowConstantTbt.KEY_MANEUVER_DISTANCE_SCALE), cmd.getDistanceToManeuverScale());			
			assertEquals("Value doesn't match input (text1).", JsonUtils.readBooleanFromJsonObject(parameters, ShowConstantTbt.KEY_TEXT1), cmd.getNavigationText1());
			assertEquals("Value doesn't match input (text2).", JsonUtils.readBooleanFromJsonObject(parameters, ShowConstantTbt.KEY_TEXT2), cmd.getNavigationText2());
			assertEquals("Value doesn't match input (time to destination).", JsonUtils.readBooleanFromJsonObject(parameters, ShowConstantTbt.KEY_TIME_TO_DESTINATION), cmd.getTimeToDestination());
			assertEquals("Value doesn't match input (total distance).", JsonUtils.readBooleanFromJsonObject(parameters, ShowConstantTbt.KEY_TOTAL_DISTANCE), cmd.getTotalDistance());
			assertEquals("Value doesn't match input (maneuver image).", JsonUtils.readBooleanFromJsonObject(parameters, ShowConstantTbt.KEY_MANEUVER_IMAGE), cmd.getTurnIcon().getValue());
			assertEquals("Value doesn't match input (next maneuver image).", JsonUtils.readBooleanFromJsonObject(parameters, ShowConstantTbt.KEY_NEXT_MANEUVER_IMAGE), cmd.getNextTurnIcon().getValue());
			JSONArray softButtonArray = JsonUtils.readJsonArrayFromJsonObject(parameters, ShowConstantTbt.KEY_SOFT_BUTTONS);
			List<SoftButton> softButtonList = new ArrayList<SoftButton>();
			for (int index = 0; index < softButtonArray.length(); index++) {
				SoftButton chunk = new SoftButton(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)softButtonArray.get(index)) );
				softButtonList.add(chunk);
			}
			assertTrue("Soft button list doesn't match input button list",  Validator.validateSoftButtons(softButtonList, cmd.getSoftButtons()));
    	} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}