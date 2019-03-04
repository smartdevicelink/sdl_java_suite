package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.DateTime;
import com.smartdevicelink.proxy.rpc.LocationDetails;
import com.smartdevicelink.proxy.rpc.NavigationInstruction;
import com.smartdevicelink.proxy.rpc.NavigationServiceData;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
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

		msg.setTimeStamp(Test.GENERAL_DATETIME);
		msg.setOrigin(Test.GENERAL_LOCATIONDETAILS);
		msg.setDestination(Test.GENERAL_LOCATIONDETAILS);
		msg.setDestinationETA(Test.GENERAL_DATETIME);
		msg.setInstructions(Test.GENERAL_NAVIGATION_INSTRUCTION_LIST);
		msg.setNextInstructionETA(Test.GENERAL_DATETIME);
		msg.setNextInstructionDistance(Test.GENERAL_FLOAT);
		msg.setNextInstructionDistanceScale(Test.GENERAL_FLOAT);
		msg.setPrompt(Test.GENERAL_STRING);
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
		assertEquals(Test.GENERAL_DATETIME, timestamp);
		assertEquals(Test.GENERAL_LOCATIONDETAILS, origin);
		assertEquals(Test.GENERAL_LOCATIONDETAILS, destination);
		assertEquals(Test.GENERAL_DATETIME, destinationETA);
		assertEquals(Test.GENERAL_NAVIGATION_INSTRUCTION_LIST, instructions);
		assertEquals(Test.GENERAL_DATETIME, nextInstructionETA);
		assertEquals(Test.GENERAL_FLOAT, nextInstructionDistance);
		assertEquals(Test.GENERAL_FLOAT, nextInstructionDistanceScale);
		assertEquals(Test.GENERAL_STRING, prompt);

		// Invalid/Null Tests
		NavigationServiceData msg = new NavigationServiceData();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getTimeStamp());
		assertNull(Test.NULL, msg.getOrigin());
		assertNull(Test.NULL, msg.getDestination());
		assertNull(Test.NULL, msg.getDestinationETA());
		assertNull(Test.NULL, msg.getInstructions());
		assertNull(Test.NULL, msg.getNextInstructionETA());
		assertNull(Test.NULL, msg.getNextInstructionDistance());
		assertNull(Test.NULL, msg.getNextInstructionDistanceScale());
		assertNull(Test.NULL, msg.getPrompt());
	}

	public void testRequiredConstructor(){
		NavigationServiceData msg = new NavigationServiceData(Test.GENERAL_DATETIME);
		assertNotNull(Test.NOT_NULL, msg);

		DateTime locationDetails = msg.getTimeStamp();

		assertEquals(Test.GENERAL_DATETIME, locationDetails);
	}

	public void testJson(){
		JSONObject reference = new JSONObject();

		try{
			reference.put(NavigationServiceData.KEY_TIMESTAMP, Test.GENERAL_DATETIME);
			reference.put(NavigationServiceData.KEY_ORIGIN, Test.GENERAL_LOCATIONDETAILS);
			reference.put(NavigationServiceData.KEY_DESTINATION, Test.GENERAL_LOCATIONDETAILS);
			reference.put(NavigationServiceData.KEY_DESTINATION_ETA, Test.GENERAL_DATETIME);
			reference.put(NavigationServiceData.KEY_INSTRUCTIONS, Test.GENERAL_NAVIGATION_INSTRUCTION_LIST);
			reference.put(NavigationServiceData.KEY_NEXT_INSTRUCTION_ETA, Test.GENERAL_DATETIME);
			reference.put(NavigationServiceData.KEY_NEXT_INSTRUCTION_DISTANCE, Test.GENERAL_FLOAT);
			reference.put(NavigationServiceData.KEY_NEXT_INSTRUCTION_DISTANCE_SCALE, Test.GENERAL_FLOAT);
			reference.put(NavigationServiceData.KEY_PROMPT, Test.GENERAL_STRING);


			JSONObject underTest = msg.serializeJSON();
			assertEquals(Test.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while(iterator.hasNext()){
				String key = (String) iterator.next();

				if(key.equals(NavigationServiceData.KEY_TIMESTAMP)||key.equals(NavigationServiceData.KEY_DESTINATION_ETA)||key.equals(NavigationServiceData.KEY_NEXT_INSTRUCTION_ETA)){
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(Test.TRUE, Validator.validateDateTime(Test.GENERAL_DATETIME, new DateTime(hashTest)));
				}else if(key.equals(NavigationServiceData.KEY_DESTINATION)||key.equals(NavigationServiceData.KEY_ORIGIN)){
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(Test.TRUE, Validator.validateLocationDetails( Test.GENERAL_LOCATIONDETAILS, new LocationDetails(hashTest)));
				}else if (key.equals(NavigationServiceData.KEY_INSTRUCTIONS)){
					JSONArray NavigationInstructionUnderTestListArrayObjTest = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
					List<NavigationInstruction> NavigationInstructionUnderTestList = new ArrayList<>();
					for (int index = 0; index < NavigationInstructionUnderTestListArrayObjTest.length(); index++) {
						NavigationInstruction NavigationInstructionData = new NavigationInstruction(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)NavigationInstructionUnderTestListArrayObjTest.get(index) ));
						NavigationInstructionUnderTestList.add(NavigationInstructionData);
					}
					assertTrue("JSON value didn't match expected value for key \"" + key + "\".",
							Validator.validateNavigationInstructionList(Test.GENERAL_NAVIGATION_INSTRUCTION_LIST, NavigationInstructionUnderTestList));
				}else {
					assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
				}
			}
		} catch(JSONException e){
			fail(Test.JSON_FAIL);
		}
	}
}