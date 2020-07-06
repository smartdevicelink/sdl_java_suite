package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.AppServiceData;
import com.smartdevicelink.proxy.rpc.MediaServiceData;
import com.smartdevicelink.proxy.rpc.NavigationServiceData;
import com.smartdevicelink.proxy.rpc.WeatherServiceData;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
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
		msg.setServiceType(TestValues.GENERAL_STRING);
		msg.setServiceID(TestValues.GENERAL_STRING);
		msg.setMediaServiceData(TestValues.GENERAL_MEDIASERVICEDATA);
		msg.setWeatherServiceData(TestValues.GENERAL_WEATHERSERVICEDATA);
		msg.setNavigationServiceData(TestValues.GENERAL_NAVIGATIONSERVICEDATA);

	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues () {
		// Test Values
		String appServiceType = msg.getServiceType();
		String serviceId = msg.getServiceID();
		MediaServiceData mediaServiceData = msg.getMediaServiceData();
		WeatherServiceData weatherServiceData = msg.getWeatherServiceData();
		NavigationServiceData navigationServiceData = msg.getNavigationServiceData();

		// Valid Tests
		assertEquals(TestValues.GENERAL_STRING, appServiceType);
		assertEquals(TestValues.GENERAL_STRING, serviceId);
		assertEquals(TestValues.GENERAL_MEDIASERVICEDATA, mediaServiceData);
		assertEquals(TestValues.GENERAL_WEATHERSERVICEDATA, weatherServiceData);
		assertEquals(TestValues.GENERAL_NAVIGATIONSERVICEDATA, navigationServiceData);

		// Invalid/Null Tests
		AppServiceData msg = new AppServiceData();
		assertNotNull(TestValues.NOT_NULL, msg);

		assertNull(TestValues.NULL, msg.getServiceType());
		assertNull(TestValues.NULL, msg.getServiceID());
		assertNull(TestValues.NULL, msg.getMediaServiceData());
		assertNull(TestValues.NULL, msg.getWeatherServiceData());
		assertNull(TestValues.NULL, msg.getNavigationServiceData());

	}

	public void testRequiredParamsConstructor(){

		msg = new AppServiceData(TestValues.GENERAL_STRING, TestValues.GENERAL_STRING);
		String appServiceType = msg.getServiceType();
		String serviceId = msg.getServiceID();
		assertEquals(TestValues.GENERAL_STRING, appServiceType);
		assertEquals(TestValues.GENERAL_STRING, serviceId);

	}

	public void testJson(){
		JSONObject reference = new JSONObject();

		try{
			reference.put(AppServiceData.KEY_SERVICE_TYPE, TestValues.GENERAL_STRING);
			reference.put(AppServiceData.KEY_SERVICE_ID, TestValues.GENERAL_STRING);
			reference.put(AppServiceData.KEY_MEDIA_SERVICE_DATA, TestValues.GENERAL_MEDIASERVICEDATA);
			reference.put(AppServiceData.KEY_WEATHER_SERVICE_DATA, TestValues.GENERAL_WEATHERSERVICEDATA);
			reference.put(AppServiceData.KEY_NAVIGATION_SERVICE_DATA, TestValues.GENERAL_NAVIGATIONSERVICEDATA);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(TestValues.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while(iterator.hasNext()){
				String key = (String) iterator.next();

				if (key.equals(AppServiceData.KEY_MEDIA_SERVICE_DATA)){
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(TestValues.TRUE, Validator.validateMediaServiceData(TestValues.GENERAL_MEDIASERVICEDATA, new MediaServiceData(hashTest)));
				} else if (key.equals(AppServiceData.KEY_WEATHER_SERVICE_DATA)){
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(TestValues.TRUE, Validator.validateWeatherServiceData(TestValues.GENERAL_WEATHERSERVICEDATA, new WeatherServiceData(hashTest)));
				} else if (key.equals(AppServiceData.KEY_NAVIGATION_SERVICE_DATA)){
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(TestValues.TRUE, Validator.validateNavigationServiceData(TestValues.GENERAL_NAVIGATIONSERVICEDATA, new NavigationServiceData(hashTest)));
				} else {
					assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
				}
			}
		} catch(JSONException e){
			fail(TestValues.JSON_FAIL);
		}
	}

}
