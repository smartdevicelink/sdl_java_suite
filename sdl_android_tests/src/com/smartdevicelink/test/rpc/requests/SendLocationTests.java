package com.smartdevicelink.test.rpc.requests;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.SendLocation;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.json.rpc.JsonFileReader;
import com.smartdevicelink.test.utils.JsonUtils;
import com.smartdevicelink.test.utils.Validator;

public class SendLocationTests extends BaseRpcTests {
	
	public static final String KEY_LAT_DEGREES          = "42";
    public static final String KEY_LON_DEGREES          = "42";
    public static final String KEY_LOCATION_NAME        = "test";
    public static final String KEY_LOCATION_DESCRIPTION = "desc";
    public static final String KEY_PHONE_NUMBER         = "1234567890";    
    public static final List<String> KEY_ADDRESS_LINES = Arrays.asList(new String[]{"1234 Test Lane", "Ferndale", "MI", "56789"});
    private static final Image IMAGE = new Image();
    private static final String VALUE = "image.jpg";
    private static final ImageType IMAGE_TYPE = ImageType.STATIC;
    
    @Override
    protected RPCMessage createMessage(){
    	SendLocation msg = new SendLocation();
    	
    	IMAGE.setValue(VALUE);
		IMAGE.setImageType(IMAGE_TYPE);
    	
    	msg.setLatitudeDegrees(Float.parseFloat(KEY_LAT_DEGREES));
    	msg.setLongitudeDegrees(Float.parseFloat(KEY_LON_DEGREES));
    	msg.setLocationName(KEY_LOCATION_NAME);
    	msg.setLocationDescription(KEY_LOCATION_DESCRIPTION);
    	msg.setPhoneNumber(KEY_PHONE_NUMBER);
    	msg.setAddressLines(KEY_ADDRESS_LINES);
    	msg.setLocationImage(IMAGE);
    	
    	return msg;
    }
    
    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_REQUEST;
    }
    
    @Override
    protected String getCommandType(){
        return FunctionID.SEND_LOCATION;
    }
    
    @Override
    protected JSONObject getExpectedParameters (int sdlVersion){
    	JSONObject result = new JSONObject();
    	
    	try {
    		result.put(SendLocation.KEY_LAT_DEGREES, KEY_LAT_DEGREES);
    		result.put(SendLocation.KEY_LON_DEGREES, KEY_LON_DEGREES);
    		result.put(SendLocation.KEY_LOCATION_NAME, KEY_LOCATION_NAME);
    		result.put(SendLocation.KEY_LOCATION_DESCRIPTION, KEY_LOCATION_DESCRIPTION);
    		result.put(SendLocation.KEY_PHONE_NUMBER, KEY_PHONE_NUMBER);
    		result.put(SendLocation.KEY_LOCATION_IMAGE, IMAGE.serializeJSON());
    		result.put(SendLocation.KEY_ADDRESS_LINES, JsonUtils.createJsonArray(KEY_ADDRESS_LINES));    		
    	} catch(JSONException e){
            /* do nothing */
        }
    	
    	return result;
    }
    
    public void testLatitudeDegrees(){
    	Float latitude = ((SendLocation) msg).getLatitudeDegrees();
        assertEquals("Latitude didn't match input latitude.", Float.parseFloat(KEY_LAT_DEGREES), latitude);
    }
    
    public void testLongitudeDegrees() {
    	Float longitude = ((SendLocation) msg).getLatitudeDegrees();
        assertEquals("Longitude didn't match input longitude.", Float.parseFloat(KEY_LON_DEGREES), longitude);
    }
    
    public void testLocationName () {
    	String locationName = ((SendLocation) msg).getLocationName();
    	assertEquals("Location name didn't match input location name.", KEY_LOCATION_NAME, locationName);
    }
    
    public void testLocationDescription () {
    	String locationDesc = ((SendLocation) msg).getLocationDescription();
    	assertEquals("Location description didn't match input location name.", KEY_LOCATION_DESCRIPTION, locationDesc);
    }
    
    public void testPhoneNumber () {
    	String phoneNumber = ((SendLocation) msg).getPhoneNumber();
    	assertEquals("Phone number didn't match input phone number.", KEY_PHONE_NUMBER, phoneNumber);
    }
    
    public void testAddressLines () {
    	List<String> addressLines = ((SendLocation) msg).getAddressLines();
    	assertTrue("Address didn't match input address.", Validator.validateStringList(KEY_ADDRESS_LINES, addressLines));
    }
    
    public void testLocationImage () {
    	Image locationImage = ((SendLocation) msg).getLocationImage();
    	assertTrue("Image didn't match input address.", Validator.validateImage(IMAGE, locationImage));
    }
    
    public void testNull(){
    	SendLocation msg = new SendLocation();
    	assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);

        assertNull("Latitude wasn't set, but getter method returned an object.", msg.getLatitudeDegrees());
        assertNull("Longitude wasn't set, but getter method returned an object.", msg.getLongitudeDegrees());
        assertNull("Location name wasn't set, but getter method returned an object.", msg.getLocationName());
        assertNull("Location description wasn't set, but getter method returned an object.", msg.getLocationDescription());
        assertNull("Phone number wasn't set, but getter method returned an object.", msg.getPhoneNumber());
        assertNull("Address lines wasn't set, but getter method returned an object.", msg.getAddressLines());
        assertNull("Location image wasn't set, but getter method returned an object.", msg.getLocationImage());
    }
    
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(getCommandType(), getMessageType());
    	assertNotNull("Command object is null", commandJson);
    	
    	try {
    		Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
    		SendLocation cmd = new SendLocation(hash);
    		
    		JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
    		assertNotNull("Command type doesn't match expected message type", body);
    		
    		// test everything in the body
    		assertEquals("Command name doesn't match input name", JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals("Correlation ID doesn't match input ID", JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());
    		
			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals("Latitude doesn't match input latitude.", Float.parseFloat(JsonUtils.readStringFromJsonObject(parameters, SendLocation.KEY_LAT_DEGREES)), cmd.getLatitudeDegrees());
			assertEquals("Longitude doesn't match input longitude.", Float.parseFloat(JsonUtils.readStringFromJsonObject(parameters, SendLocation.KEY_LON_DEGREES)), cmd.getLongitudeDegrees());
			assertEquals("Location name doesn't match input location name.", JsonUtils.readStringFromJsonObject(parameters, SendLocation.KEY_LOCATION_NAME), cmd.getLocationName());
			assertEquals("Location description doesn't match input location description.", JsonUtils.readStringFromJsonObject(parameters, SendLocation.KEY_LOCATION_DESCRIPTION), cmd.getLocationDescription());
			assertEquals("Phone number doesn't match input phone number.", JsonUtils.readStringFromJsonObject(parameters, SendLocation.KEY_PHONE_NUMBER), cmd.getPhoneNumber());
			
			List<String> addressList = JsonUtils.readStringListFromJsonObject(parameters, SendLocation.KEY_ADDRESS_LINES);
			List<String> testList = cmd.getAddressLines();
			assertEquals("Address list length not the same as reference address list length", addressList.size(), testList.size());
			assertTrue("Address list doesn't match input address list", Validator.validateStringList(addressList, testList));
			
			JSONObject cmdIcon = JsonUtils.readJsonObjectFromJsonObject(parameters, SendLocation.KEY_LOCATION_IMAGE);
			Image reference = new Image(JsonRPCMarshaller.deserializeJSONObject(cmdIcon));
			assertTrue("Image doesn't match expected image", Validator.validateImage(reference, cmd.getLocationImage()));
			
    	} catch (JSONException e) {
			e.printStackTrace();
		}
    }
}
