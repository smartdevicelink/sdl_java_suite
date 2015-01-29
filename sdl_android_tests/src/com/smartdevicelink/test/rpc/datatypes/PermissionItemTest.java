package com.smartdevicelink.test.rpc.datatypes;

import java.util.Arrays;
import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.HMIPermissions;
import com.smartdevicelink.proxy.rpc.ParameterPermissions;
import com.smartdevicelink.proxy.rpc.PermissionItem;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.test.utils.JsonUtils;
import com.smartdevicelink.test.utils.Validator;

public class PermissionItemTest extends TestCase {

	private static final String RPC_NAME = "rpcName";
	private static final HMIPermissions HMI_PERMISSIONS = new HMIPermissions();
	private static final ParameterPermissions PARAMETER_PERMISSIONS = new ParameterPermissions();
	
	private PermissionItem msg;

	@Override
	public void setUp() {
		//TODO: correct?
		HMI_PERMISSIONS.setAllowed(Arrays.asList(new HMILevel[]{ HMILevel.HMI_NONE, HMILevel.HMI_FULL}));
		PARAMETER_PERMISSIONS.setAllowed(Arrays.asList(new String[]{ "param1", "param2" }));
		
		msg = new PermissionItem();
		
		msg.setRpcName(RPC_NAME);
		msg.setHMIPermissions(HMI_PERMISSIONS);
		msg.setParameterPermissions(PARAMETER_PERMISSIONS);
		
		
	}

	public void testRPCName () {
		String copy = msg.getRpcName();
		
		assertEquals("Rpc name didn't match expected value.", RPC_NAME, copy);
	}
	
	public void testHMIPermissions () {
		HMIPermissions copy = msg.getHMIPermissions();
		
		assertNotSame("HMI permissions was not defensive copied", HMI_PERMISSIONS, copy);
    	assertTrue("Input value didn't match expected value.", Validator.validateHmiPermissions(HMI_PERMISSIONS, copy));
	}
	
	public void testParameterPermisisons () {
		ParameterPermissions copy = msg.getParameterPermissions();
		
		assertNotSame("Parameter permissions was not defensive copied", PARAMETER_PERMISSIONS, copy);
    	assertTrue("Input value didn't match expected value.", Validator.validateParameterPermissions(PARAMETER_PERMISSIONS, copy));
	}

	public void testJson() {
		JSONObject reference = new JSONObject();
		JSONObject hmi = new JSONObject(), parameter = new JSONObject();

		try {
			hmi.put(HMIPermissions.KEY_ALLOWED, HMILevel.HMI_NONE);
			hmi.put(HMIPermissions.KEY_USER_DISALLOWED, HMILevel.HMI_FULL);
			
			parameter.put(ParameterPermissions.KEY_ALLOWED, PARAMETER_PERMISSIONS.getAllowed());
			parameter.put(ParameterPermissions.KEY_USER_DISALLOWED, PARAMETER_PERMISSIONS.getUserDisallowed());
			
			reference.put(PermissionItem.KEY_RPC_NAME, RPC_NAME);
			reference.put(PermissionItem.KEY_HMI_PERMISSIONS, hmi);
			reference.put(PermissionItem.KEY_PARAMETER_PERMISSIONS, parameter);

			JSONObject underTest = msg.serializeJSON();

			assertEquals("JSON size didn't match expected size.",
					reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				
				if (key.equals(PermissionItem.KEY_HMI_PERMISSIONS)) {
					assertTrue("JSON value didn't match expected value for key \"" + key + "\".",
							Validator.validateStringList(
									JsonUtils.readStringListFromJsonObject(reference, key), 
									JsonUtils.readStringListFromJsonObject(underTest, key)));
				} else if (key.equals(PermissionItem.KEY_PARAMETER_PERMISSIONS)) {
					assertTrue("JSON value didn't match expected value for key \"" + key + "\".",
							Validator.validateStringList(
									JsonUtils.readStringListFromJsonObject(reference, key), 
									JsonUtils.readStringListFromJsonObject(underTest, key)));
				} else {
					assertEquals("JSON value didn't match expected value for key \"" + key + "\".",
							JsonUtils.readObjectFromJsonObject(reference, key),
							JsonUtils.readObjectFromJsonObject(underTest, key));
				}
			}
		} catch (JSONException e) {
			/* do nothing */
		}
	}

	public void testNull() {
		PermissionItem msg = new PermissionItem();
		assertNotNull("Null object creation failed.", msg);

		assertNull("Rpc name wasn't set, but getter method returned an object.", msg.getRpcName());
		assertNull("HMI permissions wasn't set, but getter method returned an object.", msg.getHMIPermissions());
		assertNull("Parameter permissions wasn't set, but getter method returned an object.", msg.getParameterPermissions());
	}
}
