package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.AppServiceCapability;
import com.smartdevicelink.proxy.rpc.AppServicesCapabilities;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.AppServicesCapabilities}
 */
public class AppServicesCapabilitiesTests extends TestCase {

	private AppServicesCapabilities msg;

	@Override
	public void setUp(){

		msg = new AppServicesCapabilities();
		msg.setAppServices(Test.GENERAL_APPSERVICECAPABILITY_LIST);
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues () {
		// Test Values
		List<AppServiceCapability> serviceCapabilityList = msg.getAppServices();

		// Valid Tests
		assertEquals(Test.MATCH, serviceCapabilityList, Test.GENERAL_APPSERVICECAPABILITY_LIST);

		// Invalid/Null Tests
		AppServicesCapabilities msg = new AppServicesCapabilities();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getAppServices());
	}

	public void testJson(){
		JSONObject reference = new JSONObject();

		try{
			reference.put(AppServicesCapabilities.KEY_APP_SERVICES, Test.GENERAL_APPSERVICETYPE_LIST);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(Test.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while(iterator.hasNext()) {
				String key = (String) iterator.next();
				if (key.equals(AppServicesCapabilities.KEY_APP_SERVICES)) {
					List<AppServiceCapability> list1 = Test.GENERAL_APPSERVICECAPABILITY_LIST;
					List<AppServiceCapability> list2 = JsonUtils.readAppServiceCapabilityListFromJsonObject(underTest, key);
					assertTrue(Test.TRUE, Validator.validateAppServiceCapabilityList(list1,list2));
				}else{
					assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
				}
			}
		} catch(JSONException e){
			fail(Test.JSON_FAIL);
		}
	}
}