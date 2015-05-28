package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.VehicleType;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

public class VehicleTypeTest extends TestCase {
	
	private VehicleType msg;

	@Override
	public void setUp() {
		msg = new VehicleType();
		
		msg.setModel(Test.GENERAL_STRING);
		msg.setMake(Test.GENERAL_STRING);
		msg.setTrim(Test.GENERAL_STRING);
		msg.setModelYear(Test.GENERAL_STRING);
	}

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
		// Test Values
		String year = msg.getModelYear();
		String trim = msg.getTrim();
		String make = msg.getMake();
		String model = msg.getModel();
		
		// Valid Tests
		assertEquals(Test.MATCH, Test.GENERAL_STRING, year);
		assertEquals(Test.MATCH, Test.GENERAL_STRING, model);
		assertEquals(Test.MATCH, Test.GENERAL_STRING, make);
		assertEquals(Test.MATCH, Test.GENERAL_STRING, trim);		
		
		// Invalid/Null Tests
		VehicleType msg = new VehicleType();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getModel());
		assertNull(Test.NULL, msg.getMake());
		assertNull(Test.NULL, msg.getModelYear());
		assertNull(Test.NULL, msg.getTrim());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(VehicleType.KEY_MODEL, Test.GENERAL_STRING);
			reference.put(VehicleType.KEY_MAKE, Test.GENERAL_STRING);
			reference.put(VehicleType.KEY_MODEL_YEAR, Test.GENERAL_STRING);
			reference.put(VehicleType.KEY_TRIM, Test.GENERAL_STRING);

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