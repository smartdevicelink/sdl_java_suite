package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.DateTime;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.LocationDetails;
import com.smartdevicelink.proxy.rpc.NavigationInstruction;
import com.smartdevicelink.proxy.rpc.enums.Direction;
import com.smartdevicelink.proxy.rpc.enums.NavigationAction;
import com.smartdevicelink.proxy.rpc.enums.NavigationJunction;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.NavigationInstruction}
 */
public class NavigationInstructionTests extends TestCase {

	private NavigationInstruction msg;

	@Override
	public void setUp(){
		msg = new NavigationInstruction();

		msg.setLocationDetails(Test.GENERAL_LOCATIONDETAILS);
		msg.setAction(Test.GENERAL_NAVIGATIONACTION);
		msg.setEta(Test.GENERAL_DATETIME);
		msg.setBearing(Test.GENERAL_INTEGER);
		msg.setJunctionType(Test.GENERAL_NAVIGATION_JUNCTION);
		msg.setDrivingSide(Test.GENERAL_DIRECTION);
		msg.setDetails(Test.GENERAL_STRING);
		msg.setImage(Test.GENERAL_IMAGE);
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues () {
		// Test Values
		LocationDetails locationDetails = msg.getLocationDetails();
		NavigationAction action = msg.getAction();
		DateTime eta = msg.getEta();
		Integer bearing = msg.getBearing();
		NavigationJunction junctionType = msg.getJunctionType();
		Direction drivingSide = msg.getDrivingSide();
		String details = msg.getDetails();
		Image image = msg.getImage();

		// Valid Tests
		assertEquals(Test.GENERAL_LOCATIONDETAILS, locationDetails);
		assertEquals(Test.GENERAL_NAVIGATIONACTION, action);
		assertEquals(Test.GENERAL_DATETIME, eta);
		assertEquals(Test.GENERAL_INTEGER, bearing);
		assertEquals(Test.GENERAL_NAVIGATION_JUNCTION, junctionType);
		assertEquals(Test.GENERAL_DIRECTION, drivingSide);
		assertEquals(Test.GENERAL_STRING, details);
		assertEquals(Test.GENERAL_IMAGE, image);

		// Invalid/Null Tests
		NavigationInstruction msg = new NavigationInstruction();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getLocationDetails());
		assertNull(Test.NULL, msg.getAction());
		assertNull(Test.NULL, msg.getEta());
		assertNull(Test.NULL, msg.getBearing());
		assertNull(Test.NULL, msg.getJunctionType());
		assertNull(Test.NULL, msg.getDrivingSide());
		assertNull(Test.NULL, msg.getDetails());
		assertNull(Test.NULL, msg.getImage());
	}

	public void testRequiredConstructor(){
		NavigationInstruction msg = new NavigationInstruction(Test.GENERAL_LOCATIONDETAILS, Test.GENERAL_NAVIGATIONACTION);
		assertNotNull(Test.NOT_NULL, msg);

		LocationDetails locationDetails = msg.getLocationDetails();
		NavigationAction action = msg.getAction();

		assertEquals(Test.GENERAL_LOCATIONDETAILS, locationDetails);
		assertEquals(Test.GENERAL_NAVIGATIONACTION, action);
	}

	public void testJson(){
		JSONObject reference = new JSONObject();

		try{
			reference.put(NavigationInstruction.KEY_LOCATION_DETAILS, Test.GENERAL_LOCATIONDETAILS);
			reference.put(NavigationInstruction.KEY_ACTION, Test.GENERAL_NAVIGATIONACTION);
			reference.put(NavigationInstruction.KEY_ETA, Test.GENERAL_DATETIME);
			reference.put(NavigationInstruction.KEY_BEARING, Test.GENERAL_INTEGER);
			reference.put(NavigationInstruction.KEY_JUNCTION_TYPE, Test.GENERAL_NAVIGATION_JUNCTION);
			reference.put(NavigationInstruction.KEY_DRIVING_SIDE, Test.GENERAL_DIRECTION);
			reference.put(NavigationInstruction.KEY_DETAILS, Test.GENERAL_STRING);
			reference.put(NavigationInstruction.KEY_IMAGE, Test.GENERAL_IMAGE);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(Test.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while(iterator.hasNext()){
				String key = (String) iterator.next();

				if(key.equals(NavigationInstruction.KEY_IMAGE)){
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Image refIcon1 = new Image(JsonRPCMarshaller.deserializeJSONObject(testEquals));
					assertTrue(Test.TRUE, Validator.validateImage(refIcon1, msg.getImage()));
				}else if(key.equals(NavigationInstruction.KEY_LOCATION_DETAILS)){
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(Test.TRUE, Validator.validateLocationDetails( Test.GENERAL_LOCATIONDETAILS, new LocationDetails(hashTest)));
				}else if (key.equals(NavigationInstruction.KEY_ETA)){
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(Test.TRUE, Validator.validateDateTime(Test.GENERAL_DATETIME, new DateTime(hashTest)));
				}else {
					assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
				}
			}
		} catch(JSONException e){
			fail(Test.JSON_FAIL);
		}
	}
}