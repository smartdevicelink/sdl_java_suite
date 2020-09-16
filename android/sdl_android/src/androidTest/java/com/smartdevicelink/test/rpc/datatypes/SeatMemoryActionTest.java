package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.SeatMemoryAction;
import com.smartdevicelink.proxy.rpc.enums.SeatMemoryActionType;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.SeatMemoryAction}
 */
public class SeatMemoryActionTest extends TestCase {

	private SeatMemoryAction msg;

	@Override
	public void setUp() {
		msg = new SeatMemoryAction();

		msg.setId(TestValues.GENERAL_INT);
		msg.setLabel(TestValues.GENERAL_STRING);
		msg.setAction(TestValues.GENERAL_SEATMEMORYACTIONTYPE);
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues() {
		// Test Values
		Integer id = msg.getId();
		String label = msg.getLabel();
		SeatMemoryActionType action = msg.getAction();

		// Valid Tests
		assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, id);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, label);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_SEATMEMORYACTIONTYPE, action);

		// Invalid/Null Tests
		SeatMemoryAction msg = new SeatMemoryAction();
		assertNotNull(TestValues.NOT_NULL, msg);

		assertNull(TestValues.NULL, msg.getId());
		assertNull(TestValues.NULL, msg.getLabel());
		assertNull(TestValues.NULL, msg.getAction());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(SeatMemoryAction.KEY_ID, TestValues.GENERAL_INT);
			reference.put(SeatMemoryAction.KEY_LABEL, TestValues.GENERAL_STRING);
			reference.put(SeatMemoryAction.KEY_ACTION, TestValues.GENERAL_SEATMEMORYACTIONTYPE);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(TestValues.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
			}
		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
		}
	}
}