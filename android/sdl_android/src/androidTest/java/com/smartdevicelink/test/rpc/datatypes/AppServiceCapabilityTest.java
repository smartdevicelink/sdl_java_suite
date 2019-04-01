package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.AppServiceCapability;
import com.smartdevicelink.proxy.rpc.AppServiceRecord;
import com.smartdevicelink.proxy.rpc.enums.ServiceUpdateReason;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Iterator;

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
}