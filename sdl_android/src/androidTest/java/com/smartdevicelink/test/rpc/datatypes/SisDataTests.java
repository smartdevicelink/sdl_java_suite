package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.GPSData;
import com.smartdevicelink.proxy.rpc.SisData;
import com.smartdevicelink.proxy.rpc.StationIDNumber;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.VehicleDataHelper;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.rpc.SisData}
 */
public class SisDataTests extends TestCase {

	private SisData msg;

	@Override
	public void setUp() {
		msg = new SisData();

		msg.setStationShortName(Test.GENERAL_STRING);
		msg.setStationIDNumber(Test.GENERAL_STATIONIDNUMBER);
		msg.setStationLongName(Test.GENERAL_STRING);
		msg.setStationLocation(VehicleDataHelper.GPS);
		msg.setStationMessage(Test.GENERAL_STRING);
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues() {
		// Test Values
		String stationShortName = msg.getStationShortName();
		StationIDNumber stationIDNumber = msg.getStationIDNumber();
		String stationLongName = msg.getStationLongName();
		GPSData stationLocation = msg.getStationLocation();
		String stationMessage = msg.getStationMessage();

		// Valid Tests
		assertEquals(Test.MATCH, Test.GENERAL_STRING, stationShortName);
		assertEquals(Test.MATCH, Test.GENERAL_STATIONIDNUMBER, stationIDNumber);
		assertEquals(Test.MATCH, Test.GENERAL_STRING, stationLongName);
		assertEquals(Test.MATCH, VehicleDataHelper.GPS, stationLocation);
		assertEquals(Test.MATCH, Test.GENERAL_STRING, stationMessage);

		// Invalid/Null Tests
		SisData msg = new SisData();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getStationShortName());
		assertNull(Test.NULL, msg.getStationIDNumber());
		assertNull(Test.NULL, msg.getStationLongName());
		assertNull(Test.NULL, msg.getStationLocation());
		assertNull(Test.NULL, msg.getStationMessage());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(SisData.KEY_STATION_SHORT_NAME, Test.GENERAL_STRING);
			reference.put(SisData.KEY_STATION_ID_NUMBER, JsonRPCMarshaller.serializeHashtable(Test.GENERAL_STATIONIDNUMBER.getStore()));
			reference.put(SisData.KEY_STATION_LONG_NAME, Test.GENERAL_STRING);
			reference.put(SisData.KEY_STATION_LOCATION, JsonRPCMarshaller.serializeHashtable(VehicleDataHelper.GPS.getStore()));
			reference.put(SisData.KEY_STATION_MESSAGE, Test.GENERAL_STRING);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(Test.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();

				if (key.equals(SisData.KEY_STATION_ID_NUMBER)) {
					JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(Test.TRUE, Validator.validateStationIDNumber(new StationIDNumber(hashReference), new StationIDNumber(hashTest)));
				} else if (key.equals(SisData.KEY_STATION_LOCATION)) {
					JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(Test.TRUE, Validator.validateGpsData(new GPSData(hashReference), new GPSData(hashTest)));
				} else {
					assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
				}
			}
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}
	}
}