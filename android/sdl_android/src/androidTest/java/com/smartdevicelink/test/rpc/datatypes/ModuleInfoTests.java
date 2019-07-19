package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.Grid;
import com.smartdevicelink.proxy.rpc.ModuleInfo;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Iterator;

public class ModuleInfoTests extends TestCase {
	private ModuleInfo msg;

	@Override
	public void setUp() {
		msg = new ModuleInfo();
		msg.setModuleId(Test.GENERAL_STRING);
		msg.setModuleLocation(Test.GENERAL_GRID);
		msg.setModuleServiceArea(Test.GENERAL_GRID);
		msg.setMultipleAccessAllowance(Test.GENERAL_BOOLEAN);
	}

	public void testRpcValues() {
		String id = msg.getModuleId();
		Grid loc = msg.getModuleLocation();
		Grid area = msg.getModuleServiceArea();
		boolean isAllowed = msg.getMultipleAccessAllowance();

		//valid tests
		assertEquals(Test.MATCH, Test.GENERAL_STRING, id);
		assertEquals(Test.MATCH, Test.GENERAL_GRID, loc);
		assertEquals(Test.MATCH, Test.GENERAL_GRID, area);
		assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, isAllowed);

		//null test
		ModuleInfo msg = new ModuleInfo();
		assertNull(Test.NULL, msg.getModuleId());
		assertNull(Test.NULL, msg.getModuleLocation());
		assertNull(Test.NULL, msg.getModuleServiceArea());
		assertNull(Test.NULL, msg.getMultipleAccessAllowance());
	}

	public void testJson() {
		JSONObject original = new JSONObject();
		try {
			original.put(ModuleInfo.KEY_MODULE_ID, Test.GENERAL_STRING);
			original.put(ModuleInfo.KEY_MODULE_LOCATION, JsonRPCMarshaller.serializeHashtable(Test.GENERAL_GRID.getStore()));
			original.put(ModuleInfo.KEY_MODULE_SERVICE_AREA, JsonRPCMarshaller.serializeHashtable(Test.GENERAL_GRID.getStore()));
			original.put(ModuleInfo.KEY_MULTIPLE_ACCESS_ALLOWED, Test.GENERAL_BOOLEAN);

			JSONObject serialized = msg.serializeJSON();
			assertEquals(Test.MATCH, original.length(), serialized.length());
			Iterator<String> iter = original.keys();
			while (iter.hasNext()) {
				String key = iter.next();
				if (key.equals(ModuleInfo.KEY_MODULE_LOCATION) || key.equals(ModuleInfo.KEY_MODULE_SERVICE_AREA)) {
					doTestJson(original, serialized, key);
				} else if (key.equals(ModuleInfo.KEY_MODULE_ID)) {
					String s1 = new ModuleInfo(JsonRPCMarshaller.deserializeJSONObject(original)).getModuleId();
					String s2 = new ModuleInfo(JsonRPCMarshaller.deserializeJSONObject(serialized)).getModuleId();
					assertEquals(Test.MATCH, s1, s2);
				} else if (key.equals(ModuleInfo.KEY_MULTIPLE_ACCESS_ALLOWED)) {
					boolean b1 = new ModuleInfo(JsonRPCMarshaller.deserializeJSONObject(original)).getMultipleAccessAllowance();
					boolean b2 = new ModuleInfo(JsonRPCMarshaller.deserializeJSONObject(serialized)).getMultipleAccessAllowance();
					assertEquals(Test.MATCH, b1, b2);
				}
			}
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}
	}

	private void doTestJson(JSONObject obj1, JSONObject obj2, String key) {
		try {
			JSONObject o1 = (JSONObject) JsonUtils.readObjectFromJsonObject(obj1, key);
			JSONObject o2 = (JSONObject) JsonUtils.readObjectFromJsonObject(obj2, key);
			Hashtable<String, Object> h1 = JsonRPCMarshaller.deserializeJSONObject(o1);
			Hashtable<String, Object> h2 = JsonRPCMarshaller.deserializeJSONObject(o2);
			assertTrue(Test.TRUE, Validator.validateGrid(new Grid(h1), new Grid(h2)));
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}
	}
}
