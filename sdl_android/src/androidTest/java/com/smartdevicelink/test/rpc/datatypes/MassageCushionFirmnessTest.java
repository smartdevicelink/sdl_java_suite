package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.MassageCushionFirmness;
import com.smartdevicelink.proxy.rpc.enums.MassageCushion;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.rpc.MassageCushionFirmness}
 */
public class MassageCushionFirmnessTest extends TestCase {

	private MassageCushionFirmness msg;

	@Override
	public void setUp() {
		msg = new MassageCushionFirmness();

		msg.setCushion(Test.GENERAL_MASSAGECUSHION);
		msg.setFirmness(Test.GENERAL_INT);
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues() {
		// Test Values
		MassageCushion cushion = msg.getCushion();
		Integer firmness = msg.getFirmness();

		// Valid Tests
		assertEquals(Test.MATCH, Test.GENERAL_MASSAGECUSHION, cushion);
		assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, firmness);

		// Invalid/Null Tests
		MassageCushionFirmness msg = new MassageCushionFirmness();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getCushion());
		assertNull(Test.NULL, msg.getFirmness());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(MassageCushionFirmness.KEY_CUSHION, Test.GENERAL_MASSAGECUSHION);
			reference.put(MassageCushionFirmness.KEY_FIRMNESS, Test.GENERAL_INT);

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