package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.rpc.AppServiceCapability;
import com.smartdevicelink.proxy.rpc.AppServiceManifest;
import com.smartdevicelink.proxy.rpc.AppServiceRecord;
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
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.AppServiceCapability}
 */
public class AppServiceCapabilityTest extends TestCase {

	private AppServiceCapability msg;

	@Override
	public void setUp(){

		msg = new AppServiceCapability();
		msg.setUpdatedAppServiceRecord(Test.GENERAL_APPSERVICERECORD);
		msg.setUpdateReason(Test.GENERAL_SERVICE_UPDATE_REASON);
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues () {
		// Test Values
		AppServiceRecord serviceRecord = msg.getUpdatedAppServiceRecord();
		ServiceUpdateReason updateReason = msg.getUpdateReason();

		// Valid Tests
		assertEquals(Test.MATCH, serviceRecord, Test.GENERAL_APPSERVICERECORD);
		assertEquals(Test.MATCH, updateReason, Test.GENERAL_SERVICE_UPDATE_REASON);

		// Invalid/Null Tests
		AppServiceCapability msg = new AppServiceCapability();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getUpdatedAppServiceRecord());
		assertNull(Test.NULL, msg.getUpdateReason());
	}

	public void testRequiredParamsConstructor(){
		msg = new AppServiceCapability(Test.GENERAL_APPSERVICERECORD);
		AppServiceRecord serviceRecord = msg.getUpdatedAppServiceRecord();
		assertEquals(Test.MATCH, serviceRecord, Test.GENERAL_APPSERVICERECORD);
	}

	public void testJson(){
		JSONObject reference = new JSONObject();

		try{
			reference.put(AppServiceCapability.KEY_UPDATE_REASON, Test.GENERAL_SERVICE_UPDATE_REASON);
			reference.put(AppServiceCapability.KEY_UPDATED_APP_SERVICE_RECORD, Test.GENERAL_APPSERVICERECORD);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(Test.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while(iterator.hasNext()) {
				String key = (String) iterator.next();
				if (key.equals(AppServiceCapability.KEY_UPDATED_APP_SERVICE_RECORD)){
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(Test.TRUE, Validator.validateAppServiceRecord(Test.GENERAL_APPSERVICERECORD, new AppServiceRecord(hashTest)));
				} else{
					assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
				}
			}
		} catch(JSONException e){
			fail(Test.JSON_FAIL);
		}
	}

	public void testMatchesAppService(){

		String baseName = "NavTest", baseID = "37F98053AE";

		AppServiceCapability capability1 = AppServiceFactory.createAppServiceCapability(AppServiceType.NAVIGATION, baseName, baseID, true, null);
		AppServiceCapability capability2 = AppServiceFactory.createAppServiceCapability(AppServiceType.NAVIGATION, baseName, baseID, true, null);

		assertTrue(capability1.matchesAppService(capability2));

		//Remove the service id from record 2
		capability2.getUpdatedAppServiceRecord().setServiceID(null);
		assertNull(capability2.getUpdatedAppServiceRecord().getServiceID());
		assertTrue(capability1.matchesAppService(capability2));

		/* UPDATE WITH NEW SERVICE MANIFEST CHANGES */

		AppServiceManifest appServiceManifest2 = capability2.getUpdatedAppServiceRecord().getServiceManifest();

		List<FunctionID> handledRPCs2 = new ArrayList<>();
		handledRPCs2.add(FunctionID.SEND_LOCATION);
		handledRPCs2.add(FunctionID.GET_WAY_POINTS);
		appServiceManifest2.setHandledRpcsUsingFunctionIDs(handledRPCs2);
		capability2.getUpdatedAppServiceRecord().setServiceManifest(appServiceManifest2);

		assertTrue(capability1.matchesAppService(capability2));


		/* UPDATE WITH NEW SERVICE MANIFEST APP NAME */
		capability2.getUpdatedAppServiceRecord().getServiceManifest().setServiceName("Nav-Test");
		assertFalse(capability1.matchesAppService(capability2));


		/* UPDATE WITH NEW SERVICE ID */
		//Reset name
		capability2.getUpdatedAppServiceRecord().getServiceManifest().setServiceName(baseName);

		capability2.getUpdatedAppServiceRecord().setServiceID("EEEEEEEEEE");
		assertFalse(capability1.matchesAppService(capability2));
	}


}