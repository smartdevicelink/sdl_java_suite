package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.SeatMemoryAction;
import com.smartdevicelink.proxy.rpc.enums.SeatMemoryActionType;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.rpc.SeatMemoryAction}
 */
public class SeatMemoryActionTest extends TestCase {

	private SeatMemoryAction msg;

	@Override
	public void setUp() {
		msg = new SeatMemoryAction();

		msg.setId(Test.GENERAL_INT);
		msg.setLabel(Test.GENERAL_STRING);
		msg.setAction(Test.GENERAL_SEATMEMORYACTIONTYPE);
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
		assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, id);
		assertEquals(Test.MATCH, Test.GENERAL_STRING, label);
		assertEquals(Test.MATCH, Test.GENERAL_SEATMEMORYACTIONTYPE, action);

		// Invalid/Null Tests
		SeatMemoryAction msg = new SeatMemoryAction();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getId());
		assertNull(Test.NULL, msg.getLabel());
		assertNull(Test.NULL, msg.getAction());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(SeatMemoryAction.KEY_ID, Test.GENERAL_INT);
			reference.put(SeatMemoryAction.KEY_LABEL, Test.GENERAL_STRING);
			reference.put(SeatMemoryAction.KEY_ACTION, Test.GENERAL_SEATMEMORYACTIONTYPE);

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