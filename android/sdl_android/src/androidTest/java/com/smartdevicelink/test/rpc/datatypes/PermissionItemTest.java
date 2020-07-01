package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.HMIPermissions;
import com.smartdevicelink.proxy.rpc.ParameterPermissions;
import com.smartdevicelink.proxy.rpc.PermissionItem;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.PermissionItem}
 */
public class PermissionItemTest extends TestCase {

	private PermissionItem msg;

	@Override
	public void setUp() {
		msg = new PermissionItem();
		
		msg.setRpcName(TestValues.GENERAL_STRING);
		msg.setHMIPermissions(TestValues.GENERAL_HMIPERMISSIONS);
		msg.setParameterPermissions(TestValues.GENERAL_PARAMETERPERMISSIONS);
		msg.setRequireEncryption(TestValues.GENERAL_BOOLEAN);
	}

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
		String rpcName = msg.getRpcName();
		HMIPermissions hmiPermissions = msg.getHMIPermissions();
		ParameterPermissions parameterPermissions = msg.getParameterPermissions();
		boolean isEncryptionRequired = msg.getRequireEncryption();
		
		// Valid Tests
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, rpcName);
    	assertTrue(TestValues.TRUE, Validator.validateHmiPermissions(TestValues.GENERAL_HMIPERMISSIONS, hmiPermissions));
    	assertTrue(TestValues.TRUE, Validator.validateParameterPermissions(TestValues.GENERAL_PARAMETERPERMISSIONS, parameterPermissions));
    	assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, isEncryptionRequired);
    	
    	// Invalid/Null Tests
    	PermissionItem msg = new PermissionItem();
		assertNotNull(TestValues.NOT_NULL, msg);

		assertNull(TestValues.NULL, msg.getRpcName());
		assertNull(TestValues.NULL, msg.getHMIPermissions());
		assertNull(TestValues.NULL, msg.getParameterPermissions());
		assertNull(TestValues.NULL, msg.getRequireEncryption());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(PermissionItem.KEY_RPC_NAME, TestValues.GENERAL_STRING);
			reference.put(PermissionItem.KEY_HMI_PERMISSIONS, TestValues.JSON_HMIPERMISSIONS);
			reference.put(PermissionItem.KEY_PARAMETER_PERMISSIONS, TestValues.JSON_PARAMETERPERMISSIONS);
			reference.put(PermissionItem.KEY_REQUIRE_ENCRYPTION, TestValues.GENERAL_BOOLEAN);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(TestValues.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				
				if (key.equals(PermissionItem.KEY_HMI_PERMISSIONS)) {
					assertTrue(TestValues.TRUE, Validator.validateStringList(JsonUtils.readStringListFromJsonObject(reference, key), JsonUtils.readStringListFromJsonObject(underTest, key)));
				} else if (key.equals(PermissionItem.KEY_PARAMETER_PERMISSIONS)) {
					assertTrue(TestValues.TRUE, Validator.validateStringList(JsonUtils.readStringListFromJsonObject(reference, key), JsonUtils.readStringListFromJsonObject(underTest, key)));
				} else if (key.equals(PermissionItem.KEY_REQUIRE_ENCRYPTION)) {
					assertEquals(TestValues.MATCH, JsonUtils.readBooleanFromJsonObject(reference, key), JsonUtils.readBooleanFromJsonObject(underTest, key));
				} else {
					assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
				}
			}
		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
		}
	}
}