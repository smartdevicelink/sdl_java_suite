package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.CloudAppProperties;
import com.smartdevicelink.proxy.rpc.enums.HybridAppPreference;
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
 * {@link com.smartdevicelink.proxy.rpc.CloudAppProperties}
 */
public class CloudAppPropertiesTests extends TestCase{

	private CloudAppProperties msg;

	@Override
	public void setUp(){
		msg = new CloudAppProperties();

		msg.setNicknames(Test.GENERAL_STRING_LIST);
		msg.setAppID(Test.GENERAL_STRING);
		msg.setEnabled(Test.GENERAL_BOOLEAN);
		msg.setAuthToken(Test.GENERAL_STRING);
		msg.setCloudTransportType(Test.GENERAL_STRING);
		msg.setHybridAppPreference(Test.GENERAL_HYBRID_APP_PREFERENCE);
		msg.setEndpoint(Test.GENERAL_STRING);
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues () {
		// Test Values
		List<String> nicknames = msg.getNicknames();
		String appID = msg.getAppID();
		boolean enabled = msg.isEnabled();
		String authToken = msg.getAuthToken();
		String cloudTransportType = msg.getCloudTransportType();
		HybridAppPreference hybridAppPreference = msg.getHybridAppPreference();
		String endpoint = msg.getEndpoint();

		// Valid Tests
		assertEquals(Test.MATCH, Test.GENERAL_STRING_LIST, nicknames);
		assertEquals(Test.MATCH, Test.GENERAL_STRING, appID);
		assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, enabled);
		assertEquals(Test.MATCH, Test.GENERAL_STRING, authToken);
		assertEquals(Test.MATCH, Test.GENERAL_STRING, cloudTransportType);
		assertEquals(Test.MATCH, Test.GENERAL_HYBRID_APP_PREFERENCE, hybridAppPreference);
		assertEquals(Test.MATCH, Test.GENERAL_STRING, endpoint);

		// Invalid/Null Tests
		CloudAppProperties msg = new CloudAppProperties();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getNicknames());
		assertNull(Test.NULL, msg.getAppID());
		assertNull(Test.NULL, msg.isEnabled());
		assertNull(Test.NULL, msg.getAuthToken());
		assertNull(Test.NULL, msg.getCloudTransportType());
		assertNull(Test.NULL, msg.getHybridAppPreference());
		assertNull(Test.NULL, msg.getEndpoint());
	}

	public void testJson(){
		JSONObject reference = new JSONObject();

		try{
			reference.put(CloudAppProperties.KEY_NICKNAMES, Test.GENERAL_STRING_LIST);
			reference.put(CloudAppProperties.KEY_APP_ID, Test.GENERAL_STRING);
			reference.put(CloudAppProperties.KEY_ENABLED, Test.GENERAL_BOOLEAN);
			reference.put(CloudAppProperties.KEY_AUTH_TOKEN, Test.GENERAL_STRING);
			reference.put(CloudAppProperties.KEY_CLOUD_TRANSPORT_TYPE, Test.GENERAL_STRING);
			reference.put(CloudAppProperties.KEY_HYBRID_APP_PREFERENCE, Test.GENERAL_HYBRID_APP_PREFERENCE);
			reference.put(CloudAppProperties.KEY_ENDPOINT, Test.GENERAL_STRING);

			JSONObject underTest = msg.serializeJSON();

			assertEquals(Test.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while(iterator.hasNext()){
				String key = (String) iterator.next();

				if (key.equals(CloudAppProperties.KEY_NICKNAMES)){
					Validator.validateStringList(JsonUtils.readStringListFromJsonObject(reference, key),
							JsonUtils.readStringListFromJsonObject(underTest, key));
				}else {
					assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
				}
			}
		} catch(JSONException e){
			fail(Test.JSON_FAIL);
		}
	}
}