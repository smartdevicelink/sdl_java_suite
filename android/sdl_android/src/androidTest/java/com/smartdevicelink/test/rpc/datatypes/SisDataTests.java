package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.GPSData;
import com.smartdevicelink.proxy.rpc.SisData;
import com.smartdevicelink.proxy.rpc.StationIDNumber;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.VehicleDataHelper;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.SisData}
 */
public class SisDataTests extends TestCase {

	private SisData msg;

	@Override
	public void setUp() {
		msg = new SisData();

		msg.setStationShortName(TestValues.GENERAL_STRING);
		msg.setStationIDNumber(TestValues.GENERAL_STATIONIDNUMBER);
		msg.setStationLongName(TestValues.GENERAL_STRING);
		msg.setStationLocation(VehicleDataHelper.GPS);
		msg.setStationMessage(TestValues.GENERAL_STRING);
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
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, stationShortName);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STATIONIDNUMBER, stationIDNumber);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, stationLongName);
		assertEquals(TestValues.MATCH, VehicleDataHelper.GPS, stationLocation);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, stationMessage);

		// Invalid/Null Tests
		SisData msg = new SisData();
		assertNotNull(TestValues.NOT_NULL, msg);

		assertNull(TestValues.NULL, msg.getStationShortName());
		assertNull(TestValues.NULL, msg.getStationIDNumber());
		assertNull(TestValues.NULL, msg.getStationLongName());
		assertNull(TestValues.NULL, msg.getStationLocation());
		assertNull(TestValues.NULL, msg.getStationMessage());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(SisData.KEY_STATION_SHORT_NAME, TestValues.GENERAL_STRING);
			reference.put(SisData.KEY_STATION_ID_NUMBER, JsonRPCMarshaller.serializeHashtable(TestValues.GENERAL_STATIONIDNUMBER.getStore()));
			reference.put(SisData.KEY_STATION_LONG_NAME, TestValues.GENERAL_STRING);
			reference.put(SisData.KEY_STATION_LOCATION, JsonRPCMarshaller.serializeHashtable(VehicleDataHelper.GPS.getStore()));
			reference.put(SisData.KEY_STATION_MESSAGE, TestValues.GENERAL_STRING);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(TestValues.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();

				if (key.equals(SisData.KEY_STATION_ID_NUMBER)) {
					JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(TestValues.TRUE, Validator.validateStationIDNumber(new StationIDNumber(hashReference), new StationIDNumber(hashTest)));
				} else if (key.equals(SisData.KEY_STATION_LOCATION)) {
					JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(TestValues.TRUE, Validator.validateGpsData(new GPSData(hashReference), new GPSData(hashTest)));
				} else {
					assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
				}
			}
		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
		}
	}
}