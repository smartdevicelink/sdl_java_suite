package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.HapticRect;
import com.smartdevicelink.proxy.rpc.Rectangle;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by brettywhite on 8/24/17.
 */

public class HapticRectTests extends TestCase {

	private HapticRect msg;

	@Override
	public void setUp() {
		msg = new HapticRect();

		msg.setId(TestValues.GENERAL_INTEGER);
		msg.setRect(TestValues.GENERAL_RECTANGLE);
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues () {
		// Test Values
		Integer id = msg.getId();
		Rectangle rect = msg.getRect();

		// Valid Tests
		assertEquals(TestValues.MATCH, TestValues.GENERAL_INTEGER, id);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_RECTANGLE, rect);

		// Invalid/Null Tests
		HapticRect msg = new HapticRect();
		assertNotNull(TestValues.NOT_NULL, msg);

		assertNull(TestValues.NULL, msg.getId());
		assertNull(TestValues.NULL, msg.getRect());
	}

	public void testJson(){
		JSONObject reference = new JSONObject();

		try{
			reference.put(HapticRect.KEY_ID, TestValues.GENERAL_INTEGER);
			reference.put(HapticRect.KEY_RECT, TestValues.GENERAL_RECTANGLE);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(TestValues.MATCH, reference.length(), underTest.length());

			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(reference, HapticRect.KEY_ID),
					JsonUtils.readIntegerFromJsonObject(underTest, HapticRect.KEY_ID));

			assertTrue(Validator.validateRectangle(
					(Rectangle) JsonUtils.readObjectFromJsonObject(reference, HapticRect.KEY_RECT),
					new Rectangle(JsonRPCMarshaller.deserializeJSONObject( (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, HapticRect.KEY_RECT))))
			);

		} catch(JSONException e){
			fail(TestValues.JSON_FAIL);
		}
	}
}
