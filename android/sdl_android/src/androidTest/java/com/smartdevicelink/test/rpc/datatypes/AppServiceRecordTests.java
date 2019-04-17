package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.AppServiceManifest;
import com.smartdevicelink.proxy.rpc.AppServiceRecord;
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
 * {@link com.smartdevicelink.proxy.rpc.AppServiceRecord}
 */
public class AppServiceRecordTests extends TestCase {

	private AppServiceRecord msg;

	@Override
	public void setUp(){

		msg = new AppServiceRecord();
		msg.setServicePublished(Test.GENERAL_BOOLEAN);
		msg.setServiceActive(Test.GENERAL_BOOLEAN);
		msg.setServiceManifest(Test.GENERAL_APPSERVICEMANIFEST);
		msg.setServiceID(Test.GENERAL_STRING);

	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues () {
		// Test Values

		boolean isServicePublished = msg.getServicePublished();
		boolean isServiceActive = msg.getServiceActive();
		AppServiceManifest serviceManifest = msg.getServiceManifest();
		String serviceID = msg.getServiceID();

		// Valid Tests
		assertEquals(Test.GENERAL_BOOLEAN, isServicePublished);
		assertEquals(Test.GENERAL_BOOLEAN, isServiceActive);
		assertEquals(Test.GENERAL_APPSERVICEMANIFEST, serviceManifest);
		assertEquals(Test.GENERAL_STRING, serviceID);

		// Invalid/Null Tests
		AppServiceRecord msg = new AppServiceRecord();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getServicePublished());
		assertNull(Test.NULL, msg.getServiceActive());
		assertNull(Test.NULL, msg.getServiceManifest());
		assertNull(Test.NULL, msg.getServiceID());
	}

	public void testRequiredParamsConstructor(){
		msg = new AppServiceRecord(Test.GENERAL_STRING, Test.GENERAL_APPSERVICEMANIFEST, Test.GENERAL_BOOLEAN, Test.GENERAL_BOOLEAN);

		boolean isServicePublished = msg.getServicePublished();
		boolean isServiceActive = msg.getServiceActive();
		AppServiceManifest serviceManifest = msg.getServiceManifest();
		String serviceID = msg.getServiceID();

		// Valid Tests
		assertEquals(Test.GENERAL_BOOLEAN, isServicePublished);
		assertEquals(Test.GENERAL_BOOLEAN, isServiceActive);
		assertEquals(Test.GENERAL_APPSERVICEMANIFEST, serviceManifest);
		assertEquals(Test.GENERAL_STRING, serviceID);
	}

	public void testJson(){
		JSONObject reference = new JSONObject();

		try{
			reference.put(AppServiceRecord.KEY_SERVICE_ACTIVE, Test.GENERAL_BOOLEAN);
			reference.put(AppServiceRecord.KEY_SERVICE_PUBLISHED, Test.GENERAL_BOOLEAN);
			reference.put(AppServiceRecord.KEY_SERVICE_ID, Test.GENERAL_STRING);
			reference.put(AppServiceRecord.KEY_SERVICE_MANIFEST, Test.GENERAL_APPSERVICEMANIFEST);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(Test.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while(iterator.hasNext()){
				String key = (String) iterator.next();

				if(key.equals(AppServiceRecord.KEY_SERVICE_MANIFEST)){
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(Test.TRUE, Validator.validateAppServiceManifest( Test.GENERAL_APPSERVICEMANIFEST, new AppServiceManifest(hashTest)));
				}else {
					assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
				}
			}
		} catch(JSONException e){
			fail(Test.JSON_FAIL);
		}
	}
}