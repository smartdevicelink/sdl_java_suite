package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.rpc.AppServiceCapability;
import com.smartdevicelink.proxy.rpc.AppServiceManifest;
import com.smartdevicelink.proxy.rpc.AppServicesCapabilities;
import com.smartdevicelink.proxy.rpc.MediaServiceManifest;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.WeatherServiceManifest;
import com.smartdevicelink.proxy.rpc.enums.AppServiceType;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.AppServiceManifest}
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
		assertEquals(Test.GENERAL_BOOLEAN, allowAppConsumers);

		// Invalid/Null Tests
		AppServicesCapabilities msg = new AppServicesCapabilities();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getAppServices());
		assertNull(Test.NULL, msg.getServicesSupported());
	}

	public void testRequiredParamsConstructor(){
		msg = new AppServicesCapabilities(Test.GENERAL_APPSERVICETYPE_LIST);
		List<AppServiceType> serviceTypeList = msg.getServicesSupported();
		assertEquals(Test.GENERAL_APP_SERVICE_TYPE, appServiceType);
	}

	public void testJson(){
		JSONObject reference = new JSONObject();

		try{
			reference.put(AppServiceManifest.KEY_SERVICE_NAME, Test.GENERAL_STRING);
			reference.put(AppServiceManifest.KEY_SERVICE_ICON, Test.GENERAL_STRING);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(Test.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while(iterator.hasNext()){
				String key = (String) iterator.next();

				if(key.equals(AppServiceManifest.KEY_RPC_SPEC_VERSION)){
					JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(Test.TRUE, Validator.validateSdlMsgVersion( new SdlMsgVersion(hashReference), new SdlMsgVersion(hashTest)));
				}else if(key.equals(AppServiceManifest.KEY_HANDLED_RPCS)){
					List<FunctionID> list1 = Test.GENERAL_FUNCTION_ID_LIST;
					List<FunctionID> list2 = JsonUtils.readFunctionIDListFromJsonObject(underTest, key);
					assertTrue(Test.TRUE, Validator.validateFunctionIDList(list1,list2));
				}else if(key.equals(AppServiceManifest.KEY_WEATHER_SERVICE_MANIFEST)){
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(Test.TRUE, Validator.validateWeatherServiceManifest( Test.GENERAL_WEATHER_SERVICE_MANIFEST, new WeatherServiceManifest(hashTest)));
				}else if(key.equals(AppServiceManifest.KEY_MEDIA_SERVICE_MANIFEST)){
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(Test.TRUE, Validator.validateMediaServiceManifest( Test.GENERAL_MEDIA_SERVICE_MANIFEST, new MediaServiceManifest()));
				}else {
					assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
				}
			}
		} catch(JSONException e){
			fail(Test.JSON_FAIL);
		}
	}
}