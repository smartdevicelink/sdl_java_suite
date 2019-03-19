package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.DateTime;
import com.smartdevicelink.proxy.rpc.WeatherAlert;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.WeatherAlert}
 */
public class WeatherAlertTests extends TestCase {

	private WeatherAlert msg;

	@Override
	public void setUp(){

		msg = new WeatherAlert();
		msg.setExpires(Test.GENERAL_DATETIME);
		msg.setTimeIssued(Test.GENERAL_DATETIME);
		msg.setRegions(Test.GENERAL_STRING_LIST);
		msg.setSeverity(Test.GENERAL_STRING);
		msg.setSummary(Test.GENERAL_STRING);
		msg.setTitle(Test.GENERAL_STRING);
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues () {
		// Test Values
		DateTime expires = msg.getExpires();
		DateTime issued = msg.getTimeIssued();
		List<String> regions = msg.getRegions();
		String severity = msg.getSeverity();
		String summary = msg.getSummary();
		String title = msg.getTitle();

		// Valid Tests
		assertEquals(Test.MATCH, expires, Test.GENERAL_DATETIME);
		assertEquals(Test.MATCH, issued, Test.GENERAL_DATETIME);
		assertEquals(Test.MATCH, regions, Test.GENERAL_STRING_LIST);
		assertEquals(Test.MATCH, severity, Test.GENERAL_STRING);
		assertEquals(Test.MATCH, summary, Test.GENERAL_STRING);
		assertEquals(Test.MATCH, title, Test.GENERAL_STRING);

		// Invalid/Null Tests
		WeatherAlert msg = new WeatherAlert();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getExpires());
		assertNull(Test.NULL, msg.getTimeIssued());
		assertNull(Test.NULL, msg.getRegions());
		assertNull(Test.NULL, msg.getSeverity());
		assertNull(Test.NULL, msg.getSummary());
		assertNull(Test.NULL, msg.getTitle());
	}

	public void testRequiredParamsConstructor(){
		msg = new WeatherAlert(Test.GENERAL_STRING_LIST);
		List<String> regions = msg.getRegions();
		assertEquals(Test.MATCH, regions, Test.GENERAL_STRING_LIST);
	}

	public void testJson(){
		JSONObject reference = new JSONObject();

		try{
			reference.put(WeatherAlert.KEY_EXPIRES, Test.GENERAL_DATETIME);
			reference.put(WeatherAlert.KEY_TIME_ISSUED, Test.GENERAL_DATETIME);
			reference.put(WeatherAlert.KEY_REGIONS, JsonUtils.createJsonArray(Test.GENERAL_STRING_LIST));
			reference.put(WeatherAlert.KEY_SEVERITY, Test.GENERAL_STRING);
			reference.put(WeatherAlert.KEY_SUMMARY, Test.GENERAL_STRING);
			reference.put(WeatherAlert.KEY_TITLE, Test.GENERAL_STRING);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(Test.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while(iterator.hasNext()) {
				String key = (String) iterator.next();
				if (key.equals(WeatherAlert.KEY_EXPIRES)||key.equals(WeatherAlert.KEY_TIME_ISSUED)){
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(Test.TRUE, Validator.validateDateTime(Test.GENERAL_DATETIME, new DateTime(hashTest)));
				} else if (key.equals(WeatherAlert.KEY_REGIONS)){
					JSONArray referenceArray = JsonUtils.readJsonArrayFromJsonObject(reference, key);
					JSONArray underTestArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
					assertEquals(Test.MATCH, referenceArray.length(), underTestArray.length());
					assertTrue(Test.TRUE, Validator.validateStringList(JsonUtils.readStringListFromJsonObject(reference, key), JsonUtils.readStringListFromJsonObject(underTest, key)));
				} else {
					assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
				}
			}
		} catch(JSONException e){
			fail(Test.JSON_FAIL);
		}
	}
}