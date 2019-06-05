package com.smartdevicelink.test.rpc.notifications;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.ModuleData;
import com.smartdevicelink.proxy.rpc.OnRCStatus;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.Test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OnRCStatusTests extends BaseRpcTests {
	@Override
	protected RPCMessage createMessage() {
		OnRCStatus msg = new OnRCStatus();

		List<ModuleData> listAllocatedModules = Test.GENERAL_MODULEDATA_LIST;

		msg.setAllocatedModules(listAllocatedModules);

		List<ModuleData> listFreeModules = new ArrayList<>();
		listFreeModules.add(Test.GENERAL_MODULEDATA);
		msg.setFreeModules(listFreeModules);

		msg.setAllowed(Test.GENERAL_BOOLEAN);
		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_NOTIFICATION;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.ON_RC_STATUS.toString();
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();

		JSONArray jsonArrayAllocatedModules = new JSONArray();
		JSONArray jsonArrayFreeModules = new JSONArray();
		try {
			jsonArrayAllocatedModules.put(JsonRPCMarshaller.serializeHashtable(Test.GENERAL_MODULEDATA.getStore()));
			jsonArrayFreeModules.put(JsonRPCMarshaller.serializeHashtable(Test.GENERAL_MODULEDATA.getStore()));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			result.put(OnRCStatus.KEY_ALLOCATED_MODULES, jsonArrayAllocatedModules);
			result.put(OnRCStatus.KEY_FREE_MODULES, jsonArrayFreeModules);
			result.put(OnRCStatus.KEY_ALLOWED, Test.GENERAL_BOOLEAN);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues() {
		// Test Values
		List<ModuleData> listAllocatedModules = ((OnRCStatus) msg).getAllocatedModules();
		List<ModuleData> listFreeModules = ((OnRCStatus) msg).getFreeModules();
		Boolean allowed = ((OnRCStatus) msg).getAllowed();

		// Valid Tests
		assertEquals(Test.MATCH, Test.GENERAL_MODULEDATA, listAllocatedModules.get(0));
		assertEquals(Test.MATCH, Test.GENERAL_MODULEDATA, listFreeModules.get(0));
		assertEquals(Test.MATCH, (Boolean) Test.GENERAL_BOOLEAN, allowed);

		// Invalid/Null Tests
		OnRCStatus msg = new OnRCStatus();
		assertNotNull(Test.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(Test.NULL, msg.getAllocatedModules());
		assertNull(Test.NULL, msg.getFreeModules());
		assertNull(Test.NULL, msg.getAllowed());
	}
}
