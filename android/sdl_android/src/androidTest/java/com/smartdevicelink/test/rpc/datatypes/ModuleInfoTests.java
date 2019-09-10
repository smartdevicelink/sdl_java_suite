package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.Grid;
import com.smartdevicelink.proxy.rpc.ModuleInfo;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

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

			Hashtable<String, Object> h1 = JsonRPCMarshaller.deserializeJSONObject(original);
			Hashtable<String, Object> h2 = JsonRPCMarshaller.deserializeJSONObject(serialized);
			assertTrue(Test.TRUE, Validator.validateModuleInfo(new ModuleInfo(h1), new ModuleInfo(h2)));
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}
	}
}
