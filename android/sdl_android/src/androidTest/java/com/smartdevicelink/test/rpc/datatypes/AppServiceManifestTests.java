package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.AppServiceManifest;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.MediaServiceManifest;
import com.smartdevicelink.proxy.rpc.NavigationServiceManifest;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.WeatherServiceManifest;
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
public class AppServiceManifestTests extends TestCase {

	private AppServiceManifest msg;

	@Override
	public void setUp(){

		msg = new AppServiceManifest();
		msg.setServiceType(Test.GENERAL_STRING);
		msg.setAllowAppConsumers(Test.GENERAL_BOOLEAN);
		msg.setHandledRpcs(Test.GENERAL_FUNCTION_ID_LIST);
		msg.setMediaServiceManifest(Test.GENERAL_MEDIA_SERVICE_MANIFEST);
		msg.setRpcSpecVersion(Test.GENERAL_SDLMSGVERSION);
		msg.setServiceIcon(Test.GENERAL_IMAGE);
		msg.setServiceName(Test.GENERAL_STRING);
		msg.setWeatherServiceManifest(Test.GENERAL_WEATHER_SERVICE_MANIFEST);
		msg.setNavigationServiceManifest(Test.GENERAL_NAVIGATION_SERVICE_MANIFEST);
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues () {
		// Test Values
		Image serviceIcon = msg.getServiceIcon();
		String serviceName = msg.getServiceName();
		String appServiceType = msg.getServiceType();
		boolean allowAppConsumers = msg.getAllowAppConsumers();
		SdlMsgVersion version = msg.getRpcSpecVersion();
		List<Integer> handledRPCs = msg.getHandledRpcs();
		WeatherServiceManifest weatherServiceManifestTest = msg.getWeatherServiceManifest();
		MediaServiceManifest mediaServiceManifestTest = msg.getMediaServiceManifest();
		NavigationServiceManifest navigationServiceManifest = msg.getNavigationServiceManifest();

		// Valid Tests
		assertEquals(Test.GENERAL_BOOLEAN, allowAppConsumers);
		assertEquals(Test.GENERAL_IMAGE, serviceIcon);
		assertEquals(Test.GENERAL_STRING, serviceName);
		assertEquals(Test.GENERAL_STRING, appServiceType);
		assertEquals(Test.GENERAL_SDLMSGVERSION, version);
		assertEquals(Test.MATCH, Test.GENERAL_FUNCTION_ID_LIST, handledRPCs);
		assertEquals(Test.GENERAL_WEATHER_SERVICE_MANIFEST, weatherServiceManifestTest);
		assertEquals(Test.GENERAL_MEDIA_SERVICE_MANIFEST, mediaServiceManifestTest);
		assertEquals(Test.GENERAL_NAVIGATION_SERVICE_MANIFEST, navigationServiceManifest);

		// Invalid/Null Tests
		AppServiceManifest msg = new AppServiceManifest();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getServiceIcon());
		assertNull(Test.NULL, msg.getServiceName());
		assertNull(Test.NULL, msg.getServiceType());
		assertNull(Test.NULL, msg.getAllowAppConsumers());
		assertNull(Test.NULL, msg.getRpcSpecVersion());
		assertNull(Test.NULL, msg.getHandledRpcs());
		assertNull(Test.NULL, msg.getWeatherServiceManifest());
		assertNull(Test.NULL, msg.getMediaServiceManifest());
		assertNull(Test.NULL, msg.getNavigationServiceManifest());
	}

	public void testRequiredParamsConstructor(){
		msg = new AppServiceManifest(Test.GENERAL_STRING);
		String appServiceType = msg.getServiceType();
		assertEquals(Test.GENERAL_STRING, appServiceType);
	}

	public void testJson(){
		JSONObject reference = new JSONObject();

		try{
			reference.put(AppServiceManifest.KEY_SERVICE_NAME, Test.GENERAL_STRING);
			reference.put(AppServiceManifest.KEY_SERVICE_ICON, Test.GENERAL_IMAGE);
			reference.put(AppServiceManifest.KEY_SERVICE_TYPE, Test.GENERAL_STRING);
			reference.put(AppServiceManifest.KEY_ALLOW_APP_CONSUMERS, Test.GENERAL_BOOLEAN);
			reference.put(AppServiceManifest.KEY_RPC_SPEC_VERSION, Test.GENERAL_SDLMSGVERSION.serializeJSON());
			reference.put(AppServiceManifest.KEY_HANDLED_RPCS, Test.GENERAL_FUNCTION_ID_LIST);
			reference.put(AppServiceManifest.KEY_WEATHER_SERVICE_MANIFEST, Test.GENERAL_WEATHER_SERVICE_MANIFEST);
			reference.put(AppServiceManifest.KEY_MEDIA_SERVICE_MANIFEST, Test.GENERAL_MEDIA_SERVICE_MANIFEST);
			reference.put(AppServiceManifest.KEY_NAVIGATION_SERVICE_MANIFEST, Test.GENERAL_NAVIGATION_SERVICE_MANIFEST);

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
					List<Integer> list1 = Test.GENERAL_FUNCTION_ID_LIST;
					List<Integer> list2 = JsonUtils.readIntegerListFromJsonObject(underTest, key);
					assertTrue(Test.TRUE, Validator.validateIntegerList(list1,list2));
				}else if(key.equals(AppServiceManifest.KEY_WEATHER_SERVICE_MANIFEST)){
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(Test.TRUE, Validator.validateWeatherServiceManifest( Test.GENERAL_WEATHER_SERVICE_MANIFEST, new WeatherServiceManifest(hashTest)));
				}else if(key.equals(AppServiceManifest.KEY_MEDIA_SERVICE_MANIFEST)){
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(Test.TRUE, Validator.validateMediaServiceManifest( Test.GENERAL_MEDIA_SERVICE_MANIFEST, new MediaServiceManifest(hashTest)));
				} else if(key.equals(AppServiceManifest.KEY_NAVIGATION_SERVICE_MANIFEST)){
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(Test.TRUE, Validator.validateNavigationServiceManifest( Test.GENERAL_NAVIGATION_SERVICE_MANIFEST, new NavigationServiceManifest(hashTest)));
				}else if(key.equals(AppServiceManifest.KEY_SERVICE_ICON)){
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Image refIcon1 = new Image(JsonRPCMarshaller.deserializeJSONObject(testEquals));
					assertTrue(Test.TRUE, Validator.validateImage(refIcon1, msg.getServiceIcon()));
				}else {
					assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
				}
			}
		} catch(JSONException e){
			fail(Test.JSON_FAIL);
		}
	}
}