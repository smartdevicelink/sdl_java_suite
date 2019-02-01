package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.AppServiceCapability;
import com.smartdevicelink.proxy.rpc.AppServicesCapabilities;
import com.smartdevicelink.proxy.rpc.enums.AppServiceType;
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
		msg.setServicesSupported(Test.GENERAL_APPSERVICETYPE_LIST);
		msg.setAppServices(Test.GENERAL_APPSERVICECAPABILITY_LIST);
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues () {
		// Test Values
		List<AppServiceType> serviceTypeList = msg.getServicesSupported();
		List<AppServiceCapability> serviceCapabilityList = msg.getAppServices();

		// Valid Tests
		assertEquals(Test.MATCH, serviceTypeList, Test.GENERAL_APPSERVICETYPE_LIST);
		assertEquals(Test.MATCH, serviceCapabilityList, Test.GENERAL_APPSERVICECAPABILITY_LIST);

		// Invalid/Null Tests
		AppServicesCapabilities msg = new AppServicesCapabilities();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getAppServices());
		assertNull(Test.NULL, msg.getServicesSupported());
	}

	public void testRequiredParamsConstructor(){
		msg = new AppServicesCapabilities(Test.GENERAL_APPSERVICETYPE_LIST);
		List<AppServiceType> serviceTypeList = msg.getServicesSupported();
		assertEquals(Test.MATCH, serviceTypeList, Test.GENERAL_APPSERVICETYPE_LIST);
	}

	public void testJson(){
		JSONObject reference = new JSONObject();

		try{
			reference.put(AppServicesCapabilities.KEY_APP_SERVICES, Test.GENERAL_APPSERVICETYPE_LIST);
			reference.put(AppServicesCapabilities.KEY_SERVICES_SUPPORTED, Test.GENERAL_APPSERVICECAPABILITY_LIST);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(Test.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while(iterator.hasNext()) {
				String key = (String) iterator.next();
				if (key.equals(AppServicesCapabilities.KEY_APP_SERVICES)) {
					List<AppServiceCapability> list1 = Test.GENERAL_APPSERVICECAPABILITY_LIST;
					List<AppServiceCapability> list2 = JsonUtils.readAppServiceCapabilityListFromJsonObject(underTest, key);
					assertTrue(Test.TRUE, Validator.validateAppServiceCapabilityList(list1,list2));
				} else if (key.equals(AppServicesCapabilities.KEY_SERVICES_SUPPORTED)){
					List<AppServiceType> list1 = Test.GENERAL_APPSERVICETYPE_LIST;
					List<AppServiceType> list2 = JsonUtils.readAppServiceTypeListFromJsonObject(underTest, key);
					assertTrue(Test.TRUE, Validator.validateAppServiceTypeList(list1,list2));
				} else{
					assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
				}
			}
		} catch(JSONException e){
			fail(Test.JSON_FAIL);
		}
	}
}