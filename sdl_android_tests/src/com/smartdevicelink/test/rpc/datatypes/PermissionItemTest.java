package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.HMIPermissions;
import com.smartdevicelink.proxy.rpc.ParameterPermissions;
import com.smartdevicelink.proxy.rpc.PermissionItem;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.PermissionItem}
 */
public class PermissionItemTest extends TestCase {

	private PermissionItem msg;

	@Override
	public void setUp() {
		msg = new PermissionItem();
		
		msg.setRpcName(Test.GENERAL_STRING);
		msg.setHMIPermissions(Test.GENERAL_HMIPERMISSIONS);
		msg.setParameterPermissions(Test.GENERAL_PARAMETERPERMISSIONS);
	}

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
		String rpcName = msg.getRpcName();
		HMIPermissions hmiPermissions = msg.getHMIPermissions();
		ParameterPermissions parameterPermissions = msg.getParameterPermissions();
		
		// Valid Tests
		assertEquals(Test.MATCH, Test.GENERAL_STRING, rpcName);
    	assertTrue(Test.TRUE, Validator.validateHmiPermissions(Test.GENERAL_HMIPERMISSIONS, hmiPermissions));
    	assertTrue(Test.TRUE, Validator.validateParameterPermissions(Test.GENERAL_PARAMETERPERMISSIONS, parameterPermissions));
    	
    	// Invalid/Null Tests
    	PermissionItem msg = new PermissionItem();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getRpcName());
		assertNull(Test.NULL, msg.getHMIPermissions());
		assertNull(Test.NULL, msg.getParameterPermissions());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(PermissionItem.KEY_RPC_NAME, Test.GENERAL_STRING);
			reference.put(PermissionItem.KEY_HMI_PERMISSIONS, Test.JSON_HMIPERMISSIONS);
			reference.put(PermissionItem.KEY_PARAMETER_PERMISSIONS, Test.JSON_PARAMETERPERMISSIONS);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(Test.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				
				if (key.equals(PermissionItem.KEY_HMI_PERMISSIONS)) {
					assertTrue(Test.TRUE, Validator.validateStringList(JsonUtils.readStringListFromJsonObject(reference, key), JsonUtils.readStringListFromJsonObject(underTest, key)));
				} else if (key.equals(PermissionItem.KEY_PARAMETER_PERMISSIONS)) {
					assertTrue(Test.TRUE, Validator.validateStringList(JsonUtils.readStringListFromJsonObject(reference, key), JsonUtils.readStringListFromJsonObject(underTest, key)));
				} else {
					assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
				}
			}
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}
	}
}