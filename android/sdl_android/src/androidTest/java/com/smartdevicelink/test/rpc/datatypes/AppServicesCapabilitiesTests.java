package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.AppServiceCapability;
import com.smartdevicelink.proxy.rpc.AppServicesCapabilities;
import com.smartdevicelink.proxy.rpc.enums.AppServiceType;
import com.smartdevicelink.proxy.rpc.enums.ServiceUpdateReason;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.utl.AppServiceFactory;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
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

	public void testUpdate(){
		String baseName = "NavTest", baseID = "37F98053AE";
		AppServiceCapability capability1 = AppServiceFactory.createAppServiceCapability(AppServiceType.NAVIGATION, baseName, null, true, null);
		AppServicesCapabilities capabilities1 = new AppServicesCapabilities();
		capabilities1.setAppServices(Collections.singletonList(capability1));

		assertNotNull(capabilities1.getAppServices());
		assertEquals(capabilities1.getAppServices().size(), 1);
		/* TEST TO ENSURE A THE RECORD HAS THE CORRECT "NULL" VALUE FOR SERVICE ID */
		assertNull(capabilities1.getAppServices().get(0).getUpdatedAppServiceRecord().getServiceID());

		/* TEST TO ENSURE THAT THE LIST DOES NOT GET UPDATED FOR NULL OR EMPTY LISTS */
		assertFalse(capabilities1.updateAppServices(null));
		assertFalse(capabilities1.updateAppServices(new ArrayList<AppServiceCapability>()));


		AppServiceCapability capability2 = AppServiceFactory.createAppServiceCapability(AppServiceType.NAVIGATION, baseName, baseID, true, null);

		/* TEST TO ENSURE A THE LIST BEING STORED WAS MODIFIED */
		assertTrue(capabilities1.updateAppServices(Collections.singletonList(capability2)));

		/* TEST TO ENSURE A NEW RECORD WAS NOT ADDED */
		assertEquals(capabilities1.getAppServices().size(), 1);

		assertTrue(capabilities1.getAppServices().get(0).getUpdatedAppServiceRecord().getServiceID().equals(baseID));

		AppServiceCapability capability3 = AppServiceFactory.createAppServiceCapability(AppServiceType.NAVIGATION, "NewNav", null, true, null);

		/* TEST TO ENSURE A THE LIST BEING STORED WAS MODIFIED */
		assertTrue(capabilities1.updateAppServices(Collections.singletonList(capability3)));

		/* TEST TO ENSURE A NEW RECORD WAS ADDED */
		assertEquals(capabilities1.getAppServices().size(), 2);

		AppServiceCapability capability4 = AppServiceFactory.createAppServiceCapability(AppServiceType.NAVIGATION, "NewNav", "eeeeeeeee", true, null);

		/* TEST TO ENSURE A THE LIST BEING STORED WAS MODIFIED */
		assertTrue(capabilities1.updateAppServices(Collections.singletonList(capability4)));

		/* TEST TO ENSURE A NEW RECORD WAS NOT ADDED */
		assertEquals(capabilities1.getAppServices().size(), 2);

		AppServiceCapability capability5 = AppServiceFactory.createAppServiceCapability(AppServiceType.NAVIGATION, "NewNav", "fffffff", true, null);

		/* TEST TO ENSURE A THE LIST BEING STORED WAS MODIFIED */
		assertTrue(capabilities1.updateAppServices(Collections.singletonList(capability5)));

		/* TEST TO ENSURE A NEW RECORD WAS NOT ADDED */
		assertEquals(capabilities1.getAppServices().size(), 3);


		capability5.setUpdateReason(ServiceUpdateReason.REMOVED);

		/* TEST TO ENSURE A THE LIST BEING STORED WAS MODIFIED */
		assertTrue(capabilities1.updateAppServices(Collections.singletonList(capability5)));

		/* TEST TO ENSURE THE RECORD WAS REMOVED */
		assertEquals(capabilities1.getAppServices().size(), 2);

		/* TEST TO ENSURE THE RECORD REMOVED WAS THE CORRECT ONE */
		assertFalse(capabilities1.getAppServices().contains(capability5));


	}
}