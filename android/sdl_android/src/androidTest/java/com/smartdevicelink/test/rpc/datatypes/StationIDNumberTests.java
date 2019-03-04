package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.StationIDNumber;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.rpc.StationIDNumber}
 */
public class StationIDNumberTests extends TestCase {

	private StationIDNumber msg;

	@Override
	public void setUp() {
		msg = new StationIDNumber();

		msg.setCountryCode(Test.GENERAL_INT);
		msg.setFccFacilityId(Test.GENERAL_INT);
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues() {
		// Test Values

		int countryCode = msg.getCountryCode();
		int fccFacilityId = msg.getFccFacilityId();

		// Valid Tests
		assertEquals(Test.MATCH, Test.GENERAL_INT, countryCode);
		assertEquals(Test.MATCH, Test.GENERAL_INT, fccFacilityId);

		// Invalid/Null Tests
		StationIDNumber msg = new StationIDNumber();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getCountryCode());
		assertNull(Test.NULL, msg.getFccFacilityId());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {

			reference.put(StationIDNumber.KEY_COUNTRY_CODE, Test.GENERAL_INT);
			reference.put(StationIDNumber.KEY_FCC_FACILITY_ID, Test.GENERAL_INT);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(Test.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();

				assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));

			}
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}
	}
}