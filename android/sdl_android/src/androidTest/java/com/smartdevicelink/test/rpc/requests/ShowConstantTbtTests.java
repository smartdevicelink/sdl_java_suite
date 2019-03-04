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
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.ShowConstantTbt}
 */
public class ShowConstantTbtTests extends BaseRpcTests {

	@Override
	protected RPCMessage createMessage() {
		ShowConstantTbt msg = new ShowConstantTbt();
		
		msg.setDistanceToManeuver(Test.GENERAL_DOUBLE);
		msg.setDistanceToManeuverScale(Test.GENERAL_DOUBLE);
		msg.setEta(Test.GENERAL_STRING);
		msg.setManeuverComplete(true);
		msg.setNavigationText1(Test.GENERAL_STRING);
		msg.setNavigationText2(Test.GENERAL_STRING);
		msg.setNextTurnIcon(Test.GENERAL_IMAGE);
		msg.setSoftButtons(Test.GENERAL_SOFTBUTTON_LIST);
		msg.setTimeToDestination(Test.GENERAL_STRING);
		msg.setTotalDistance(Test.GENERAL_STRING);
		msg.setTurnIcon(Test.GENERAL_IMAGE);
		
		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.SHOW_CONSTANT_TBT.toString();
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();
        
        try {			
			result.put(ShowConstantTbt.KEY_SOFT_BUTTONS, Test.JSON_SOFTBUTTONS);
        	result.put(ShowConstantTbt.KEY_ETA, Test.GENERAL_STRING);
        	result.put(ShowConstantTbt.KEY_MANEUVER_COMPLETE, true);
        	result.put(ShowConstantTbt.KEY_MANEUVER_DISTANCE, Test.GENERAL_DOUBLE);
        	result.put(ShowConstantTbt.KEY_MANEUVER_DISTANCE_SCALE, Test.GENERAL_DOUBLE);
        	result.put(ShowConstantTbt.KEY_TEXT1, Test.GENERAL_STRING);
        	result.put(ShowConstantTbt.KEY_TEXT2, Test.GENERAL_STRING);
        	result.put(ShowConstantTbt.KEY_TIME_TO_DESTINATION, Test.GENERAL_STRING);
        	result.put(ShowConstantTbt.KEY_TOTAL_DISTANCE, Test.GENERAL_STRING);
        	result.put(ShowConstantTbt.KEY_MANEUVER_IMAGE, Test.GENERAL_IMAGE.serializeJSON());
        	result.put(ShowConstantTbt.KEY_NEXT_MANEUVER_IMAGE, Test.GENERAL_IMAGE.serializeJSON());        	
        } catch(JSONException e){
        	fail(Test.JSON_FAIL);
        }
        
		return result;
	}
	
	/**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
		// Test Values
    	Double  testScale                = ((ShowConstantTbt) msg).getDistanceToManeuverScale();
    	Double  testDistance             = ((ShowConstantTbt) msg).getDistanceToManeuver();
    	String  testEta                  = ((ShowConstantTbt) msg).getEta();
		String  testTimeToDestination    = ((ShowConstantTbt) msg).getTimeToDestination();
		String  testTotalDistance        = ((ShowConstantTbt) msg).getTotalDistance();
		String  testNavText2             = ((ShowConstantTbt) msg).getNavigationText2();
		String  testNavText1             = ((ShowConstantTbt) msg).getNavigationText1();
		Boolean testManeuverComplete     = ((ShowConstantTbt) msg).getManeuverComplete();
		Image   testTurnIcon             = ((ShowConstantTbt) msg).getTurnIcon();
		Image   testNextTurnIcon         = ((ShowConstantTbt) msg).getNextTurnIcon();
		List<SoftButton> testSoftButtons = ((ShowConstantTbt) msg).getSoftButtons();
		
		// Valid Test
		assertEquals(Test.MATCH, Test.GENERAL_STRING, testTimeToDestination);
		assertEquals(Test.MATCH, Test.GENERAL_DOUBLE, testScale);
		assertEquals(Test.MATCH, Test.GENERAL_STRING, testNavText1);
		assertEquals(Test.MATCH, Test.GENERAL_STRING, testNavText2);
		assertEquals(Test.MATCH, Test.GENERAL_STRING, testEta);
		assertEquals(Test.MATCH, Test.GENERAL_STRING, testTotalDistance);
		assertEquals(Test.MATCH, Test.GENERAL_DOUBLE, testDistance);
		assertTrue(Test.TRUE, testManeuverComplete);
		assertTrue(Test.TRUE, Validator.validateSoftButtons(Test.GENERAL_SOFTBUTTON_LIST, testSoftButtons));
		assertTrue(Test.TRUE, Validator.validateImage(Test.GENERAL_IMAGE, testTurnIcon));
		assertTrue(Test.TRUE, Validator.validateImage(Test.GENERAL_IMAGE, testNextTurnIcon));
		
		// Invalid/Null Tests
		ShowConstantTbt msg = new ShowConstantTbt();
		assertNotNull(Test.NOT_NULL, msg);
        testNullBase(msg);
        
        assertNull(Test.NULL, msg.getSoftButtons());
        assertNull(Test.NULL, msg.getNavigationText1());
        assertNull(Test.NULL, msg.getNavigationText2());
        assertNull(Test.NULL, msg.getDistanceToManeuver());
        assertNull(Test.NULL, msg.getDistanceToManeuverScale());
        assertNull(Test.NULL, msg.getEta());
        assertNull(Test.NULL, msg.getManeuverComplete());
        assertNull(Test.NULL, msg.getNextTurnIcon());
        assertNull(Test.NULL, msg.getTimeToDestination());
        assertNull(Test.NULL, msg.getTotalDistance());
        assertNull(Test.NULL, msg.getTurnIcon());
	}
	
	/**
     * Tests a valid JSON construction of this RPC message.
     */
	public void testJsonConstructor () {
		JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
    	assertNotNull(Test.NOT_NULL, commandJson);
    	
    	try {
    		Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
    		ShowConstantTbt cmd = new ShowConstantTbt(hash);
    		JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, ShowConstantTbt.KEY_ETA), cmd.getEta());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, ShowConstantTbt.KEY_MANEUVER_COMPLETE), cmd.getManeuverComplete());
			assertEquals(Test.MATCH, JsonUtils.readDoubleFromJsonObject(parameters, ShowConstantTbt.KEY_MANEUVER_DISTANCE), cmd.getDistanceToManeuver());
			assertEquals(Test.MATCH, JsonUtils.readDoubleFromJsonObject(parameters, ShowConstantTbt.KEY_MANEUVER_DISTANCE_SCALE), cmd.getDistanceToManeuverScale());			
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, ShowConstantTbt.KEY_TEXT1), cmd.getNavigationText1());
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, ShowConstantTbt.KEY_TEXT2), cmd.getNavigationText2());
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, ShowConstantTbt.KEY_TIME_TO_DESTINATION), cmd.getTimeToDestination());
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, ShowConstantTbt.KEY_TOTAL_DISTANCE), cmd.getTotalDistance());
			
			JSONObject icon1 = JsonUtils.readJsonObjectFromJsonObject(parameters, ShowConstantTbt.KEY_MANEUVER_IMAGE);
			Image refIcon1 = new Image(JsonRPCMarshaller.deserializeJSONObject(icon1));
			assertTrue(Test.TRUE, Validator.validateImage(refIcon1, cmd.getTurnIcon()));
			
			JSONObject icon2 = JsonUtils.readJsonObjectFromJsonObject(parameters, ShowConstantTbt.KEY_NEXT_MANEUVER_IMAGE);
			Image refIcon2 = new Image(JsonRPCMarshaller.deserializeJSONObject(icon2));
			assertTrue(Test.TRUE, Validator.validateImage(refIcon2, cmd.getNextTurnIcon()));
			
			JSONArray softButtonArray = JsonUtils.readJsonArrayFromJsonObject(parameters, ShowConstantTbt.KEY_SOFT_BUTTONS);
			List<SoftButton> softButtonList = new ArrayList<SoftButton>();
			for (int index = 0; index < softButtonArray.length(); index++) {
				SoftButton chunk = new SoftButton(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)softButtonArray.get(index)) );
				softButtonList.add(chunk);
			}
			assertTrue(Test.TRUE,  Validator.validateSoftButtons(softButtonList, cmd.getSoftButtons()));
    	} catch (JSONException e) {
    		fail(Test.JSON_FAIL);
		}
	}
}