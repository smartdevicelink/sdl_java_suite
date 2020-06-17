package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.ShowConstantTbt;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static android.support.test.InstrumentationRegistry.getContext;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.ShowConstantTbt}
 */
public class ShowConstantTbtTests extends BaseRpcTests {

	@Override
	protected RPCMessage createMessage() {
		ShowConstantTbt msg = new ShowConstantTbt();
		
		msg.setDistanceToManeuver(TestValues.GENERAL_DOUBLE);
		msg.setDistanceToManeuverScale(TestValues.GENERAL_DOUBLE);
		msg.setEta(TestValues.GENERAL_STRING);
		msg.setManeuverComplete(true);
		msg.setNavigationText1(TestValues.GENERAL_STRING);
		msg.setNavigationText2(TestValues.GENERAL_STRING);
		msg.setNextTurnIcon(TestValues.GENERAL_IMAGE);
		msg.setSoftButtons(TestValues.GENERAL_SOFTBUTTON_LIST);
		msg.setTimeToDestination(TestValues.GENERAL_STRING);
		msg.setTotalDistance(TestValues.GENERAL_STRING);
		msg.setTurnIcon(TestValues.GENERAL_IMAGE);
		
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
			result.put(ShowConstantTbt.KEY_SOFT_BUTTONS, TestValues.JSON_SOFTBUTTONS);
        	result.put(ShowConstantTbt.KEY_ETA, TestValues.GENERAL_STRING);
        	result.put(ShowConstantTbt.KEY_MANEUVER_COMPLETE, true);
        	result.put(ShowConstantTbt.KEY_MANEUVER_DISTANCE, TestValues.GENERAL_DOUBLE);
        	result.put(ShowConstantTbt.KEY_MANEUVER_DISTANCE_SCALE, TestValues.GENERAL_DOUBLE);
        	result.put(ShowConstantTbt.KEY_TEXT1, TestValues.GENERAL_STRING);
        	result.put(ShowConstantTbt.KEY_TEXT2, TestValues.GENERAL_STRING);
        	result.put(ShowConstantTbt.KEY_TIME_TO_DESTINATION, TestValues.GENERAL_STRING);
        	result.put(ShowConstantTbt.KEY_TOTAL_DISTANCE, TestValues.GENERAL_STRING);
        	result.put(ShowConstantTbt.KEY_MANEUVER_IMAGE, TestValues.GENERAL_IMAGE.serializeJSON());
        	result.put(ShowConstantTbt.KEY_NEXT_MANEUVER_IMAGE, TestValues.GENERAL_IMAGE.serializeJSON());
        } catch(JSONException e){
        	fail(TestValues.JSON_FAIL);
        }
        
		return result;
	}
	
	/**
	 * Tests the expected values of the RPC message.
	 */
	@Test
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
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testTimeToDestination);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_DOUBLE, testScale);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testNavText1);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testNavText2);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testEta);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testTotalDistance);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_DOUBLE, testDistance);
		assertTrue(TestValues.TRUE, testManeuverComplete);
		assertTrue(TestValues.TRUE, Validator.validateSoftButtons(TestValues.GENERAL_SOFTBUTTON_LIST, testSoftButtons));
		assertTrue(TestValues.TRUE, Validator.validateImage(TestValues.GENERAL_IMAGE, testTurnIcon));
		assertTrue(TestValues.TRUE, Validator.validateImage(TestValues.GENERAL_IMAGE, testNextTurnIcon));
		
		// Invalid/Null Tests
		ShowConstantTbt msg = new ShowConstantTbt();
		assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);
        
        assertNull(TestValues.NULL, msg.getSoftButtons());
        assertNull(TestValues.NULL, msg.getNavigationText1());
        assertNull(TestValues.NULL, msg.getNavigationText2());
        assertNull(TestValues.NULL, msg.getDistanceToManeuver());
        assertNull(TestValues.NULL, msg.getDistanceToManeuverScale());
        assertNull(TestValues.NULL, msg.getEta());
        assertNull(TestValues.NULL, msg.getManeuverComplete());
        assertNull(TestValues.NULL, msg.getNextTurnIcon());
        assertNull(TestValues.NULL, msg.getTimeToDestination());
        assertNull(TestValues.NULL, msg.getTotalDistance());
        assertNull(TestValues.NULL, msg.getTurnIcon());
	}
	
	/**
     * Tests a valid JSON construction of this RPC message.
     */
	@Test
	public void testJsonConstructor () {
		JSONObject commandJson = JsonFileReader.readId(getContext(), getCommandType(), getMessageType());
    	assertNotNull(TestValues.NOT_NULL, commandJson);
    	
    	try {
    		Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
    		ShowConstantTbt cmd = new ShowConstantTbt(hash);
    		JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(TestValues.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, ShowConstantTbt.KEY_ETA), cmd.getEta());
			assertEquals(TestValues.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, ShowConstantTbt.KEY_MANEUVER_COMPLETE), cmd.getManeuverComplete());
			assertEquals(TestValues.MATCH, JsonUtils.readDoubleFromJsonObject(parameters, ShowConstantTbt.KEY_MANEUVER_DISTANCE), cmd.getDistanceToManeuver());
			assertEquals(TestValues.MATCH, JsonUtils.readDoubleFromJsonObject(parameters, ShowConstantTbt.KEY_MANEUVER_DISTANCE_SCALE), cmd.getDistanceToManeuverScale());
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, ShowConstantTbt.KEY_TEXT1), cmd.getNavigationText1());
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, ShowConstantTbt.KEY_TEXT2), cmd.getNavigationText2());
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, ShowConstantTbt.KEY_TIME_TO_DESTINATION), cmd.getTimeToDestination());
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, ShowConstantTbt.KEY_TOTAL_DISTANCE), cmd.getTotalDistance());
			
			JSONObject icon1 = JsonUtils.readJsonObjectFromJsonObject(parameters, ShowConstantTbt.KEY_MANEUVER_IMAGE);
			Image refIcon1 = new Image(JsonRPCMarshaller.deserializeJSONObject(icon1));
			assertTrue(TestValues.TRUE, Validator.validateImage(refIcon1, cmd.getTurnIcon()));
			
			JSONObject icon2 = JsonUtils.readJsonObjectFromJsonObject(parameters, ShowConstantTbt.KEY_NEXT_MANEUVER_IMAGE);
			Image refIcon2 = new Image(JsonRPCMarshaller.deserializeJSONObject(icon2));
			assertTrue(TestValues.TRUE, Validator.validateImage(refIcon2, cmd.getNextTurnIcon()));
			
			JSONArray softButtonArray = JsonUtils.readJsonArrayFromJsonObject(parameters, ShowConstantTbt.KEY_SOFT_BUTTONS);
			List<SoftButton> softButtonList = new ArrayList<SoftButton>();
			for (int index = 0; index < softButtonArray.length(); index++) {
				SoftButton chunk = new SoftButton(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)softButtonArray.get(index)) );
				softButtonList.add(chunk);
			}
			assertTrue(TestValues.TRUE,  Validator.validateSoftButtons(softButtonList, cmd.getSoftButtons()));
    	} catch (JSONException e) {
    		fail(TestValues.JSON_FAIL);
		}
	}
}