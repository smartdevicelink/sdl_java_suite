package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.MassageModeData;
import com.smartdevicelink.proxy.rpc.enums.MassageMode;
import com.smartdevicelink.proxy.rpc.enums.MassageZone;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.rpc.MassageModeData}
 */
public class MassageModeDataTest extends TestCase {

	private MassageModeData msg;

	@Override
	public void setUp() {
		msg = new MassageModeData();

		msg.setMassageZone(Test.GENERAL_MASSAGEZONE);
		msg.setMassageMode(Test.GENERAL_MASSAGEMODE);
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues() {
		// Test Values
		MassageZone massageZone = msg.getMassageZone();
		MassageMode massageMode = msg.getMassageMode();

		// Valid Tests
		assertEquals(Test.MATCH, Test.GENERAL_MASSAGEZONE, massageZone);
		assertEquals(Test.MATCH, Test.GENERAL_MASSAGEMODE, massageMode);


		// Invalid/Null Tests
		MassageModeData msg = new MassageModeData();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getMassageMode());
		assertNull(Test.NULL, msg.getMassageZone());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(MassageModeData.KEY_MASSAGE_MODE, Test.GENERAL_MASSAGEMODE);
			reference.put(MassageModeData.KEY_MASSAGE_ZONE, Test.GENERAL_MASSAGEZONE);

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