package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.DateTime;
import com.smartdevicelink.proxy.rpc.LocationDetails;
import com.smartdevicelink.proxy.rpc.NavigationInstruction;
import com.smartdevicelink.proxy.rpc.NavigationServiceData;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.NavigationServiceData}
 */
public class NavigationServiceDataTests extends TestCase {

	private NavigationServiceData msg;

	@Override
	public void setUp(){
		msg = new NavigationServiceData();

		msg.setTimeStamp(TestValues.GENERAL_DATETIME);
		msg.setOrigin(TestValues.GENERAL_LOCATIONDETAILS);
		msg.setDestination(TestValues.GENERAL_LOCATIONDETAILS);
		msg.setDestinationETA(TestValues.GENERAL_DATETIME);
		msg.setInstructions(TestValues.GENERAL_NAVIGATION_INSTRUCTION_LIST);
		msg.setNextInstructionETA(TestValues.GENERAL_DATETIME);
		msg.setNextInstructionDistance(TestValues.GENERAL_FLOAT);
		msg.setNextInstructionDistanceScale(TestValues.GENERAL_FLOAT);
		msg.setPrompt(TestValues.GENERAL_STRING);
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues () {
		// Test Values
		DateTime timestamp = msg.getTimeStamp();
		LocationDetails origin = msg.getOrigin();
		LocationDetails destination = msg.getDestination();
		DateTime destinationETA = msg.getDestinationETA();
		List<NavigationInstruction> instructions = msg.getInstructions();
		DateTime nextInstructionETA = msg.getNextInstructionETA();
		Float nextInstructionDistance = msg.getNextInstructionDistance();
		Float nextInstructionDistanceScale = msg.getNextInstructionDistanceScale();
		String prompt = msg.getPrompt();

		// Valid Tests
		assertEquals(TestValues.GENERAL_DATETIME, timestamp);
		assertEquals(TestValues.GENERAL_LOCATIONDETAILS, origin);
		assertEquals(TestValues.GENERAL_LOCATIONDETAILS, destination);
		assertEquals(TestValues.GENERAL_DATETIME, destinationETA);
		assertEquals(TestValues.GENERAL_NAVIGATION_INSTRUCTION_LIST, instructions);
		assertEquals(TestValues.GENERAL_DATETIME, nextInstructionETA);
		assertEquals(TestValues.GENERAL_FLOAT, nextInstructionDistance);
		assertEquals(TestValues.GENERAL_FLOAT, nextInstructionDistanceScale);
		assertEquals(TestValues.GENERAL_STRING, prompt);

		// Invalid/Null Tests
		NavigationServiceData msg = new NavigationServiceData();
		assertNotNull(TestValues.NOT_NULL, msg);

		assertNull(TestValues.NULL, msg.getTimeStamp());
		assertNull(TestValues.NULL, msg.getOrigin());
		assertNull(TestValues.NULL, msg.getDestination());
		assertNull(TestValues.NULL, msg.getDestinationETA());
		assertNull(TestValues.NULL, msg.getInstructions());
		assertNull(TestValues.NULL, msg.getNextInstructionETA());
		assertNull(TestValues.NULL, msg.getNextInstructionDistance());
		assertNull(TestValues.NULL, msg.getNextInstructionDistanceScale());
		assertNull(TestValues.NULL, msg.getPrompt());
	}

	public void testRequiredConstructor(){
		NavigationServiceData msg = new NavigationServiceData(TestValues.GENERAL_DATETIME);
		assertNotNull(TestValues.NOT_NULL, msg);

		DateTime locationDetails = msg.getTimeStamp();

		assertEquals(TestValues.GENERAL_DATETIME, locationDetails);
	}

	public void testJson(){
		JSONObject reference = new JSONObject();

		try{
			reference.put(NavigationServiceData.KEY_TIMESTAMP, TestValues.GENERAL_DATETIME);
			reference.put(NavigationServiceData.KEY_ORIGIN, TestValues.GENERAL_LOCATIONDETAILS);
			reference.put(NavigationServiceData.KEY_DESTINATION, TestValues.GENERAL_LOCATIONDETAILS);
			reference.put(NavigationServiceData.KEY_DESTINATION_ETA, TestValues.GENERAL_DATETIME);
			reference.put(NavigationServiceData.KEY_INSTRUCTIONS, TestValues.GENERAL_NAVIGATION_INSTRUCTION_LIST);
			reference.put(NavigationServiceData.KEY_NEXT_INSTRUCTION_ETA, TestValues.GENERAL_DATETIME);
			reference.put(NavigationServiceData.KEY_NEXT_INSTRUCTION_DISTANCE, TestValues.GENERAL_FLOAT);
			reference.put(NavigationServiceData.KEY_NEXT_INSTRUCTION_DISTANCE_SCALE, TestValues.GENERAL_FLOAT);
			reference.put(NavigationServiceData.KEY_PROMPT, TestValues.GENERAL_STRING);


			JSONObject underTest = msg.serializeJSON();
			assertEquals(TestValues.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while(iterator.hasNext()){
				String key = (String) iterator.next();

				if(key.equals(NavigationServiceData.KEY_TIMESTAMP)||key.equals(NavigationServiceData.KEY_DESTINATION_ETA)||key.equals(NavigationServiceData.KEY_NEXT_INSTRUCTION_ETA)){
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(TestValues.TRUE, Validator.validateDateTime(TestValues.GENERAL_DATETIME, new DateTime(hashTest)));
				}else if(key.equals(NavigationServiceData.KEY_DESTINATION)||key.equals(NavigationServiceData.KEY_ORIGIN)){
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(TestValues.TRUE, Validator.validateLocationDetails( TestValues.GENERAL_LOCATIONDETAILS, new LocationDetails(hashTest)));
				}else if (key.equals(NavigationServiceData.KEY_INSTRUCTIONS)){
					JSONArray NavigationInstructionUnderTestListArrayObjTest = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
					List<NavigationInstruction> NavigationInstructionUnderTestList = new ArrayList<>();
					for (int index = 0; index < NavigationInstructionUnderTestListArrayObjTest.length(); index++) {
						NavigationInstruction NavigationInstructionData = new NavigationInstruction(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)NavigationInstructionUnderTestListArrayObjTest.get(index) ));
						NavigationInstructionUnderTestList.add(NavigationInstructionData);
					}
					assertTrue("JSON value didn't match expected value for key \"" + key + "\".",
							Validator.validateNavigationInstructionList(TestValues.GENERAL_NAVIGATION_INSTRUCTION_LIST, NavigationInstructionUnderTestList));
				}else {
					assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
				}
			}
		} catch(JSONException e){
			fail(TestValues.JSON_FAIL);
		}
	}
}