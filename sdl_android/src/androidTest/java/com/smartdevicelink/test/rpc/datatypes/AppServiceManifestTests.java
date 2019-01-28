package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.rpc.AppServiceManifest;
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
public class AppServiceManifestTests extends TestCase {

	private AppServiceManifest msg;
	MediaServiceManifest mediaServiceManifest;
	WeatherServiceManifest weatherServiceManifest;
	JSONObject uriScheme;

	@Override
	public void setUp(){

		mediaServiceManifest = new MediaServiceManifest();

		weatherServiceManifest = new WeatherServiceManifest();
		weatherServiceManifest.setWeatherForLocationSupported(Test.GENERAL_BOOLEAN);
		weatherServiceManifest.setCurrentForecastSupported(Test.GENERAL_BOOLEAN);
		weatherServiceManifest.setMaxMultidayForecastAmount(Test.GENERAL_INTEGER);
		weatherServiceManifest.setMaxMinutelyForecastAmount(Test.GENERAL_INTEGER);
		weatherServiceManifest.setMaxHourlyForecastAmount(Test.GENERAL_INTEGER);

		uriScheme = new JSONObject();

		msg = new AppServiceManifest();
		msg.setServiceType(Test.GENERAL_APP_SERVICE_TYPE);
		msg.setAllowAppConsumers(Test.GENERAL_BOOLEAN);
		msg.setHandledRpcs(Test.GENERAL_FUNCTION_ID_LIST);
		msg.setMediaServiceManifest(mediaServiceManifest);
		msg.setRpcSpecVersion(Test.GENERAL_SDLMSGVERSION);
		msg.setServiceIcon(Test.GENERAL_STRING);
		msg.setServiceName(Test.GENERAL_STRING);
		msg.setUriPrefix(Test.GENERAL_STRING);
		msg.setUriScheme(uriScheme);
		msg.setWeatherServiceManifest(weatherServiceManifest);

	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues () {
		// Test Values
		String serviceIcon = msg.getServiceIcon();
		String serviceName = msg.getServiceName();
		String uriPrefix = msg.getUriPrefix();
		AppServiceType appServiceType = msg.getServiceType();
		boolean allowAppConsumers = msg.getAllowAppConsumers();
		JSONObject uriSchemeObject = msg.getUriScheme();
		SdlMsgVersion version = msg.getRpcSpecVersion();
		List<FunctionID> handledRPCs = msg.getHandledRpcs();
		WeatherServiceManifest weatherServiceManifestTest = msg.getWeatherServiceManifest();
		MediaServiceManifest mediaServiceManifestTest = msg.getMediaServiceManifest();

		// Valid Tests
		assertEquals(Test.GENERAL_BOOLEAN, allowAppConsumers);
		assertEquals(Test.GENERAL_STRING, serviceIcon);
		assertEquals(Test.GENERAL_STRING, serviceName);
		assertEquals(Test.GENERAL_STRING, uriPrefix);
		assertEquals(Test.GENERAL_APP_SERVICE_TYPE, appServiceType);
		assertEquals(uriScheme, uriSchemeObject);
		assertEquals(Test.GENERAL_SDLMSGVERSION, version);
		assertEquals(Test.MATCH, Test.GENERAL_FUNCTION_ID_LIST, handledRPCs);
		assertEquals(weatherServiceManifest, weatherServiceManifestTest);
		assertEquals(mediaServiceManifest, mediaServiceManifestTest);

		// Invalid/Null Tests
		AppServiceManifest msg = new AppServiceManifest();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getServiceIcon());
		assertNull(Test.NULL, msg.getServiceName());
		assertNull(Test.NULL, msg.getUriPrefix());
		assertNull(Test.NULL, msg.getServiceType());
		assertNull(Test.NULL, msg.getAllowAppConsumers());
		assertNull(Test.NULL, msg.getUriScheme());
		assertNull(Test.NULL, msg.getRpcSpecVersion());
		assertNull(Test.NULL, msg.getHandledRpcs());
		assertNull(Test.NULL, msg.getWeatherServiceManifest());
		assertNull(Test.NULL, msg.getMediaServiceManifest());
	}

	public void testJson(){
		JSONObject reference = new JSONObject();

		try{
			reference.put(AppServiceManifest.KEY_SERVICE_NAME, Test.GENERAL_STRING);
			reference.put(AppServiceManifest.KEY_SERVICE_ICON, Test.GENERAL_STRING);
			reference.put(AppServiceManifest.KEY_URI_PREFIX, Test.GENERAL_STRING);
			reference.put(AppServiceManifest.KEY_SERVICE_TYPE, Test.GENERAL_APP_SERVICE_TYPE);
			reference.put(AppServiceManifest.KEY_ALLOW_APP_CONSUMERS, Test.GENERAL_BOOLEAN);
			reference.put(AppServiceManifest.KEY_RPC_SPEC_VERSION, Test.GENERAL_SDLMSGVERSION.serializeJSON());
			reference.put(AppServiceManifest.KEY_HANDLED_RPCS, Test.GENERAL_FUNCTION_ID_LIST);
			reference.put(AppServiceManifest.KEY_URI_SCHEME, uriScheme);
			reference.put(AppServiceManifest.KEY_WEATHER_SERVICE_MANIFEST, weatherServiceManifest);
			reference.put(AppServiceManifest.KEY_MEDIA_SERVICE_MANIFEST, mediaServiceManifest);

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
					assertTrue(Test.TRUE, Validator.validateWeatherServiceManifest( weatherServiceManifest, new WeatherServiceManifest(hashTest)));
				}else if(key.equals(AppServiceManifest.KEY_MEDIA_SERVICE_MANIFEST)){
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(Test.TRUE, Validator.validateMediaServiceManifest( mediaServiceManifest, new MediaServiceManifest()));
				}else {
					assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
				}
			}
		} catch(JSONException e){
			fail(Test.JSON_FAIL);
		}
	}
}