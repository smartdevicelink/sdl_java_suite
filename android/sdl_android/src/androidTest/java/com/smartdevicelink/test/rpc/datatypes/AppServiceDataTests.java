package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.AppServiceData;
import com.smartdevicelink.proxy.rpc.MediaServiceData;
import com.smartdevicelink.proxy.rpc.NavigationServiceData;
import com.smartdevicelink.proxy.rpc.WeatherServiceData;
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
 * {@link com.smartdevicelink.proxy.rpc.AppServiceData}
 */
public class AppServiceDataTests extends TestCase {

	private AppServiceData msg;

	@Override
	public void setUp() {

		msg = new AppServiceData();
		msg.setServiceType(Test.GENERAL_STRING);
		msg.setServiceId(Test.GENERAL_STRING);
		msg.setMediaServiceData(Test.GENERAL_MEDIASERVICE_DATA);
		msg.setWeatherServiceData(Test.GENERAL_WEATHERSERVICE_DATA);
		msg.setNavigationServiceData(Test.GENERAL_NAVIGATIONSERVICE_DATA);

	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues () {
		// Test Values
		String appServiceType = msg.getServiceType();
		String serviceId = msg.getServiceId();
		MediaServiceData mediaServiceData = msg.getMediaServiceData();
		WeatherServiceData weatherServiceData = msg.getWeatherServiceData();
		NavigationServiceData navigationServiceData = msg.getNavigationServiceData();

		// Valid Tests
		assertEquals(Test.GENERAL_STRING, appServiceType);
		assertEquals(Test.GENERAL_STRING, serviceId);
		assertEquals(Test.GENERAL_MEDIASERVICE_DATA, mediaServiceData);
		assertEquals(Test.GENERAL_WEATHERSERVICE_DATA, weatherServiceData);
		assertEquals(Test.GENERAL_NAVIGATIONSERVICE_DATA, navigationServiceData);

		// Invalid/Null Tests
		AppServiceData msg = new AppServiceData();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getServiceType());
		assertNull(Test.NULL, msg.getServiceId());
		assertNull(Test.NULL, msg.getMediaServiceData());
		assertNull(Test.NULL, msg.getWeatherServiceData());
		assertNull(Test.NULL, msg.getNavigationServiceData());

	}

	public void testRequiredParamsConstructor(){

		msg = new AppServiceData(Test.GENERAL_STRING, Test.GENERAL_STRING);
		String appServiceType = msg.getServiceType();
		String serviceId = msg.getServiceId();
		assertEquals(Test.GENERAL_STRING, appServiceType);
		assertEquals(Test.GENERAL_STRING, serviceId);

	}

	public void testJson(){
		JSONObject reference = new JSONObject();

		try{
			reference.put(AppServiceData.KEY_SERVICE_TYPE, Test.GENERAL_STRING);
			reference.put(AppServiceData.KEY_SERVICE_ID, Test.GENERAL_STRING);
			reference.put(AppServiceData.KEY_MEDIA_SERVICE_DATA, Test.GENERAL_MEDIASERVICE_DATA);
			reference.put(AppServiceData.KEY_WEATHER_SERVICE_DATA, Test.GENERAL_WEATHERSERVICE_DATA);
			reference.put(AppServiceData.KEY_NAVIGATION_SERVICE_DATA, Test.GENERAL_NAVIGATIONSERVICE_DATA);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(Test.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while(iterator.hasNext()){
				String key = (String) iterator.next();

				if (key.equals(AppServiceData.KEY_MEDIA_SERVICE_DATA)){
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(Test.TRUE, Validator.validateMediaServiceData(Test.GENERAL_MEDIASERVICE_DATA, new MediaServiceData(hashTest)));
				} else if (key.equals(AppServiceData.KEY_WEATHER_SERVICE_DATA)){
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(Test.TRUE, Validator.validateWeatherServiceData(Test.GENERAL_WEATHERSERVICE_DATA, new WeatherServiceData(hashTest)));
				} else if (key.equals(AppServiceData.KEY_NAVIGATION_SERVICE_DATA)){
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(Test.TRUE, Validator.validateNavigationServiceData(Test.GENERAL_NAVIGATIONSERVICE_DATA, new NavigationServiceData(hashTest)));
				} else {
					assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
				}
			}
		} catch(JSONException e){
			fail(Test.JSON_FAIL);
		}
	}

}
