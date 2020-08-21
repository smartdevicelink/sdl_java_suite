package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.SendLocation;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Hashtable;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.SendLocation}
 */
public class SendLocationTests extends BaseRpcTests {
	
    @Override
    protected RPCMessage createMessage(){
    	SendLocation msg = new SendLocation();
    	
    	msg.setLatitudeDegrees(TestValues.GENERAL_DOUBLE);
    	msg.setLongitudeDegrees(TestValues.GENERAL_DOUBLE);
    	msg.setLocationName(TestValues.GENERAL_STRING);
    	msg.setLocationDescription(TestValues.GENERAL_STRING);
    	msg.setPhoneNumber(TestValues.GENERAL_STRING);
    	msg.setAddressLines(TestValues.GENERAL_STRING_LIST);
    	msg.setLocationImage(TestValues.GENERAL_IMAGE);
    	
    	return msg;
    }
    
    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_REQUEST;
    }
    
    @Override
    protected String getCommandType(){
        return FunctionID.SEND_LOCATION.toString();
    }
    
    @Override
    protected JSONObject getExpectedParameters (int sdlVersion){
    	JSONObject result = new JSONObject();
    	
    	try {
    		result.put(SendLocation.KEY_LAT_DEGREES, TestValues.GENERAL_DOUBLE);
    		result.put(SendLocation.KEY_LON_DEGREES, TestValues.GENERAL_DOUBLE);
    		result.put(SendLocation.KEY_LOCATION_NAME, TestValues.GENERAL_STRING);
    		result.put(SendLocation.KEY_LOCATION_DESCRIPTION, TestValues.GENERAL_STRING);
    		result.put(SendLocation.KEY_PHONE_NUMBER, TestValues.GENERAL_STRING);
    		result.put(SendLocation.KEY_LOCATION_IMAGE, TestValues.GENERAL_IMAGE.serializeJSON());
    		result.put(SendLocation.KEY_ADDRESS_LINES, JsonUtils.createJsonArray(TestValues.GENERAL_STRING_LIST));
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
    	Double latitude = ((SendLocation) msg).getLatitudeDegrees();
        Double longitude = ((SendLocation) msg).getLongitudeDegrees();
        String locationName = ((SendLocation) msg).getLocationName();
    	String locationDesc = ((SendLocation) msg).getLocationDescription();
    	String phoneNumber = ((SendLocation) msg).getPhoneNumber();
    	List<String> addressLines = ((SendLocation) msg).getAddressLines();
    	Image locationImage = ((SendLocation) msg).getLocationImage();
    	
    	// Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_DOUBLE, longitude);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_DOUBLE, latitude);
    	assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, locationDesc);
    	assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, locationName);
    	assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, phoneNumber);
    	assertTrue(TestValues.TRUE, Validator.validateStringList(TestValues.GENERAL_STRING_LIST, addressLines));
    	assertTrue(TestValues.TRUE, Validator.validateImage(TestValues.GENERAL_IMAGE, locationImage));
    
    	// Invalid/Null Tests
    	SendLocation msg = new SendLocation();
    	assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getLatitudeDegrees());
        assertNull(TestValues.NULL, msg.getLongitudeDegrees());
        assertNull(TestValues.NULL, msg.getLocationName());
        assertNull(TestValues.NULL, msg.getLocationDescription());
        assertNull(TestValues.NULL, msg.getPhoneNumber());
        assertNull(TestValues.NULL, msg.getAddressLines());
        assertNull(TestValues.NULL, msg.getLocationImage());
    }

    /**
     * Tests a valid JSON construction of this RPC message.
     */
    @Test
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(getInstrumentation().getTargetContext(), getCommandType(), getMessageType());
    	assertNotNull("Command object is null", commandJson);
    	
    	try {
    		Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
    		SendLocation cmd = new SendLocation(hash);
    		assertNotNull(TestValues.NOT_NULL, cmd);
    		
    		JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
    		assertNotNull(TestValues.NOT_NULL, body);
    		
    		// Test everything in the json body.
    		assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());
    		
			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals(TestValues.MATCH, JsonUtils.readDoubleFromJsonObject(parameters, SendLocation.KEY_LAT_DEGREES), cmd.getLatitudeDegrees());
			assertEquals(TestValues.MATCH, JsonUtils.readDoubleFromJsonObject(parameters, SendLocation.KEY_LON_DEGREES), cmd.getLongitudeDegrees());
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, SendLocation.KEY_LOCATION_NAME), cmd.getLocationName());
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, SendLocation.KEY_LOCATION_DESCRIPTION), cmd.getLocationDescription());
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, SendLocation.KEY_PHONE_NUMBER), cmd.getPhoneNumber());
			
			List<String> addressList = JsonUtils.readStringListFromJsonObject(parameters, SendLocation.KEY_ADDRESS_LINES);
			List<String> testList = cmd.getAddressLines();
			assertEquals(TestValues.MATCH, addressList.size(), testList.size());
			assertTrue(TestValues.TRUE, Validator.validateStringList(addressList, testList));
			
			JSONObject cmdIcon = JsonUtils.readJsonObjectFromJsonObject(parameters, SendLocation.KEY_LOCATION_IMAGE);
			Image reference = new Image(JsonRPCMarshaller.deserializeJSONObject(cmdIcon));
			assertTrue(TestValues.TRUE, Validator.validateImage(reference, cmd.getLocationImage()));
			
    	} catch (JSONException e) {
    		fail(TestValues.JSON_FAIL);
		}
    }
}