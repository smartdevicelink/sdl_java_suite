package com.smartdevicelink.test.rpc.requests;

import java.util.Hashtable;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.SendLocation;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.SendLocation}
 */
public class SendLocationTests extends BaseRpcTests {
	
    @Override
    protected RPCMessage createMessage(){
    	SendLocation msg = new SendLocation();
    	
    	msg.setLatitudeDegrees(Test.GENERAL_DOUBLE);
    	msg.setLongitudeDegrees(Test.GENERAL_DOUBLE);
    	msg.setLocationName(Test.GENERAL_STRING);
    	msg.setLocationDescription(Test.GENERAL_STRING);
    	msg.setPhoneNumber(Test.GENERAL_STRING);
    	msg.setAddressLines(Test.GENERAL_STRING_LIST);
    	msg.setLocationImage(Test.GENERAL_IMAGE);
    	
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
    		result.put(SendLocation.KEY_LAT_DEGREES, Test.GENERAL_DOUBLE);
    		result.put(SendLocation.KEY_LON_DEGREES, Test.GENERAL_DOUBLE);
    		result.put(SendLocation.KEY_LOCATION_NAME, Test.GENERAL_STRING);
    		result.put(SendLocation.KEY_LOCATION_DESCRIPTION, Test.GENERAL_STRING);
    		result.put(SendLocation.KEY_PHONE_NUMBER, Test.GENERAL_STRING);
    		result.put(SendLocation.KEY_LOCATION_IMAGE, Test.GENERAL_IMAGE.serializeJSON());
    		result.put(SendLocation.KEY_ADDRESS_LINES, JsonUtils.createJsonArray(Test.GENERAL_STRING_LIST));    		
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
    	Double latitude = ((SendLocation) msg).getLatitudeDegrees();
        Double longitude = ((SendLocation) msg).getLongitudeDegrees();
        String locationName = ((SendLocation) msg).getLocationName();
    	String locationDesc = ((SendLocation) msg).getLocationDescription();
    	String phoneNumber = ((SendLocation) msg).getPhoneNumber();
    	List<String> addressLines = ((SendLocation) msg).getAddressLines();
    	Image locationImage = ((SendLocation) msg).getLocationImage();
    	
    	// Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_DOUBLE, longitude);
        assertEquals(Test.MATCH, Test.GENERAL_DOUBLE, latitude);
    	assertEquals(Test.MATCH, Test.GENERAL_STRING, locationDesc);
    	assertEquals(Test.MATCH, Test.GENERAL_STRING, locationName);
    	assertEquals(Test.MATCH, Test.GENERAL_STRING, phoneNumber);
    	assertTrue(Test.TRUE, Validator.validateStringList(Test.GENERAL_STRING_LIST, addressLines));
    	assertTrue(Test.TRUE, Validator.validateImage(Test.GENERAL_IMAGE, locationImage));
    
    	// Invalid/Null Tests
    	SendLocation msg = new SendLocation();
    	assertNotNull(Test.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(Test.NULL, msg.getLatitudeDegrees());
        assertNull(Test.NULL, msg.getLongitudeDegrees());
        assertNull(Test.NULL, msg.getLocationName());
        assertNull(Test.NULL, msg.getLocationDescription());
        assertNull(Test.NULL, msg.getPhoneNumber());
        assertNull(Test.NULL, msg.getAddressLines());
        assertNull(Test.NULL, msg.getLocationImage());
    }

    /**
     * Tests a valid JSON construction of this RPC message.
     */
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
    	assertNotNull("Command object is null", commandJson);
    	
    	try {
    		Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
    		SendLocation cmd = new SendLocation(hash);
    		assertNotNull(Test.NOT_NULL, cmd);
    		
    		JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
    		assertNotNull(Test.NOT_NULL, body);
    		
    		// Test everything in the json body.
    		assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());
    		
			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals(Test.MATCH, JsonUtils.readDoubleFromJsonObject(parameters, SendLocation.KEY_LAT_DEGREES), cmd.getLatitudeDegrees());
			assertEquals(Test.MATCH, JsonUtils.readDoubleFromJsonObject(parameters, SendLocation.KEY_LON_DEGREES), cmd.getLongitudeDegrees());
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, SendLocation.KEY_LOCATION_NAME), cmd.getLocationName());
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, SendLocation.KEY_LOCATION_DESCRIPTION), cmd.getLocationDescription());
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, SendLocation.KEY_PHONE_NUMBER), cmd.getPhoneNumber());
			
			List<String> addressList = JsonUtils.readStringListFromJsonObject(parameters, SendLocation.KEY_ADDRESS_LINES);
			List<String> testList = cmd.getAddressLines();
			assertEquals(Test.MATCH, addressList.size(), testList.size());
			assertTrue(Test.TRUE, Validator.validateStringList(addressList, testList));
			
			JSONObject cmdIcon = JsonUtils.readJsonObjectFromJsonObject(parameters, SendLocation.KEY_LOCATION_IMAGE);
			Image reference = new Image(JsonRPCMarshaller.deserializeJSONObject(cmdIcon));
			assertTrue(Test.TRUE, Validator.validateImage(reference, cmd.getLocationImage()));
			
    	} catch (JSONException e) {
    		fail(Test.JSON_FAIL);
		}
    }
}