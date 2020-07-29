package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.AppServiceManifest;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.MediaServiceManifest;
import com.smartdevicelink.proxy.rpc.NavigationServiceManifest;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.WeatherServiceManifest;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
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
		msg.setServiceType(TestValues.GENERAL_STRING);
		msg.setAllowAppConsumers(TestValues.GENERAL_BOOLEAN);
		msg.setHandledRpcs(TestValues.GENERAL_FUNCTION_ID_LIST);
		msg.setMediaServiceManifest(TestValues.GENERAL_MEDIA_SERVICE_MANIFEST);
		msg.setRpcSpecVersion(TestValues.GENERAL_SDLMSGVERSION);
		msg.setServiceIcon(TestValues.GENERAL_IMAGE);
		msg.setServiceName(TestValues.GENERAL_STRING);
		msg.setWeatherServiceManifest(TestValues.GENERAL_WEATHER_SERVICE_MANIFEST);
		msg.setNavigationServiceManifest(TestValues.GENERAL_NAVIGATION_SERVICE_MANIFEST);
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
		assertEquals(TestValues.GENERAL_BOOLEAN, allowAppConsumers);
		assertEquals(TestValues.GENERAL_IMAGE, serviceIcon);
		assertEquals(TestValues.GENERAL_STRING, serviceName);
		assertEquals(TestValues.GENERAL_STRING, appServiceType);
		assertEquals(TestValues.GENERAL_SDLMSGVERSION, version);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_FUNCTION_ID_LIST, handledRPCs);
		assertEquals(TestValues.GENERAL_WEATHER_SERVICE_MANIFEST, weatherServiceManifestTest);
		assertEquals(TestValues.GENERAL_MEDIA_SERVICE_MANIFEST, mediaServiceManifestTest);
		assertEquals(TestValues.GENERAL_NAVIGATION_SERVICE_MANIFEST, navigationServiceManifest);

		// Invalid/Null Tests
		AppServiceManifest msg = new AppServiceManifest();
		assertNotNull(TestValues.NOT_NULL, msg);

		assertNull(TestValues.NULL, msg.getServiceIcon());
		assertNull(TestValues.NULL, msg.getServiceName());
		assertNull(TestValues.NULL, msg.getServiceType());
		assertNull(TestValues.NULL, msg.getAllowAppConsumers());
		assertNull(TestValues.NULL, msg.getRpcSpecVersion());
		assertNull(TestValues.NULL, msg.getHandledRpcs());
		assertNull(TestValues.NULL, msg.getWeatherServiceManifest());
		assertNull(TestValues.NULL, msg.getMediaServiceManifest());
		assertNull(TestValues.NULL, msg.getNavigationServiceManifest());
	}

	public void testRequiredParamsConstructor(){
		msg = new AppServiceManifest(TestValues.GENERAL_STRING);
		String appServiceType = msg.getServiceType();
		assertEquals(TestValues.GENERAL_STRING, appServiceType);
	}

	public void testJson(){
		JSONObject reference = new JSONObject();

		try{
			reference.put(AppServiceManifest.KEY_SERVICE_NAME, TestValues.GENERAL_STRING);
			reference.put(AppServiceManifest.KEY_SERVICE_ICON, TestValues.GENERAL_IMAGE);
			reference.put(AppServiceManifest.KEY_SERVICE_TYPE, TestValues.GENERAL_STRING);
			reference.put(AppServiceManifest.KEY_ALLOW_APP_CONSUMERS, TestValues.GENERAL_BOOLEAN);
			reference.put(AppServiceManifest.KEY_RPC_SPEC_VERSION, TestValues.GENERAL_SDLMSGVERSION.serializeJSON());
			reference.put(AppServiceManifest.KEY_HANDLED_RPCS, TestValues.GENERAL_FUNCTION_ID_LIST);
			reference.put(AppServiceManifest.KEY_WEATHER_SERVICE_MANIFEST, TestValues.GENERAL_WEATHER_SERVICE_MANIFEST);
			reference.put(AppServiceManifest.KEY_MEDIA_SERVICE_MANIFEST, TestValues.GENERAL_MEDIA_SERVICE_MANIFEST);
			reference.put(AppServiceManifest.KEY_NAVIGATION_SERVICE_MANIFEST, TestValues.GENERAL_NAVIGATION_SERVICE_MANIFEST);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(TestValues.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while(iterator.hasNext()){
				String key = (String) iterator.next();

				if(key.equals(AppServiceManifest.KEY_RPC_SPEC_VERSION)){
					JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(TestValues.TRUE, Validator.validateSdlMsgVersion( new SdlMsgVersion(hashReference), new SdlMsgVersion(hashTest)));
				}else if(key.equals(AppServiceManifest.KEY_HANDLED_RPCS)){
					List<Integer> list1 = TestValues.GENERAL_FUNCTION_ID_LIST;
					List<Integer> list2 = JsonUtils.readIntegerListFromJsonObject(underTest, key);
					assertTrue(TestValues.TRUE, Validator.validateIntegerList(list1,list2));
				}else if(key.equals(AppServiceManifest.KEY_WEATHER_SERVICE_MANIFEST)){
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(TestValues.TRUE, Validator.validateWeatherServiceManifest( TestValues.GENERAL_WEATHER_SERVICE_MANIFEST, new WeatherServiceManifest(hashTest)));
				}else if(key.equals(AppServiceManifest.KEY_MEDIA_SERVICE_MANIFEST)){
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(TestValues.TRUE, Validator.validateMediaServiceManifest( TestValues.GENERAL_MEDIA_SERVICE_MANIFEST, new MediaServiceManifest(hashTest)));
				} else if(key.equals(AppServiceManifest.KEY_NAVIGATION_SERVICE_MANIFEST)){
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(TestValues.TRUE, Validator.validateNavigationServiceManifest( TestValues.GENERAL_NAVIGATION_SERVICE_MANIFEST, new NavigationServiceManifest(hashTest)));
				}else if(key.equals(AppServiceManifest.KEY_SERVICE_ICON)){
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Image refIcon1 = new Image(JsonRPCMarshaller.deserializeJSONObject(testEquals));
					assertTrue(TestValues.TRUE, Validator.validateImage(refIcon1, msg.getServiceIcon()));
				}else {
					assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
				}
			}
		} catch(JSONException e){
			fail(TestValues.JSON_FAIL);
		}
	}
}