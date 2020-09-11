package com.smartdevicelink.test.rpc.datatypes;


import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.SeatLocation;
import com.smartdevicelink.proxy.rpc.SeatLocationCapability;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

public class SeatLocationCapabilityTests extends TestCase {

	private SeatLocationCapability msg;

	@Override
	public void setUp() {
		msg = new SeatLocationCapability();
		msg.setCols(TestValues.GENERAL_INT);
		msg.setRows(TestValues.GENERAL_INT);
		msg.setLevels(TestValues.GENERAL_INT);
		msg.setSeats(TestValues.GENERAL_SEAT_LIST);
	}

	public void testRpcValues() {
		int row = msg.getRows();
		int col = msg.getCols();
		int level = msg.getLevels();
		List<SeatLocation> seats = msg.getSeats();

		//valid tests
		assertEquals(TestValues.MATCH, row, TestValues.GENERAL_INT);
		assertEquals(TestValues.MATCH, col, TestValues.GENERAL_INT);
		assertEquals(TestValues.MATCH, level, TestValues.GENERAL_INT);
		assertEquals(TestValues.MATCH, seats.size(), TestValues.GENERAL_SEAT_LIST.size());
		for (int i = 0; i < TestValues.GENERAL_SEAT_LIST.size(); i++) {
			assertTrue(TestValues.TRUE, Validator.validateGrid(TestValues.GENERAL_SEAT_LIST.get(i).getGrid(), seats.get(i).getGrid()));
		}

		//null tests
		SeatLocationCapability msg = new SeatLocationCapability();
		assertNull(TestValues.NULL, msg.getCols());
		assertNull(TestValues.NULL, msg.getRows());
		assertNull(TestValues.NULL, msg.getLevels());
		assertNull(TestValues.NULL, msg.getSeats());
	}

	public void testJson() {
		JSONObject original = new JSONObject();
		try {
			original.put(SeatLocationCapability.KEY_COLS, TestValues.GENERAL_INT);
			original.put(SeatLocationCapability.KEY_ROWS, TestValues.GENERAL_INT);
			original.put(SeatLocationCapability.KEY_LEVELS, TestValues.GENERAL_INT);
			original.put(SeatLocationCapability.KEY_SEATS, TestValues.JSON_SEAT_LOCATIONS);

			JSONObject serialized = msg.serializeJSON();
			assertEquals(serialized.length(), original.length());

			Iterator<String> iter = original.keys();
			String key = "";
			while (iter.hasNext()) {
				key = iter.next();
				if (key.equals(SeatLocationCapability.KEY_COLS)) {
					int i1 = new SeatLocationCapability(JsonRPCMarshaller.deserializeJSONObject(original)).getCols();
					int i2 = new SeatLocationCapability(JsonRPCMarshaller.deserializeJSONObject(serialized)).getCols();
					assertEquals(TestValues.MATCH, i1, i2);
				} else if (key.equals(SeatLocationCapability.KEY_ROWS)) {
					int i1 = new SeatLocationCapability(JsonRPCMarshaller.deserializeJSONObject(original)).getRows();
					int i2 = new SeatLocationCapability(JsonRPCMarshaller.deserializeJSONObject(serialized)).getRows();
					assertEquals(TestValues.MATCH, i1, i2);
				} else if (key.equals(SeatLocationCapability.KEY_LEVELS)) {
					int i1 = new SeatLocationCapability(JsonRPCMarshaller.deserializeJSONObject(original)).getLevels();
					int i2 = new SeatLocationCapability(JsonRPCMarshaller.deserializeJSONObject(serialized)).getLevels();
					assertEquals(TestValues.MATCH, i1, i2);
				} else if (key.equals(SeatLocationCapability.KEY_SEATS)) {
					JSONArray arr1 = JsonUtils.readJsonArrayFromJsonObject(original, key);
					JSONArray arr2 = JsonUtils.readJsonArrayFromJsonObject(serialized, key);
					assertEquals(TestValues.MATCH, arr1.length(), arr2.length());
					for (int i = 0; i < TestValues.GENERAL_SEAT_LIST.size(); i++) {
						Hashtable<String, Object> h1 = JsonRPCMarshaller.deserializeJSONObject(arr1.getJSONObject(i));
						Hashtable<String, Object> h2 = JsonRPCMarshaller.deserializeJSONObject(arr2.getJSONObject(i));
						assertTrue(TestValues.MATCH, Validator.validateSeatLocation(new SeatLocation(h1), new SeatLocation(h2)));
					}
				}
			}
		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
		}
	}
}
